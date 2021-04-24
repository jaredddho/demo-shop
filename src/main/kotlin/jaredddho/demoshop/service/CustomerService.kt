package jaredddho.demoshop.service

import jaredddho.demoshop.model.Customer
import jaredddho.demoshop.model.auth.AccessToken
import jaredddho.demoshop.model.auth.AppUser
import jaredddho.demoshop.model.auth.RefreshToken
import jaredddho.demoshop.model.auth.UsernamePasswordCredential
import jaredddho.demoshop.repository.CustomerRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*
import javax.naming.AuthenticationException

@Service
class CustomerService(
        private val authenticationManager: AuthenticationManager,
        private val customerRepository: CustomerRepository,
        private val passwordEncoder: PasswordEncoder,
        private val tokenProvider: TokenProvider
) {

    fun findCustomerByEmail(email: String): Optional<Customer> =
            customerRepository.findCustomerByEmail(email)

    fun createCustomer(customer: Customer): Customer =
            customerRepository.save(
                    customer.apply {
                        password = passwordEncoder.encode(password)
                    }
            )

    // TODO: when updating customer, need to update AppUser cache in UserService
    fun updateCustomer() {}

    fun login(usernamePasswordCredential: UsernamePasswordCredential): Pair<AccessToken, RefreshToken>? {
        return try {
            val appUser = authenticate(
                    usernamePasswordCredential.username,
                    usernamePasswordCredential.password
            ).principal as AppUser
            tokenProvider.createAccessToken(appUser) to tokenProvider.createRefreshToken(appUser)
        } catch (ex: AuthenticationException) {
            null
        }
    }

    private fun authenticate(username: String, password: String): Authentication {
        return authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                        username,
                        password
                )
        )
    }
}
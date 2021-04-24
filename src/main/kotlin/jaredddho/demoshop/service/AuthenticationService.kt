package jaredddho.demoshop.service

import org.slf4j.Logger
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class AuthenticationService(
        private val logger: Logger,
        private val userService: UserService
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val appUser = userService.findUserByUsername(username)
        return when {
            appUser != null -> appUser
            else -> {
                throw UsernameNotFoundException(username)
            }
        }
    }
}
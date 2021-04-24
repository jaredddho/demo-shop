package jaredddho.demoshop.controller

import jaredddho.demoshop.constants.*
import jaredddho.demoshop.model.Customer
import jaredddho.demoshop.model.auth.AccessToken
import jaredddho.demoshop.model.auth.AppUser
import jaredddho.demoshop.model.auth.RefreshToken
import jaredddho.demoshop.model.auth.UsernamePasswordCredential
import jaredddho.demoshop.service.CustomerService
import org.slf4j.Logger
import org.springframework.http.*
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping(
        produces = [MediaType.APPLICATION_JSON_VALUE]
)
class CustomerController(
        private val logger: Logger,
        private val customerService: CustomerService
) {

    @RequestMapping(
            value = [API_CUSTOMERS_SIGNUP],
            method = [RequestMethod.POST],
            consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun signup(@RequestBody customer: Customer): Customer {
        return customerService
                .findCustomerByEmail(customer.email)
                .or { Optional.of(customerService.createCustomer(customer)) }
                .get()
    }

    @RequestMapping(
            value = [API_CUSTOMERS_LOGIN],
            method = [RequestMethod.POST],
            consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun login(@RequestBody usernamePasswordCredential: UsernamePasswordCredential):
            ResponseEntity<Pair<AccessToken, RefreshToken>?> {
        val tokens = customerService.login(usernamePasswordCredential)
        return when {
            tokens != null -> {
                ResponseEntity.ok()
                        .headers(HttpHeaders().apply {
                            setCookies(this, tokens.first.token, tokens.second.token)
                        })
                        .body(tokens)
            }
            else -> {
                ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .build()
            }
        }
    }

    @RequestMapping(
            value = [API_CUSTOMERS_LOGOUT],
            method = [RequestMethod.POST]
    )
    fun logout(requestEntity: RequestEntity<Any>): ResponseEntity<Any> {
        SecurityContextHolder.clearContext()
        return ResponseEntity.ok()
                .headers(HttpHeaders().apply {
                    setCookies(this, null, null)
                })
                .build()
    }

    @RequestMapping(
            value = [API_CUSTOMERS_ME],
            method = [RequestMethod.POST]
    )
    fun me(): Customer {
        SecurityContextHolder.getContext().authentication.let {
            val appUser = it.principal as AppUser
            return appUser.customer
        }
    }

    private fun setCookies(headers: HttpHeaders, accessToken: String?, refreshToken: String?) {
        headers.add(HttpHeaders.SET_COOKIE,
                ResponseCookie.from(COOKIE_ACCESS_TOKEN, accessToken ?: accessToken.toString())
                        .maxAge(if (accessToken != null) -1L else 0L)
                        .path("/")
                        .build()
                        .toString())
        headers.add(HttpHeaders.SET_COOKIE,
                ResponseCookie.from(COOKIE_REFRESH_TOKEN, refreshToken ?: refreshToken.toString())
                        .maxAge(if (refreshToken != null) -1L else 0L)
                        .path("/")
                        .build()
                        .toString())
    }
}
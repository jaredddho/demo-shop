package jaredddho.demoshop.model.auth

import jaredddho.demoshop.model.Customer
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

data class AppUser(
        val customer: Customer
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
            Collections.emptyList()

    override fun getPassword(): String = customer.password

    override fun getUsername(): String = customer.email

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = customer.enabled
}
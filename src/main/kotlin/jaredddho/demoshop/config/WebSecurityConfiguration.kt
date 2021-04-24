package jaredddho.demoshop.config

import jaredddho.demoshop.constants.API_CUSTOMERS_LOGIN
import jaredddho.demoshop.constants.API_CUSTOMERS_SIGNUP
import jaredddho.demoshop.service.AuthenticationService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.HttpStatusEntryPoint
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.csrf.CsrfFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CharacterEncodingFilter

@Configuration
class WebSecurityConfiguration(
        private val authenticationService: AuthenticationService
) : WebSecurityConfigurerAdapter() {

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource =
            UrlBasedCorsConfigurationSource().apply {
                registerCorsConfiguration("/**", CorsConfiguration().applyPermitDefaultValues())
            }


    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(authenticationService)
                .passwordEncoder(passwordEncoder())
    }

    override fun configure(http: HttpSecurity) {
        http.cors().and().csrf().disable()
                .addFilterBefore(
                        CharacterEncodingFilter(Charsets.UTF_8.name(), true),
                        CsrfFilter::class.java
                )
                .addFilterAfter(
                        AuthorizationFilter(authenticationService),
                        UsernamePasswordAuthenticationFilter::class.java
                )
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,
                        API_CUSTOMERS_SIGNUP,
                        API_CUSTOMERS_LOGIN
                ).permitAll()
                .antMatchers(HttpMethod.POST).authenticated()
                .antMatchers(HttpMethod.PUT).authenticated()
                .antMatchers(HttpMethod.DELETE).authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }
}

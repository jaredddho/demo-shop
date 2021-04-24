package jaredddho.demoshop.config

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.DecodedJWT
import jaredddho.demoshop.constants.COOKIE_ACCESS_TOKEN
import jaredddho.demoshop.constants.JWT_ISSUER
import jaredddho.demoshop.constants.JWT_SECRET
import jaredddho.demoshop.service.AuthenticationService
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthorizationFilter(
        private val authenticationService: AuthenticationService
) : OncePerRequestFilter() {

    private val jwtAlgorithm = Algorithm.HMAC512(JWT_SECRET)

    override fun doFilterInternal(
            request: HttpServletRequest,
            response: HttpServletResponse,
            chain: FilterChain
    ) {
        retrieveToken(request)?.let {
            try {
                val decodedJWT = validateToken(it)
                val userDetails = authenticationService.loadUserByUsername(decodedJWT.subject)
                SecurityContextHolder.getContext().authentication =
                        UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                emptyList()
                        )
            } catch (ex: JWTVerificationException) {
                SecurityContextHolder.clearContext()
            }
        }

        chain.doFilter(request, response)
    }

    private fun validateToken(token: String): DecodedJWT {
        return JWT.require(jwtAlgorithm)
                .withIssuer(JWT_ISSUER)
                .build()
                .verify(token)
    }

    private fun retrieveToken(request: HttpServletRequest): String? {
        val accessToken = retrieveTokenFromCookie(request)
        return when {
            accessToken != null -> accessToken
            else -> retrieveTokenFromHeader(request)
        }
    }

    private fun retrieveTokenFromCookie(request: HttpServletRequest): String? {
        val cookies = request.cookies ?: return null
        cookies.forEach {
            if (COOKIE_ACCESS_TOKEN.contentEquals(it.name)) {
                val accessToken = it.value
                if (StringUtils.hasText(accessToken)) return accessToken
            }
        }
        return null
    }

    private fun retrieveTokenFromHeader(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION)
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(AUTHORIZATION_PREFIX)) {
            val accessToken = bearerToken.substring(AUTHORIZATION_PREFIX.length)
            if (StringUtils.hasText(accessToken)) return accessToken
        }
        return null
    }
}

private const val AUTHORIZATION_PREFIX = "Bearer "


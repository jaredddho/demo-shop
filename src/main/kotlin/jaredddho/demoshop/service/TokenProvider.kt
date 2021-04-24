package jaredddho.demoshop.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import jaredddho.demoshop.constants.JWT_ACCESS_DURATION
import jaredddho.demoshop.constants.JWT_ISSUER
import jaredddho.demoshop.constants.JWT_REFRESH_DURATION
import jaredddho.demoshop.constants.JWT_SECRET
import jaredddho.demoshop.model.auth.AppUser
import jaredddho.demoshop.model.auth.AccessToken
import jaredddho.demoshop.model.auth.RefreshToken
import org.springframework.stereotype.Service
import java.util.*

@Service
class TokenProvider {

    private val algorithm = Algorithm.HMAC512(JWT_SECRET)

    fun createAccessToken(appUser: AppUser): AccessToken {
        val issuedAt = System.currentTimeMillis()
        val expiresAt = issuedAt + JWT_ACCESS_DURATION
        return AccessToken(
                createToken(
                        issuedAt,
                        expiresAt,
                        appUser
                ),
                expiresAt
        )
    }

    fun createRefreshToken(appUser: AppUser): RefreshToken {
        val issuedAt = System.currentTimeMillis()
        val expiresAt = issuedAt + JWT_REFRESH_DURATION
        return RefreshToken(
                createToken(
                        issuedAt,
                        expiresAt,
                        appUser
                ),
                expiresAt
        )
    }

    private fun createToken(issuedAt: Long, expiresAt: Long, appUser: AppUser): String = JWT.create()
            .withIssuer(JWT_ISSUER)
            .withIssuedAt(Date(issuedAt))
            .withExpiresAt(Date(expiresAt))
            .withSubject(appUser.username)
            .sign(algorithm)
}
package jaredddho.demoshop.model.auth

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(Include.NON_NULL)
open class Token(
        @JsonProperty(value = "token_type")
        val tokenType: TokenType,
        @JsonProperty(value = "token")
        open val token: String,
        @JsonProperty(value = "expires_at")
        open val expiresAt: Long
)

data class AccessToken(
        override val token: String,
        override val expiresAt: Long
): Token(TokenType.ACCESS, token, expiresAt)

data class RefreshToken(
        override val token: String,
        override val expiresAt: Long
): Token(TokenType.REFRESH, token, expiresAt)
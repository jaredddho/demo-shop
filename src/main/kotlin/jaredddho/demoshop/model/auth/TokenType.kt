package jaredddho.demoshop.model.auth

import com.fasterxml.jackson.annotation.JsonProperty

enum class TokenType {
    @JsonProperty(value = "access")
    ACCESS,

    @JsonProperty(value = "refresh")
    REFRESH
}
package jaredddho.demoshop.model.auth

import com.fasterxml.jackson.annotation.JsonProperty

data class UsernamePasswordCredential(
        @JsonProperty(value = "username")
        val username: String,
        @JsonProperty(value = "password")
        val password: String
)
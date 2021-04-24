package jaredddho.demoshop.model

import com.fasterxml.jackson.annotation.JsonProperty

enum class OrderStatus {
    @JsonProperty(value = "created")
    CREATED,

    @JsonProperty(value = "completed")
    COMPLETED,

    @JsonProperty(value = "cancelled")
    CANCELLED,

    @JsonProperty(value = "rejected")
    REJECTED
}
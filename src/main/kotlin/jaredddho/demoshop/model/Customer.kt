package jaredddho.demoshop.model

import com.fasterxml.jackson.annotation.JsonProperty
import org.apache.commons.lang3.builder.ToStringBuilder
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.io.Serializable

@Document(collection = "customers")
data class Customer(
        @Id
        @JsonProperty(value = "id")
        val id: String?,
        @JsonProperty(value = "enabled")
        val enabled: Boolean = true,
        @JsonProperty(value = "first_name")
        val firstName: String,
        @JsonProperty(value = "last_name")
        val lastName: String,
        @Indexed
        @JsonProperty(value = "email")
        val email: String,
        @JsonProperty(
                value = "password",
                access = JsonProperty.Access.WRITE_ONLY
        )
        var password: String
) : Serializable {

    override fun toString(): String {
        return ToStringBuilder.reflectionToString(this)
    }
}
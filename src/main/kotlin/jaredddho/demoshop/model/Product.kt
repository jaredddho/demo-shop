package jaredddho.demoshop.model

import com.fasterxml.jackson.annotation.JsonProperty
import org.apache.commons.lang3.builder.ToStringBuilder
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.io.Serializable

@Document(collection = "products")
data class Product(
        @Id
        @JsonProperty(value = "id")
        val id: String?,
        @JsonProperty(value = "name")
        val name: String,
        @JsonProperty(value = "description")
        val description: String?,
        @JsonProperty(value = "unit_price")
        val unitPrice: Double
) : Serializable {
    override fun toString(): String {
        return ToStringBuilder.reflectionToString(this)
    }
}
package jaredddho.demoshop.model

import com.fasterxml.jackson.annotation.JsonProperty
import org.apache.commons.lang3.builder.ToStringBuilder
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import java.io.Serializable
import java.util.stream.Collectors

@Document(collection = "orders")
data class Order(
        @Id
        @JsonProperty(value = "id")
        val id: String?,
        @DBRef
        @JsonProperty(value = "customer")
        val customer: Customer,
        @DBRef
        @JsonProperty(value = "items")
        val items: List<OrderItem>,
        @Transient
        @JsonProperty(value = "purchased_total")
        val purchasedTotal: Double = items.stream()
                .collect(
                        Collectors.summarizingDouble(
                                OrderItem::purchasedTotal
                        )
                ).sum
) : Serializable {

    override fun toString(): String {
        return ToStringBuilder.reflectionToString(this)
    }
}
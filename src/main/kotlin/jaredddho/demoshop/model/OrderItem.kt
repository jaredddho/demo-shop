package jaredddho.demoshop.model

import com.fasterxml.jackson.annotation.JsonProperty
import org.apache.commons.lang3.builder.ToStringBuilder
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import java.io.Serializable

@Document(collection = "order_items")
data class OrderItem(
        @Id
        @JsonProperty(value = "id")
        val id: String?,
        @DBRef
        @JsonProperty(value = "product")
        val product: Product,
        @JsonProperty(value = "purchased_unit_price")
        val purchasedUnitPrice: Double,
        @JsonProperty(value = "purchased_quantity")
        val purchasedQuantity: Int,
        @Transient
        @JsonProperty(value = "purchased_total")
        val purchasedTotal: Double = purchasedUnitPrice * purchasedQuantity,
        @JsonProperty(value = "order_status")
        val orderStatus: OrderStatus = OrderStatus.CREATED
) : Serializable {
    override fun toString(): String {
        return ToStringBuilder.reflectionToString(this)
    }
}
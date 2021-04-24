package jaredddho.demoshop.repository

import jaredddho.demoshop.model.Customer
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface CustomerRepository : MongoRepository<Customer, String> {
    fun findCustomerByEmail(email: String): Optional<Customer>
}
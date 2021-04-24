package jaredddho.demoshop.repository

import jaredddho.demoshop.model.Product
import org.springframework.data.mongodb.repository.MongoRepository

interface ProductRepository: MongoRepository<Product, String>
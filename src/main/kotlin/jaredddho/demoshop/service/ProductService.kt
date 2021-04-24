package jaredddho.demoshop.service

import jaredddho.demoshop.constants.CACHE_NAME_PRODUCTS
import jaredddho.demoshop.model.Product
import jaredddho.demoshop.repository.ProductRepository
import org.slf4j.Logger
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
@CacheConfig(cacheNames = [CACHE_NAME_PRODUCTS])
class ProductService(
        private val productRepository: ProductRepository
) {

    @Cacheable(cacheNames = [CACHE_NAME_PRODUCTS], unless = "#result == null")
    fun findProductById(id: String): Product? = productRepository.findByIdOrNull(id)

    @Cacheable(cacheNames = [CACHE_NAME_PRODUCTS])
    fun findAllProducts(): List<Product> = productRepository.findAll()

    @CachePut(cacheNames = [CACHE_NAME_PRODUCTS], key = "#product.id")
    fun saveProduct(product: Product): Product = productRepository.save(product)
}

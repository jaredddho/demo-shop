package jaredddho.demoshop.controller

import jaredddho.demoshop.constants.API_PRODUCTS_FIND_BY_ID
import jaredddho.demoshop.constants.API_PRODUCTS_UPDATE
import jaredddho.demoshop.model.Product
import jaredddho.demoshop.service.ProductService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(
        produces = [MediaType.APPLICATION_JSON_VALUE])
class ProductController(
        private val productService: ProductService
) {

    @RequestMapping(value = [API_PRODUCTS_FIND_BY_ID], method = [RequestMethod.GET])
    fun findProductById(@PathVariable id: String): Product? {
        return productService.findProductById(id)
    }

    @RequestMapping(value = [API_PRODUCTS_UPDATE], method = [RequestMethod.POST],
            consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun updateProduct(@RequestBody product: Product): Product {
        return productService.saveProduct(product)
    }
}
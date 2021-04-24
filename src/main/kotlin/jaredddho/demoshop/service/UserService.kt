package jaredddho.demoshop.service

import jaredddho.demoshop.constants.CACHE_NAME_USERS
import jaredddho.demoshop.model.auth.AppUser
import jaredddho.demoshop.repository.CustomerRepository
import org.slf4j.Logger
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
@CacheConfig(cacheNames = [CACHE_NAME_USERS])
class UserService(
        private val logger: Logger,
        private val customerRepository: CustomerRepository
) {
    @Cacheable(
            cacheNames = [CACHE_NAME_USERS],
            key = "#username"
    )
    fun findUserByUsername(username: String): AppUser? {
        return customerRepository.findCustomerByEmail(username).map {
            logger.debug("HITTING DATABASE")
            AppUser(it)
        }.orElse(null)
    }
}
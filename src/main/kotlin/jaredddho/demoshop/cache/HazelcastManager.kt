package jaredddho.demoshop.cache

import com.hazelcast.core.HazelcastInstance
import jaredddho.demoshop.model.Product
import jaredddho.demoshop.model.auth.AppUser
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class HazelcastManager(
        val logger: Logger,
        @Qualifier("hazelcastInstance")
        private val hazelcastInstance: HazelcastInstance
) {

    init {
        initCacheListeners()
    }

    private fun initCacheListeners() {
        hazelcastInstance.getMap<String, Product>("products")
                .addEntryListener(
                        ProductCacheEventListener(this),
                        true
                )
        hazelcastInstance.getMap<String, AppUser>("users")
                .addEntryListener(
                        AppUserCacheEventListener(this),
                        true
                )
    }
}
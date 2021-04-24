package jaredddho.demoshop.cache

import com.hazelcast.core.EntryEvent
import com.hazelcast.core.EntryListener
import com.hazelcast.map.MapEvent
import jaredddho.demoshop.model.Product

class ProductCacheEventListener(
        hazelcastManager: HazelcastManager
) : EntryListener<String, Product> {

    private val logger = hazelcastManager.logger

    override fun entryAdded(event: EntryEvent<String, Product>?) {
        logger.trace(
                "{} Added - {}",
                Product::class.simpleName,
                event?.key
        )
    }

    override fun entryUpdated(event: EntryEvent<String, Product>?) {
        logger.trace(
                "{} Updated - {}",
                Product::class.simpleName,
                event?.key
        )
    }

    override fun entryRemoved(event: EntryEvent<String, Product>?) {
        logger.trace(
                "{} Removed - {}",
                Product::class.simpleName,
                event?.key
        )
    }

    override fun entryEvicted(event: EntryEvent<String, Product>?) {
        logger.trace(
                "{} Evicted - {}",
                Product::class.simpleName,
                event?.key
        )
    }

    override fun entryExpired(event: EntryEvent<String, Product>?) {

        logger.trace(
                "{} Expired - {}",
                Product::class.simpleName,
                event?.key
        )
    }

    override fun mapCleared(event: MapEvent?) = Unit

    override fun mapEvicted(event: MapEvent?) = Unit
}
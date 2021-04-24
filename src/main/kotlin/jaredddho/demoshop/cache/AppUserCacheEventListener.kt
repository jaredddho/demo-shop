package jaredddho.demoshop.cache

import com.hazelcast.core.EntryEvent
import com.hazelcast.core.EntryListener
import com.hazelcast.map.MapEvent
import jaredddho.demoshop.model.auth.AppUser

class AppUserCacheEventListener(
        hazelcastManager: HazelcastManager
) : EntryListener<String, AppUser> {

    private val logger = hazelcastManager.logger

    override fun entryAdded(event: EntryEvent<String, AppUser>?) {
        logger.trace(
                "{} Added - {}",
                AppUser::class.simpleName,
                event?.key
        )
    }

    override fun entryUpdated(event: EntryEvent<String, AppUser>?) {
        logger.trace(
                "{} Updated - {}",
                AppUser::class.simpleName,
                event?.key
        )
    }

    override fun entryRemoved(event: EntryEvent<String, AppUser>?) {
        logger.trace(
                "{} Removed - {}",
                AppUser::class.simpleName,
                event?.key
        )
    }

    override fun entryEvicted(event: EntryEvent<String, AppUser>?) {
        logger.trace(
                "{} Evicted - {}",
                AppUser::class.simpleName,
                event?.key
        )
    }

    override fun entryExpired(event: EntryEvent<String, AppUser>?) {

        logger.trace(
                "{} Expired - {}",
                AppUser::class.simpleName,
                event?.key
        )
    }

    override fun mapCleared(event: MapEvent?) = Unit

    override fun mapEvicted(event: MapEvent?) = Unit
}
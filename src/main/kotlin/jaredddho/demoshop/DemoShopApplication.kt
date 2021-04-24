package jaredddho.demoshop

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.ImportResource

@EnableCaching
@SpringBootApplication
@ImportResource(locations = ["file:config/beans.xml"])
class DemoShopApplication

fun main(args: Array<String>) {
    runApplication<DemoShopApplication>(*args)
}

package jaredddho.demoshop.config

import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.i18n.CookieLocaleResolver
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor
import java.nio.charset.StandardCharsets
import java.util.*

@Configuration
class WebMvcConfiguration : WebMvcAutoConfiguration(),
        WebMvcConfigurer {

    @Bean
    fun messageSource(): MessageSource {
        return ReloadableResourceBundleMessageSource().apply {
            setBasename("file:config/i18n/messages")
            setDefaultEncoding(StandardCharsets.UTF_8.name())
        }
    }

    @Bean
    fun localeResolver(): LocaleResolver {
        return CookieLocaleResolver().apply {
            cookieName = "language"
            setDefaultLocale(Locale.UK)
        }
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(LocaleChangeInterceptor()
                .apply { paramName = "lang" })
    }
}
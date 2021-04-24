package jaredddho.demoshop.views

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.servlet.ModelAndView

abstract class BaseModelAndView(
        val view: String
) : ModelAndView(view) {
    init {
        this.addObject(ATTRIBUTE_IS_AUTHENTICATED, isAuthenticated())
    }

    private fun isAuthenticated(): Boolean {
        val authentication = SecurityContextHolder.getContext()
                .authentication
        return authentication != null && authentication.isAuthenticated
    }
}

private const val ATTRIBUTE_IS_AUTHENTICATED = "authenticated"
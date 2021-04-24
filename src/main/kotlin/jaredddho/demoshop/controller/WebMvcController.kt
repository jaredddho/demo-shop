package jaredddho.demoshop.controller

import jaredddho.demoshop.constants.WEB_HOME
import jaredddho.demoshop.constants.WEB_LOCALE
import jaredddho.demoshop.constants.WEB_LOGIN
import jaredddho.demoshop.constants.WEB_ROOT
import jaredddho.demoshop.views.HomeModelAndView
import jaredddho.demoshop.views.LoginModelAndView
import org.springframework.stereotype.Controller
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
class WebMvcController(
        private val localeResolver: LocaleResolver
) {

    @RequestMapping(value = [WEB_LOCALE])
    fun changeLocale(
            request: HttpServletRequest,
            response: HttpServletResponse,
            @RequestParam(value = "locale")
            locale: String
    ) = localeResolver.setLocale(
            request,
            response,
            StringUtils.parseLocaleString(locale)
    )

    @RequestMapping(value = [WEB_ROOT, WEB_HOME])
    fun home(): ModelAndView {
        return HomeModelAndView()
    }

    @RequestMapping(value = [WEB_LOGIN])
    fun login(): ModelAndView {
        return LoginModelAndView()
    }
}
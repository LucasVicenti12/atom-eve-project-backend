package org.eve.infra.auth.service

import jakarta.inject.Singleton
import jakarta.ws.rs.core.NewCookie
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.eve.domain.auth.entities.Token

@Singleton
class CookieService {
    @ConfigProperty(name = "mp.jwt.token.cookie")
    lateinit var cookieName: String

    fun cleanCookie(): NewCookie = NewCookie.Builder(cookieName)
        .value("")
        .path("/")
        .maxAge(0)
        .build()

    fun generateCookieFromToken(token: Token): NewCookie = NewCookie.Builder(cookieName)
        .value(token.token)
        .path("/")
        .httpOnly(true)
        .secure(true)
        .sameSite(NewCookie.SameSite.STRICT)
        .maxAge(token.expiration!!.toInt())
        .build()
}
package org.eve.infra.auth.service

import io.smallrye.jwt.build.Jwt
import jakarta.inject.Singleton
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.eve.domain.auth.entities.Token
import java.time.Duration
import java.time.Instant

@Singleton
class TokenService {
    @ConfigProperty(name = "mp.jwt.verify.issuer")
    lateinit var issuer: String

    fun generateToken(username: String): Token {
        val expiration = Duration.ofDays(7)
        val now = Instant.now()

        val instant = now.plus(expiration)

        val token = Jwt.issuer(issuer)
            .subject(username)
            .expiresAt(instant)
            .sign()

        val timer = expiration.toMillis()

        return Token(
            token = token,
            expiration = timer
        )
    }
}
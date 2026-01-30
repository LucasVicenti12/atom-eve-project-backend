package org.eve.domain.auth.usecase.implementation

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.eve.domain.auth.entities.Login
import org.eve.domain.auth.entities.Token
import org.eve.domain.auth.exceptions.AUTH_LOGIN_IS_EMPTY
import org.eve.domain.auth.exceptions.AUTH_PASSWORD_IS_EMPTY
import org.eve.domain.auth.usecase.AuthUseCase
import org.eve.domain.users.repository.UserRepository
import org.eve.utils.entities.DefaultResponse
import org.eve.utils.exceptions.UNEXPECTED_ERROR
import org.eve.infra.auth.service.TokenService
import io.vertx.core.impl.logging.LoggerFactory
import org.eve.domain.auth.exceptions.AUTH_INVALID_CREDENTIALS
import org.eve.domain.users.entities.User
import org.eve.utils.functions.Session
import org.springframework.security.crypto.password.PasswordEncoder

@ApplicationScoped
class AuthUseCaseImplementation(
    private val userRepository: UserRepository,
    private val session: Session
) : AuthUseCase {
    private val logger by lazy {
        LoggerFactory.getLogger(AuthUseCaseImplementation::class.java)
    }

    @Inject
    private lateinit var encoder: PasswordEncoder

    @Inject
    private lateinit var tokenService: TokenService

    override fun login(login: Login): DefaultResponse<Token> {
        try {
            if (login.username.isNullOrBlank()) {
                return DefaultResponse(error = AUTH_LOGIN_IS_EMPTY)
            }

            if (login.password.isNullOrBlank()) {
                return DefaultResponse(error = AUTH_PASSWORD_IS_EMPTY)
            }

            val user = userRepository.getUserByUserName(login.username!!)

            val matches = encoder.matches(login.password!!, user?.password)

            if (!matches) {
                return DefaultResponse(
                    error = AUTH_INVALID_CREDENTIALS
                )
            }

            val token = tokenService.generateToken(user!!.username!!)

            return DefaultResponse(
                data = token
            )
        } catch (e: Exception) {
            logger.error("ERROR_ON_LOGIN", e)
            return DefaultResponse(error = UNEXPECTED_ERROR)
        }
    }

    override fun me(): DefaultResponse<User> {
        try{
            return DefaultResponse(
                data = session.getUser()
            )
        }catch (e: Exception) {
            logger.error("ERROR_ON_GET_ME", e)
            return DefaultResponse(error = UNEXPECTED_ERROR)
        }
    }
}
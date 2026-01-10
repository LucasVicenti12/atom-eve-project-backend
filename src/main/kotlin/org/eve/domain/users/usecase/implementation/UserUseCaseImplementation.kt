package org.eve.domain.users.usecase.implementation

import io.vertx.core.impl.logging.LoggerFactory
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.eve.domain.users.entities.User
import org.eve.domain.users.exceptions.USER_ALREADY_EXISTS
import org.eve.domain.users.exceptions.USER_EMAIL_IS_EMPTY
import org.eve.domain.users.exceptions.USER_NAME_IS_EMPTY
import org.eve.domain.users.exceptions.USER_NOT_FOUND
import org.eve.domain.users.exceptions.USER_PASSWORD_IS_EMPTY
import org.eve.domain.users.exceptions.USER_USERNAME_IS_EMPTY
import org.eve.domain.users.repository.UserRepository
import org.eve.domain.users.usecase.UserUseCase
import org.eve.utils.entities.DefaultResponse
import org.eve.utils.entities.Pagination
import org.eve.utils.exceptions.UNEXPECTED_ERROR
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.UUID

@ApplicationScoped
class UserUseCaseImplementation(
    private val userRepository: UserRepository,
) : UserUseCase {
    @Inject
    private lateinit var encoder: PasswordEncoder

    private val logger by lazy {
        LoggerFactory.getLogger(UserUseCaseImplementation::class.java)
    }

    override fun createUser(user: User): DefaultResponse<User> {
        try {
            if (user.username.isNullOrBlank()) {
                return DefaultResponse(error = USER_USERNAME_IS_EMPTY)
            }

            if (user.name.isNullOrBlank()) {
                return DefaultResponse(error = USER_NAME_IS_EMPTY)
            }

            if (user.email.isNullOrBlank()) {
                return DefaultResponse(error = USER_EMAIL_IS_EMPTY)
            }

            if (user.password.isNullOrBlank()) {
                return DefaultResponse(error = USER_PASSWORD_IS_EMPTY)
            }

            if (userRepository.getUserByUserName(user.username!!) != null) {
                return DefaultResponse(error = USER_ALREADY_EXISTS)
            }

            user.password = encoder.encode(user.password)

            return DefaultResponse(
                data = userRepository.createUser(user),
            )
        } catch (e: Exception) {
            logger.error("ERROR_ON_CREATE_USER", e)
            return DefaultResponse(error = UNEXPECTED_ERROR)
        }
    }

    override fun updateUser(user: User): DefaultResponse<User> {
        try {
            if (userRepository.getUserByUUID(user.uuid!!) == null) {
                return DefaultResponse(error = USER_NOT_FOUND)
            }

            if (user.name.isNullOrBlank()) {
                return DefaultResponse(error = USER_NAME_IS_EMPTY)
            }

            if (user.email.isNullOrBlank()) {
                return DefaultResponse(error = USER_EMAIL_IS_EMPTY)
            }

            return DefaultResponse(
                data = userRepository.updateUser(user),
            )
        } catch (e: Exception) {
            logger.error("ERROR_ON_UPDATE_USER", e)
            return DefaultResponse(error = UNEXPECTED_ERROR)
        }
    }

    override fun getUserByUUID(uuid: UUID): DefaultResponse<User> {
        try {
            val user = userRepository.getUserByUUID(uuid) ?: return DefaultResponse(
                error = USER_NOT_FOUND
            )

            return DefaultResponse(
                data = user,
            )
        } catch (e: Exception) {
            logger.error("ERROR_ON_GET_USER_BY_UUID", e)
            return DefaultResponse(error = UNEXPECTED_ERROR)
        }
    }

    override fun getUserByUserName(username: String): DefaultResponse<User> {
        try {
            val user = userRepository.getUserByUserName(username)

            if (user === null) {
                return DefaultResponse(error = USER_NOT_FOUND)
            }

            return DefaultResponse(
                data = user,
            )
        } catch (e: Exception) {
            logger.error("ERROR_ON_GET_USER_BY_USER_NAME", e)
            return DefaultResponse(error = UNEXPECTED_ERROR)
        }
    }

    override fun getPaginatedUsers(
        page: Int,
        count: Int
    ): DefaultResponse<Pagination<User>> {
        try {
            return DefaultResponse(
                data = userRepository.getPaginatedUsers(page, count)
            )
        } catch (e: Exception) {
            logger.error("ERROR_ON_GET_USER_PAGINATION", e)
            return DefaultResponse(error = UNEXPECTED_ERROR)
        }
    }
}
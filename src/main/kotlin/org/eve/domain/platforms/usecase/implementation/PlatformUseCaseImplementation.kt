package org.eve.domain.platforms.usecase.implementation

import io.vertx.core.impl.logging.LoggerFactory
import jakarta.enterprise.context.ApplicationScoped
import org.eve.domain.platforms.entities.Platform
import org.eve.domain.platforms.exceptions.PLATFORM_COLOR_IS_EMPTY
import org.eve.domain.platforms.exceptions.PLATFORM_NAME_IS_EMPTY
import org.eve.domain.platforms.exceptions.PLATFORM_NOT_FOUND
import org.eve.domain.platforms.repository.PlatformRepository
import org.eve.domain.platforms.usecase.PlatformUseCase
import org.eve.utils.entities.DefaultResponse
import org.eve.utils.entities.Pagination
import org.eve.utils.exceptions.UNEXPECTED_ERROR
import java.util.UUID

@ApplicationScoped
class PlatformUseCaseImplementation(
    private val platformRepository: PlatformRepository
) : PlatformUseCase {
    private val logger by lazy {
        LoggerFactory.getLogger(PlatformUseCaseImplementation::class.java)
    }

    override fun createPlatform(platform: Platform, projectUUID: UUID): DefaultResponse<Platform> {
        try {
            if (platform.name.isNullOrBlank()) {
                return DefaultResponse(error = PLATFORM_NAME_IS_EMPTY)
            }

            if (platform.color.isNullOrBlank()) {
                return DefaultResponse(error = PLATFORM_COLOR_IS_EMPTY)
            }

            return DefaultResponse(
                data = platformRepository.createPlatform(platform, projectUUID)
            )
        } catch (e: Exception) {
            logger.error("ERROR_ON_CREATE_PLATFORM", e)
            return DefaultResponse(error = UNEXPECTED_ERROR)
        }
    }

    override fun updatePlatform(platform: Platform): DefaultResponse<Platform> {
        try {
            if (platformRepository.getPlatformByUUID(platform.uuid!!) == null) {
                return DefaultResponse(error = PLATFORM_NOT_FOUND)
            }

            if (platform.name.isNullOrBlank()) {
                return DefaultResponse(error = PLATFORM_NAME_IS_EMPTY)
            }

            if (platform.color.isNullOrBlank()) {
                return DefaultResponse(error = PLATFORM_COLOR_IS_EMPTY)
            }

            return DefaultResponse(
                data = platformRepository.updatePlatform(platform)
            )
        } catch (e: Exception) {
            logger.error("ERROR_ON_UPDATE_PLATFORM", e)
            return DefaultResponse(error = UNEXPECTED_ERROR)
        }
    }

    override fun getPlatformByUUID(uuid: UUID): DefaultResponse<Platform> {
        try {
            val platform = platformRepository.getPlatformByUUID(uuid) ?: return DefaultResponse(
                error = PLATFORM_NOT_FOUND
            )

            return DefaultResponse(
                data = platform
            )
        } catch (e: Exception) {
            logger.error("ERROR_ON_GET_PLATFORM_BY_UUID", e)
            return DefaultResponse(error = UNEXPECTED_ERROR)
        }
    }

    override fun getPaginatedPlatforms(page: Int, count: Int): DefaultResponse<Pagination<Platform>> {
        try {
            return DefaultResponse(
                data = platformRepository.getPaginatedPlatforms(page, count)
            )
        } catch (e: Exception) {
            logger.error("ERROR_ON_GET_PLATFORM_PAGINATION", e)
            return DefaultResponse(error = UNEXPECTED_ERROR)
        }
    }

    override fun getAllPlatforms(projectUUID: UUID): DefaultResponse<List<Platform>> {
        try {
            return DefaultResponse(
                data = platformRepository.getAllPlatforms(projectUUID)
            )
        } catch (e: Exception) {
            logger.error("ERROR_ON_GET_ALL_PLATFORMS", e)
            return DefaultResponse(error = UNEXPECTED_ERROR)
        }
    }
}
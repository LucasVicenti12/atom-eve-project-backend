package org.eve.domain.platforms.usecase

import org.eve.domain.platforms.entities.Platform
import org.eve.utils.entities.DefaultResponse
import org.eve.utils.entities.Pagination
import java.util.UUID

interface PlatformUseCase {
    fun createPlatform(platform: Platform, projectUUID: UUID): DefaultResponse<Platform>
    fun updatePlatform(platform: Platform): DefaultResponse<Platform>
    fun getPlatformByUUID(uuid: UUID): DefaultResponse<Platform>

    fun getPaginatedPlatforms(page: Int, count: Int): DefaultResponse<Pagination<Platform>>
    fun getAllPlatforms(projectUUID: UUID): DefaultResponse<List<Platform>>
}
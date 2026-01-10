package org.eve.domain.platforms.repository

import org.eve.domain.platforms.entities.Platform
import org.eve.utils.entities.Pagination
import java.util.UUID

interface PlatformRepository {
    fun createPlatform(platform: Platform, projectUUID: UUID): Platform?
    fun updatePlatform(platform: Platform): Platform?
    fun getPlatformByUUID(uuid: UUID): Platform?

    fun getPaginatedPlatforms(page: Int, count: Int): Pagination<Platform>
    fun getAllPlatforms(projectUUID: UUID): List<Platform>
}
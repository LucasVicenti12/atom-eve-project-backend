package org.eve.infra.platforms.repository

import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import org.eve.domain.platforms.entities.Platform
import org.eve.domain.platforms.repository.PlatformRepository
import org.eve.infra.platforms.database.PlatformJPA
import org.eve.infra.platforms.database.PlatformRepositoryJPA
import org.eve.infra.projects.database.ProjectJPA
import org.eve.utils.entities.Pagination
import java.util.UUID

@ApplicationScoped
class PlatformRepositoryImplementation(
    private val platformRepositoryJPA: PlatformRepositoryJPA
) : PlatformRepository {
    @Transactional
    override fun createPlatform(
        projectUUID: UUID,
        platform: Platform,
    ): Platform? {
        val projectJPA = ProjectJPA().apply {
            this.uuid = projectUUID
        }

        val platformJPA = PlatformJPA().apply {
            this.color = platform.color
            this.name = platform.name
            this.project = projectJPA
        }

        platformRepositoryJPA.persist(platformJPA)

        return platformJPA.toPlatformWithoutProject()
    }

    @Transactional
    override fun updatePlatform(platform: Platform): Platform? {
        platformRepositoryJPA.updatePlatform(platform)

        return platformRepositoryJPA.findById(platform.uuid!!)?.toPlatform()
    }

    @Transactional
    override fun getPlatformByUUID(uuid: UUID): Platform? =
        platformRepositoryJPA.findById(uuid)?.toPlatform()

    @Transactional
    override fun getPaginatedPlatforms(
        projectUUID: UUID,
        page: Int,
        count: Int
    ): Pagination<Platform> {
        val paginated = platformRepositoryJPA.getPaginatedPlatformsByProjectUUID(
            projectUUID,
            page,
            count
        )

        val items = paginated.list<PlatformJPA>()
            .map {
                it.toPlatform()
            }

        return Pagination(
            items = items,
            totalPages = paginated.pageCount(),
            totalCount = paginated.count(),
            page = page,
            count = count
        )
    }

    @Transactional
    override fun getAllPlatforms(projectUUID: UUID): List<Platform> = platformRepositoryJPA
        .getPlatformsByProjectUUID(projectUUID)
        .map {
            it.toPlatformWithoutProject()
        }
}
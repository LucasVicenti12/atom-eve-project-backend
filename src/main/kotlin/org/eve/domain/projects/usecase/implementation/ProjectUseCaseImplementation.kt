package org.eve.domain.projects.usecase.implementation

import io.vertx.core.impl.logging.LoggerFactory
import jakarta.enterprise.context.ApplicationScoped
import org.eve.domain.projects.entities.Project
import org.eve.domain.projects.exceptions.PROJECT_COLOR_IS_EMPTY
import org.eve.domain.projects.exceptions.PROJECT_NOT_FOUND
import org.eve.domain.projects.exceptions.PROJECT_TITLE_IS_EMPTY
import org.eve.domain.projects.repository.ProjectRepository
import org.eve.domain.projects.usecase.ProjectUseCase
import org.eve.utils.entities.DefaultResponse
import org.eve.utils.entities.Pagination
import org.eve.utils.entities.UNEXPECTED_ERROR
import java.util.UUID

@ApplicationScoped
class ProjectUseCaseImplementation(
    private val projectRepository: ProjectRepository
) : ProjectUseCase {
    private val logger by lazy {
        LoggerFactory.getLogger(ProjectUseCaseImplementation::class.java)
    }

    override fun createProject(project: Project): DefaultResponse<Project> {
        try {
            if (project.title.isNullOrBlank()) {
                return DefaultResponse(error = PROJECT_TITLE_IS_EMPTY)
            }

            if (project.color.isNullOrBlank()) {
                return DefaultResponse(error = PROJECT_COLOR_IS_EMPTY)
            }

            return DefaultResponse(
                data = projectRepository.createProject(project),
            )
        } catch (e: Exception) {
            logger.error("ERROR_ON_CREATE_PROJECT", e)
            return DefaultResponse(error = UNEXPECTED_ERROR)
        }
    }

    override fun updateProject(project: Project): DefaultResponse<Project> {
        try {
            if (projectRepository.getProjectByUUID(project.uuid!!) == null) {
                return DefaultResponse(error = PROJECT_NOT_FOUND)
            }

            if (project.title.isNullOrBlank()) {
                return DefaultResponse(error = PROJECT_TITLE_IS_EMPTY)
            }

            if (project.color.isNullOrBlank()) {
                return DefaultResponse(error = PROJECT_COLOR_IS_EMPTY)
            }

            return DefaultResponse(
                data = projectRepository.updateProject(project),
            )
        } catch (e: Exception) {
            logger.error("ERROR_ON_UPDATE_PROJECT", e)
            return DefaultResponse(error = UNEXPECTED_ERROR)
        }
    }

    override fun getProjectByUUID(uuid: UUID): DefaultResponse<Project> {
        try {
            val project = projectRepository.getProjectByUUID(uuid) ?: return DefaultResponse(
                error = PROJECT_NOT_FOUND
            )

            return DefaultResponse(
                data = project,
            )
        } catch (e: Exception) {
            logger.error("ERROR_ON_GET_PROJECT_BY_UUID", e)
            return DefaultResponse(error = UNEXPECTED_ERROR)
        }
    }

    override fun getPaginatedProjects(
        page: Int,
        count: Int
    ): DefaultResponse<Pagination<Project>> {
        try {
            return DefaultResponse(
                data = projectRepository.getPaginatedProjects(page, count)
            )
        } catch (e: Exception) {
            logger.error("ERROR_ON_GET_PROJECT_PAGINATION", e)
            return DefaultResponse(error = UNEXPECTED_ERROR)
        }
    }
}
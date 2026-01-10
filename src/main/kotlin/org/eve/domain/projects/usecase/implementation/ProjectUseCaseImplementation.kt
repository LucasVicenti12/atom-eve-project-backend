package org.eve.domain.projects.usecase.implementation

import io.vertx.core.impl.logging.LoggerFactory
import jakarta.enterprise.context.ApplicationScoped
import org.eve.domain.projects.entities.Project
import org.eve.domain.projects.exceptions.PROJECT_COLOR_IS_EMPTY
import org.eve.domain.projects.exceptions.PROJECT_NOT_FOUND
import org.eve.domain.projects.exceptions.PROJECT_THIS_USER_ALREADY_ADDED
import org.eve.domain.projects.exceptions.PROJECT_TITLE_IS_EMPTY
import org.eve.domain.projects.exceptions.PROJECT_YOU_ARENT_THE_OWNER
import org.eve.domain.projects.exceptions.PROJECT_YOU_CANNOT_ADD_YOURSELF
import org.eve.domain.projects.repository.ProjectRepository
import org.eve.domain.projects.usecase.ProjectUseCase
import org.eve.domain.users.exceptions.USER_NOT_FOUND
import org.eve.domain.users.repository.UserRepository
import org.eve.utils.entities.DefaultResponse
import org.eve.utils.entities.Pagination
import org.eve.utils.exceptions.UNEXPECTED_ERROR
import org.eve.utils.functions.CurrentUser
import java.util.UUID

@ApplicationScoped
class ProjectUseCaseImplementation(
    private val projectRepository: ProjectRepository,
    private val userRepository: UserRepository,
    private val currentUser: CurrentUser
) : ProjectUseCase {
    private val logger by lazy {
        LoggerFactory.getLogger(ProjectUseCaseImplementation::class.java)
    }

    override fun createProject(project: Project): DefaultResponse<Project> {
        try {
            if (project.name.isNullOrBlank()) {
                return DefaultResponse(error = PROJECT_TITLE_IS_EMPTY)
            }

            if (project.color.isNullOrBlank()) {
                return DefaultResponse(error = PROJECT_COLOR_IS_EMPTY)
            }

            val user = currentUser.get()

            project.owner = user

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

            if (project.name.isNullOrBlank()) {
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

    override fun getAllProjects(): DefaultResponse<List<Project>> {
        try {
            return DefaultResponse(
                data = projectRepository.getAllProjects()
            )
        } catch (e: Exception) {
            logger.error("ERROR_ON_GET_ALL_PROJECTS", e)
            return DefaultResponse(error = UNEXPECTED_ERROR)
        }
    }

    override fun addMemberToProject(
        projectUUID: UUID,
        userUUID: UUID
    ): DefaultResponse<Unit> {
        try {
            val user = currentUser.get()

            userRepository.getUserByUUID(userUUID) ?: return DefaultResponse(
                error = USER_NOT_FOUND
            )

            val project = projectRepository.getProjectByUUID(projectUUID) ?: return DefaultResponse(
                error = PROJECT_NOT_FOUND
            )

            if(project.owner!!.uuid != user.uuid) {
                return DefaultResponse(
                    error = PROJECT_YOU_ARENT_THE_OWNER
                )
            }

            if(user.uuid == userUUID){
                return DefaultResponse(
                    error = PROJECT_YOU_CANNOT_ADD_YOURSELF
                )
            }

            val added = project.members?.find {
                x -> x.user.uuid == userUUID
            }

            if (added != null) {
                return DefaultResponse(
                    error = PROJECT_THIS_USER_ALREADY_ADDED
                )
            }

            projectRepository.addMemberToProject(projectUUID, userUUID)

            return DefaultResponse()
        } catch (e: Exception) {
            logger.error("ERROR_ON_ADD_MEMBER", e)
            return DefaultResponse(error = UNEXPECTED_ERROR)
        }
    }

    override fun removeMemberFromProject(
        projectUUID: UUID,
        userUUID: UUID
    ): DefaultResponse<Unit> {
        try {
            val user = currentUser.get()

            userRepository.getUserByUUID(userUUID) ?: return DefaultResponse(
                error = USER_NOT_FOUND
            )

            val project = projectRepository.getProjectByUUID(projectUUID) ?: return DefaultResponse(
                error = PROJECT_NOT_FOUND
            )

            if(project.owner!!.uuid != user.uuid) {
                return DefaultResponse(
                    error = PROJECT_YOU_ARENT_THE_OWNER
                )
            }

            projectRepository.removeMemberFromProject(projectUUID, userUUID)

            return DefaultResponse()
        } catch (e: Exception) {
            logger.error("ERROR_ON_REMOVE_MEMBER", e)
            return DefaultResponse(error = UNEXPECTED_ERROR)
        }
    }
}
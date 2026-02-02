package org.eve.domain.tasks.usecase.implementation

import io.vertx.core.impl.logging.LoggerFactory
import jakarta.enterprise.context.ApplicationScoped
import org.eve.domain.platforms.exceptions.PLATFORM_NOT_FOUND
import org.eve.domain.platforms.repository.PlatformRepository
import org.eve.domain.tasks.entities.Task
import org.eve.domain.tasks.exceptions.TASK_DESCRIPTION_IS_EMPTY
import org.eve.domain.tasks.exceptions.TASK_NOT_FOUND
import org.eve.domain.tasks.exceptions.TASK_PLATFORM_IS_EMPTY
import org.eve.domain.tasks.exceptions.TASK_TITLE_IS_EMPTY
import org.eve.domain.tasks.repository.TaskRepository
import org.eve.domain.tasks.usecase.TaskUseCase
import org.eve.utils.entities.DefaultResponse
import org.eve.utils.entities.Pagination
import org.eve.utils.exceptions.UNEXPECTED_ERROR
import org.eve.utils.functions.Session
import java.util.UUID

@ApplicationScoped
class TaskUseCaseImplementation(
    private val taskRepository: TaskRepository,
    private val platformRepository: PlatformRepository,
    private val session: Session
) : TaskUseCase {
    private val logger by lazy {
        LoggerFactory.getLogger(TaskUseCaseImplementation::class.java)
    }

    override fun createTask(task: Task): DefaultResponse<Task> {
        try {
            val project = session.getProject()

            if (task.title.isNullOrBlank()) {
                return DefaultResponse(
                    error = TASK_TITLE_IS_EMPTY
                )
            }

            if (task.description.isNullOrBlank()) {
                return DefaultResponse(
                    error = TASK_DESCRIPTION_IS_EMPTY
                )
            }

            if (task.platform == null) {
                return DefaultResponse(
                    error = TASK_PLATFORM_IS_EMPTY
                )
            }

            platformRepository.getPlatformByUUID(task.platform!!.uuid!!) ?: return DefaultResponse(
                error = PLATFORM_NOT_FOUND
            )

            return DefaultResponse(
                data = taskRepository.createTask(project.uuid!!, task)
            )
        } catch (e: Exception) {
            logger.error("ERROR_ON_CREATE_TASK", e)
            return DefaultResponse(
                error = UNEXPECTED_ERROR
            )
        }
    }

    override fun updateTask(task: Task): DefaultResponse<Task> {
        try {
            if (taskRepository.getTaskByUUID(task.uuid!!) == null) {
                return DefaultResponse(
                    error = TASK_NOT_FOUND
                )
            }

            if (task.title.isNullOrBlank()) {
                return DefaultResponse(
                    error = TASK_TITLE_IS_EMPTY
                )
            }

            if (task.description.isNullOrBlank()) {
                return DefaultResponse(
                    error = TASK_DESCRIPTION_IS_EMPTY
                )
            }

            if (task.platform == null) {
                return DefaultResponse(
                    error = TASK_PLATFORM_IS_EMPTY
                )
            }

            platformRepository.getPlatformByUUID(task.platform!!.uuid!!) ?: return DefaultResponse(
                error = PLATFORM_NOT_FOUND
            )

            return DefaultResponse(
                data = taskRepository.updateTask(task)
            )
        } catch (e: Exception) {
            logger.error("ERROR_ON_UPDATE_TASK", e)
            return DefaultResponse(
                error = UNEXPECTED_ERROR
            )
        }
    }

    override fun getTaskByUUID(uuid: UUID): DefaultResponse<Task> {
        try {
            val task = taskRepository.getTaskByUUID(uuid) ?: return DefaultResponse(
                error = TASK_NOT_FOUND
            )

            return DefaultResponse(
                data = task
            )
        } catch (e: Exception) {
            logger.error("ERROR_ON_GET_TASK_BY_UUID", e)
            return DefaultResponse(error = UNEXPECTED_ERROR)
        }
    }

    override fun getPaginatedTasks(
        page: Int,
        count: Int
    ): DefaultResponse<Pagination<Task>> {
        try {
            val project = session.getProject()

            return DefaultResponse(
                data = taskRepository.getPaginatedTasks(project.uuid!!, page, count)
            )
        } catch (e: Exception) {
            logger.error("ERROR_ON_GET_TASK_PAGINATION", e)
            return DefaultResponse(error = UNEXPECTED_ERROR)
        }
    }

    override fun getAllTasks(): DefaultResponse<List<Task>> {
        try {
            val project = session.getProject()

            return DefaultResponse(
                data = taskRepository.getAllTasks(project.uuid!!)
            )
        } catch (e: Exception) {
            logger.error("ERROR_ON_GET_ALL_TASK", e)
            return DefaultResponse(error = UNEXPECTED_ERROR)
        }
    }
}
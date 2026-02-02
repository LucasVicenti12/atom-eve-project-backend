package org.eve.infra.tasks.repository

import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import org.eve.domain.tasks.entities.Task
import org.eve.domain.tasks.repository.TaskRepository
import org.eve.infra.platforms.database.PlatformJPA
import org.eve.infra.projects.database.ProjectJPA
import org.eve.infra.tasks.database.TaskJPA
import org.eve.infra.tasks.database.TaskRepositoryJPA
import org.eve.utils.entities.Pagination
import java.util.UUID

@ApplicationScoped
class TaskRepositoryImplementation(
    private val taskRepositoryJPA: TaskRepositoryJPA
) : TaskRepository {
    @Transactional
    override fun createTask(
        projectUUID: UUID,
        task: Task
    ): Task? {
        val projectJPA = ProjectJPA().apply {
            this.uuid = projectUUID
        }

        val platformJPA = PlatformJPA().apply {
            this.uuid = task.platform!!.uuid!!
        }

        val taskJPA = TaskJPA().apply {
            this.title = task.title
            this.description = task.description
            this.platform = platformJPA
            this.project = projectJPA
        }

        taskRepositoryJPA.persist(taskJPA)

        return taskJPA.toTaskWithoutProjectAndPlatform()
    }

    @Transactional
    override fun updateTask(task: Task): Task? {
        taskRepositoryJPA.updateTask(task)

        return taskRepositoryJPA.findById(task.uuid!!)?.toTask()
    }

    @Transactional
    override fun getTaskByUUID(uuid: UUID): Task? =
        taskRepositoryJPA.findById(uuid)?.toTask()

    @Transactional
    override fun getPaginatedTasks(
        projectUUID: UUID,
        page: Int,
        count: Int
    ): Pagination<Task> {
        val paginated = taskRepositoryJPA.getPaginatedTaskByProjectUUID(
            projectUUID,
            page,
            count
        )

        val items = paginated.list<TaskJPA>()
            .map {
                it.toTask()
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
    override fun getAllTasks(projectUUID: UUID): List<Task> = taskRepositoryJPA
        .getTaskByProjectUUID(projectUUID)
        .map {
            it.toTaskWithoutProjectAndPlatform()
        }
}
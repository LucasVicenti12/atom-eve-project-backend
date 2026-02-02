package org.eve.domain.tasks.repository

import org.eve.domain.tasks.entities.Task
import org.eve.utils.entities.Pagination
import java.util.UUID

interface TaskRepository {
    fun createTask(projectUUID: UUID, task: Task): Task?
    fun updateTask(task: Task): Task?
    fun getTaskByUUID(uuid: UUID): Task?

    fun getPaginatedTasks(projectUUID: UUID, page: Int, count: Int): Pagination<Task>
    fun getAllTasks(projectUUID: UUID): List<Task>
}
package org.eve.domain.tasks.usecase

import org.eve.domain.tasks.entities.Task
import org.eve.utils.entities.DefaultResponse
import org.eve.utils.entities.Pagination
import java.util.UUID

interface TaskUseCase {
    fun createTask(task: Task): DefaultResponse<Task>
    fun updateTask(task: Task): DefaultResponse<Task>
    fun getTaskByUUID(uuid: UUID): DefaultResponse<Task>

    fun getPaginatedTasks(page: Int, count: Int): DefaultResponse<Pagination<Task>>
    fun getAllTasks(): DefaultResponse<List<Task>>
}
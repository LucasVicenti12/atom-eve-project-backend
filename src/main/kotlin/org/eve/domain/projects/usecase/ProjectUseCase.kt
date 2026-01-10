package org.eve.domain.projects.usecase

import org.eve.domain.projects.entities.Project
import org.eve.utils.entities.DefaultResponse
import org.eve.utils.entities.Pagination
import java.util.UUID

interface ProjectUseCase {
    fun createProject(project: Project): DefaultResponse<Project>
    fun updateProject(project: Project): DefaultResponse<Project>

    fun getProjectByUUID(uuid: UUID): DefaultResponse<Project>
    fun getPaginatedProjects(page: Int, count: Int): DefaultResponse<Pagination<Project>>
    fun getAllProjects(): DefaultResponse<List<Project>>

    fun addMemberToProject(projectUUID: UUID, userUUID: UUID): DefaultResponse<Unit>
    fun removeMemberFromProject(projectUUID: UUID, userUUID: UUID): DefaultResponse<Unit>
}
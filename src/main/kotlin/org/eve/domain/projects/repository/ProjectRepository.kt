package org.eve.domain.projects.repository

import org.eve.domain.projects.entities.Project
import org.eve.utils.entities.Pagination
import java.util.UUID

interface ProjectRepository {
    fun createProject(project: Project): Project?
    fun updateProject(project: Project): Project?

    fun getProjectByUUID(uuid: UUID): Project?
    fun getPaginatedProjects(page: Int, count: Int): Pagination<Project>
    fun getAllProjects(): List<Project>

    fun addMemberToProject(projectUUID: UUID, userUUID: UUID)
    fun removeMemberFromProject(projectUUID: UUID, userUUID: UUID)
}
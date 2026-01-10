package org.eve.infra.projects.repository

import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import org.eve.domain.projects.entities.Project
import org.eve.domain.projects.repository.ProjectRepository
import org.eve.infra.projects.database.ProjectMembers
import org.eve.infra.projects.database.ProjectMembersRepositoryJPA
import org.eve.infra.projects.database.ProjectJPA
import org.eve.infra.projects.database.ProjectRepositoryJPA
import org.eve.infra.users.database.UserJPA
import org.eve.infra.users.database.UserRepositoryJPA
import org.eve.utils.entities.Pagination
import java.util.UUID

@ApplicationScoped
class ProjectRepositoryImplementation(
    private val projectRepositoryJPA: ProjectRepositoryJPA,
    private val projectMembersRepositoryJPA: ProjectMembersRepositoryJPA,
    private val userRepositoryJPA: UserRepositoryJPA
) : ProjectRepository {
    @Transactional
    override fun createProject(project: Project): Project? {
        val userJPA = userRepositoryJPA.findById(project.owner!!.uuid)

        val projectJPA = ProjectJPA().apply {
            this.name = project.name
            this.description = project.description
            this.color = project.color
            this.status = project.status
            this.owner = userJPA
        }

        projectRepositoryJPA.persist(projectJPA)

        return projectJPA.toProject()
    }

    @Transactional
    override fun updateProject(project: Project): Project? {
        projectRepositoryJPA.updateProject(project)

        return projectRepositoryJPA.findById(project.uuid!!)?.toProject()
    }

    @Transactional
    override fun getProjectByUUID(uuid: UUID): Project? =
        projectRepositoryJPA.findById(uuid)?.toProject()

    @Transactional
    override fun getPaginatedProjects(
        page: Int,
        count: Int
    ): Pagination<Project> {
        val paginated = projectRepositoryJPA.findPaginated(page, count)

        val items = paginated.list<ProjectJPA>()
            .map {
                it.toProject()
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
    override fun getAllProjects(): List<Project> = projectRepositoryJPA.findAllWithStatusActive()
        .map {
            it.toProjectResume()
        }

    @Transactional
    override fun addMemberToProject(projectUUID: UUID, userUUID: UUID) {
        val projectJPA = ProjectJPA().apply {
            this.uuid = projectUUID
        }

        val userJPA = UserJPA().apply {
            this.uuid = userUUID
        }

        val projectMembers = ProjectMembers().apply {
            this.project = projectJPA
            this.user = userJPA
        }

        projectMembersRepositoryJPA.persist(projectMembers)
    }

    @Transactional
    override fun removeMemberFromProject(projectUUID: UUID, userUUID: UUID) {
        projectMembersRepositoryJPA.removeByUserUUID(projectUUID, userUUID)
    }
}
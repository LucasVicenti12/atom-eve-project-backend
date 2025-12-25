package org.eve.infra.projects.repository

import jakarta.enterprise.context.ApplicationScoped
import org.eve.domain.projects.entities.Project
import org.eve.domain.projects.repository.ProjectRepository
import org.eve.infra.projects.database.ProjectJPA
import org.eve.infra.projects.database.ProjectRepositoryJPA
import org.eve.utils.entities.Pagination
import java.util.UUID

@ApplicationScoped
class ProjectRepositoryImplementation(
    private val projectRepositoryJPA: ProjectRepositoryJPA
) : ProjectRepository {
    override fun createProject(project: Project): Project? {
        val projectJPA = ProjectJPA().apply {
            this.title = project.title
            this.description = project.description
            this.color = project.color
        }

        projectRepositoryJPA.persist(projectJPA)

        return projectJPA.toProject()
    }

    override fun updateProject(project: Project): Project? {
        projectRepositoryJPA.updateProject(project)

        return projectRepositoryJPA.findById(project.uuid!!)?.toProject()
    }

    override fun getProjectByUUID(uuid: UUID): Project? =
        projectRepositoryJPA.findById(uuid)?.toProject()

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
}
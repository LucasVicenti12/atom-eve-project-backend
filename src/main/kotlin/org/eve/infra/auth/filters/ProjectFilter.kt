package org.eve.infra.auth.filters

import io.vertx.ext.web.RoutingContext
import jakarta.annotation.Priority
import jakarta.inject.Inject
import jakarta.ws.rs.Priorities
import jakarta.ws.rs.container.ContainerRequestContext
import jakarta.ws.rs.container.ContainerRequestFilter
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.Provider
import org.eve.domain.projects.repository.ProjectRepository
import org.eve.utils.annotations.ProjectRequired
import org.eve.utils.entities.DefaultResponse
import org.eve.utils.exceptions.PROJECT_IS_REQUIRED
import org.eve.utils.exceptions.PROJECT_NOT_FOUND
import java.util.UUID

@Provider
@ProjectRequired
@Priority(Priorities.AUTHORIZATION)
class ProjectFilter(
    private val projectRepository: ProjectRepository,
) : ContainerRequestFilter {
    @Inject
    private lateinit var routingContext: RoutingContext

    override fun filter(ctx: ContainerRequestContext) {
        val projectUUID = ctx.getHeaderString("Project-UUID")

        if (projectUUID.isNullOrEmpty()) {
            val response = DefaultResponse<Unit>(
                error = PROJECT_IS_REQUIRED
            )

            ctx.abortWith(
                Response.status(Response.Status.UNAUTHORIZED)
                    .entity(response)
                    .build()
            )
            return
        }

        val project = projectRepository.getProjectByUUID(UUID.fromString(projectUUID))

        if (project === null) {
            val response = DefaultResponse<Unit>(
                error = PROJECT_NOT_FOUND
            )

            ctx.abortWith(
                Response.status(Response.Status.UNAUTHORIZED)
                    .entity(response)
                    .build()
            )
            return
        }

        routingContext.put("project", project)
    }
}
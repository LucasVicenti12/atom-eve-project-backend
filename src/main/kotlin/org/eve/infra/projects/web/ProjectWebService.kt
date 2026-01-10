package org.eve.infra.projects.web

import jakarta.ws.rs.Consumes
import jakarta.ws.rs.DELETE
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.PUT
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.Produces
import jakarta.ws.rs.QueryParam
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eve.domain.projects.entities.Project
import org.eve.domain.projects.usecase.ProjectUseCase
import org.eve.utils.entities.DefaultResponse
import java.util.UUID

@Path("/projects")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class ProjectWebService(
    private val projectUseCase: ProjectUseCase
) {
    @POST
    fun createProject(project: Project): Response {
        val response = projectUseCase.createProject(project)

        if (response.error != null) {
            return Response.status(
                Response.Status.BAD_REQUEST
            ).entity(response).build()
        }

        return Response.ok(response).build()
    }

    @PUT
    fun updateProject(project: Project): Response {
        val response = projectUseCase.updateProject(project)

        if (response.error != null) {
            return Response.status(
                Response.Status.BAD_REQUEST
            ).entity(response).build()
        }

        return Response.ok(response).build()
    }

    @GET
    @Path("/uuid/{uuid}")
    fun getProjectByUUID(@PathParam(value = "uuid") uuid: UUID): Response {
        val response = projectUseCase.getProjectByUUID(uuid)

        if (response.error != null) {
            return Response.status(
                Response.Status.BAD_REQUEST
            ).entity(response).build()
        }

        return Response.ok(response).build()
    }

    @GET
    fun getPaginatedProjects(
        @QueryParam(value = "page") page: Int,
        @QueryParam(value = "count") count: Int
    ): Response {
        val response = projectUseCase.getPaginatedProjects(page, count)

        if (response.error != null) {
            return Response.status(
                Response.Status.BAD_REQUEST
            ).entity(response).build()
        }

        return Response.ok(response).build()
    }

    @GET
    @Path("/all")
    fun getAllProjects(): Response {
        val response = projectUseCase.getAllProjects()

        if (response.error != null) {
            return Response.status(
                Response.Status.BAD_REQUEST
            ).entity(response).build()
        }

        return Response.ok(response).build()
    }

    @POST
    @Path("/{projectUUID}/add/{userUUID}")
    fun addMemberToProject(
        @PathParam(value = "projectUUID") projectUUID: UUID,
        @PathParam(value = "userUUID") userUUID: UUID
    ): Response {
        val response = projectUseCase.addMemberToProject(projectUUID, userUUID)

        if (response.error != null) {
            return Response.status(
                Response.Status.BAD_REQUEST
            ).entity(response).build()
        }

        return Response.ok().build()
    }

    @DELETE
    @Path("/{projectUUID}/remove/{userUUID}")
    fun removeMemberFromProject(
        @PathParam(value = "projectUUID") projectUUID: UUID,
        @PathParam(value = "userUUID") userUUID: UUID
    ): Response {
        val response = projectUseCase.removeMemberFromProject(projectUUID, userUUID)

        if (response.error != null) {
            return Response.status(
                Response.Status.BAD_REQUEST
            ).entity(response).build()
        }

        return Response.ok().build()
    }
}
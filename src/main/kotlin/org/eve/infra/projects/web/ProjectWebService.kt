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
import org.eve.utils.annotations.ProjectRequired
import org.eve.utils.functions.Session
import java.util.UUID

@Path("/projects")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class ProjectWebService(
    private val projectUseCase: ProjectUseCase,
    private val session: Session,
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
    @Path("/all")
    fun getPaginatedProjectsAll(
        @QueryParam(value = "page") page: Int,
        @QueryParam(value = "count") count: Int,
        @QueryParam(value = "all") all: Boolean?
    ): Response {
        val response = projectUseCase.getPaginatedProjects(page, count, all ?: true)

        if (response.error != null) {
            return Response.status(
                Response.Status.BAD_REQUEST
            ).entity(response).build()
        }

        return Response.ok(response).build()
    }

    @POST
    @Path("/add/{userUUID}")
    @ProjectRequired
    fun addMemberToProject(
        @PathParam(value = "userUUID") userUUID: UUID
    ): Response {
        val project = session.getProject()

        val response = projectUseCase.addMemberToProject(project.uuid!!, userUUID)

        if (response.error != null) {
            return Response.status(
                Response.Status.BAD_REQUEST
            ).entity(response).build()
        }

        return Response.ok().build()
    }

    @DELETE
    @Path("/remove/{userUUID}")
    @ProjectRequired
    fun removeMemberFromProject(
        @PathParam(value = "userUUID") userUUID: UUID
    ): Response {
        val project = session.getProject()

        val response = projectUseCase.removeMemberFromProject(project.uuid!!, userUUID)

        if (response.error != null) {
            return Response.status(
                Response.Status.BAD_REQUEST
            ).entity(response).build()
        }

        return Response.ok().build()
    }
}
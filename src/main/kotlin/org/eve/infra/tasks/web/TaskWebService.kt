package org.eve.infra.tasks.web

import jakarta.ws.rs.Consumes
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.PUT
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.Produces
import jakarta.ws.rs.QueryParam
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eve.domain.tasks.entities.Task
import org.eve.domain.tasks.usecase.TaskUseCase
import org.eve.utils.annotations.ProjectRequired
import java.util.UUID

@Path("/tasks")
@ProjectRequired
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class TaskWebService(
    private val taskUseCase: TaskUseCase
) {
    @POST
    fun createTask(task: Task): Response {
        val response = taskUseCase.createTask(task)

        if (response.error != null) {
            return Response.status(
                Response.Status.BAD_REQUEST
            ).entity(response).build()
        }

        return Response.ok(response).build()
    }

    @PUT
    fun updateTask(task: Task): Response {
        val response = taskUseCase.updateTask(task)

        if (response.error != null) {
            return Response.status(
                Response.Status.BAD_REQUEST
            ).entity(response).build()
        }

        return Response.ok(response).build()
    }

    @GET
    @Path("/uuid/{uuid}")
    fun getTaskByUUID(@PathParam(value = "uuid") uuid: UUID): Response {
        val response = taskUseCase.getTaskByUUID(uuid)

        if (response.error != null) {
            return Response.status(
                Response.Status.BAD_REQUEST
            ).entity(response).build()
        }

        return Response.ok(response).build()
    }

    @GET
    fun getPaginatedTasks(
        @QueryParam(value = "page") page: Int,
        @QueryParam(value = "count") count: Int
    ): Response {
        val response = taskUseCase.getPaginatedTasks(page, count)

        if (response.error != null) {
            return Response.status(
                Response.Status.BAD_REQUEST
            ).entity(response).build()
        }

        return Response.ok(response).build()
    }

    @GET
    @Path("/all")
    fun getAllTasks(): Response {
        val response = taskUseCase.getAllTasks()

        if (response.error != null) {
            return Response.status(
                Response.Status.BAD_REQUEST
            ).entity(response).build()
        }

        return Response.ok(response).build()
    }
}
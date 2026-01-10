package org.eve.infra.users.web

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
import org.eve.domain.users.entities.User
import org.eve.domain.users.usecase.UserUseCase
import java.util.UUID

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class UserWebService(
    private val userUseCase: UserUseCase
) {
    @POST
    @Path("/register")
    fun createUser(user: User): Response {
        val response = userUseCase.createUser(user)

        if (response.error != null) {
            return Response.status(
                Response.Status.BAD_REQUEST
            ).entity(response).build()
        }

        return Response.ok(response).build()
    }

    @PUT
    @Path("/update")
    fun updateUser(user: User): Response {
        val response = userUseCase.updateUser(user)

        if (response.error != null) {
            return Response.status(
                Response.Status.BAD_REQUEST
            ).entity(response).build()
        }

        return Response.ok(response).build()
    }

    @GET
    @Path("/uuid/{uuid}")
    fun getUserByUUID(
        @PathParam(value = "uuid") uuid: UUID
    ): Response {
        val response = userUseCase.getUserByUUID(uuid)

        if (response.error != null) {
            return Response.status(
                Response.Status.BAD_REQUEST
            ).entity(response).build()
        }

        return Response.ok(response).build()
    }

    @GET
    fun getPaginatedUsers(
        @QueryParam(value = "page") page: Int,
        @QueryParam(value = "count") count: Int
    ): Response {
        val response = userUseCase.getPaginatedUsers(page, count)

        if (response.error != null) {
            return Response.status(
                Response.Status.BAD_REQUEST
            ).entity(response).build()
        }

        return Response.ok(response).build()
    }
}
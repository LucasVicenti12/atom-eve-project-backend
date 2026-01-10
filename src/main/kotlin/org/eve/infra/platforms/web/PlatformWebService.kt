package org.eve.infra.platforms.web

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
import org.eve.domain.platforms.entities.Platform
import org.eve.domain.platforms.usecase.PlatformUseCase
import java.util.UUID

@Path("/platforms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class PlatformWebService(
    private val platformUseCase: PlatformUseCase
) {
    @POST
    @Path("/{projectUUID}")
    fun createPlatform(
        platform: Platform,
        @PathParam(value = "projectUUID") projectUUID: UUID,
    ): Response {
        val response = platformUseCase.createPlatform(platform, projectUUID)

        if (response.error != null) {
            return Response.status(
                Response.Status.BAD_REQUEST
            ).entity(response).build()
        }

        return Response.ok(response).build()
    }

    @PUT
    fun updatePlatform(platform: Platform): Response {
        val response = platformUseCase.updatePlatform(platform)

        if (response.error != null) {
            return Response.status(
                Response.Status.BAD_REQUEST
            ).entity(response).build()
        }

        return Response.ok(response).build()
    }

    @GET
    @Path("/uuid/{uuid}")
    fun getPlatformByUUID(@PathParam(value = "uuid") uuid: UUID): Response {
        val response = platformUseCase.getPlatformByUUID(uuid)

        if (response.error != null) {
            return Response.status(
                Response.Status.BAD_REQUEST
            ).entity(response).build()
        }

        return Response.ok(response).build()
    }

    @GET
    fun getPaginatedPlatforms(
        @QueryParam(value = "page") page: Int,
        @QueryParam(value = "count") count: Int
    ): Response {
        val response = platformUseCase.getPaginatedPlatforms(page, count)

        if (response.error != null) {
            return Response.status(
                Response.Status.BAD_REQUEST
            ).entity(response).build()
        }

        return Response.ok(response).build()
    }

    @GET
    @Path("/all/{uuid}")
    fun getAllPlatforms(
        @PathParam(value = "uuid") uuid: UUID
    ): Response {
        val response = platformUseCase.getAllPlatforms(uuid)

        if (response.error != null) {
            return Response.status(
                Response.Status.BAD_REQUEST
            ).entity(response).build()
        }

        return Response.ok(response).build()
    }
}
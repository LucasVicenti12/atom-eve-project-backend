package org.eve.infra.auth.web

import jakarta.ws.rs.Consumes
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eve.domain.auth.entities.Login
import org.eve.domain.auth.usecase.AuthUseCase
import org.eve.infra.auth.service.CookieService

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class AuthWebService(
    private val authUseCase: AuthUseCase,
    private val cookieService: CookieService
) {
    @POST
    @Path("/login")
    fun login(login: Login): Response {
        val response = authUseCase.login(login)

        if (response.error != null) {
            return Response.status(
                Response.Status.BAD_REQUEST
            ).entity(response).build()
        }

        val cookie = cookieService.generateCookieFromToken(
            token = response.data!!
        )

        return Response.ok(response).cookie(cookie).build()
    }

    @POST
    @Path("/logout")
    fun logout(): Response {
        val cookie = cookieService.cleanCookie()

        return Response.noContent()
            .cookie(cookie)
            .build()
    }

    @GET
    @Path("/me")
    fun me(): Response {
        val response = authUseCase.me()

        if (response.error != null) {
            return Response.status(
                Response.Status.BAD_REQUEST
            ).entity(response).build()
        }

        return Response.ok(response).build()
    }
}
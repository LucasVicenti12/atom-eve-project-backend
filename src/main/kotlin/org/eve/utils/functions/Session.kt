package org.eve.utils.functions

import io.quarkus.security.identity.SecurityIdentity
import jakarta.enterprise.context.ApplicationScoped
import jakarta.ws.rs.container.ContainerRequestContext
import jakarta.ws.rs.core.Context
import org.eve.domain.projects.entities.Project
import org.eve.domain.users.entities.User

@ApplicationScoped
class Session(
    private val identity: SecurityIdentity,
){
    @Context
    private lateinit var request: ContainerRequestContext

    fun getUser(): User = identity.getAttribute("user")

    fun getProject(): Project = request.getProperty("project") as Project
}
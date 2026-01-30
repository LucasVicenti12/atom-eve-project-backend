package org.eve.utils.functions

import io.quarkus.security.identity.SecurityIdentity
import jakarta.enterprise.context.ApplicationScoped
import org.eve.domain.projects.entities.Project
import org.eve.domain.users.entities.User

@ApplicationScoped
class Session(
    private val identity: SecurityIdentity,
){
    fun getUser(): User = identity.getAttribute("user")

    fun getProject(): Project = identity.getAttribute("project")
}
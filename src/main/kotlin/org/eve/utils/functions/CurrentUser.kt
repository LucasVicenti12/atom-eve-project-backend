package org.eve.utils.functions

import io.quarkus.security.identity.SecurityIdentity
import jakarta.enterprise.context.ApplicationScoped
import org.eve.domain.users.entities.User

@ApplicationScoped
class CurrentUser(
    private val identity: SecurityIdentity
){
    fun get(): User = identity.getAttribute("user")
}
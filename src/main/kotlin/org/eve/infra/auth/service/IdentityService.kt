package org.eve.infra.auth.service

import io.quarkus.security.AuthenticationFailedException
import io.quarkus.security.identity.AuthenticationRequestContext
import io.quarkus.security.identity.SecurityIdentity
import io.quarkus.security.identity.SecurityIdentityAugmentor
import io.quarkus.security.runtime.QuarkusSecurityIdentity
import io.smallrye.mutiny.Uni
import io.vertx.ext.web.RoutingContext
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.eve.domain.projects.entities.Project
import org.eve.domain.users.repository.UserRepository

@ApplicationScoped
class IdentityService(
    private val userRepository: UserRepository,
) : SecurityIdentityAugmentor {
    @Inject
    private lateinit var routingContext: RoutingContext

    override fun augment(
        identity: SecurityIdentity,
        context: AuthenticationRequestContext
    ): Uni<SecurityIdentity> {
        if (identity.isAnonymous) {
            return Uni.createFrom().item(identity)
        }

        return context.runBlocking {
            val builder = QuarkusSecurityIdentity.builder(identity)

            val user = userRepository.getUserByUserName(identity.principal.name)
                ?: throw AuthenticationFailedException("user not found")

            user.password = null

            builder.addAttribute("user", user)

            val project = routingContext.get<Project>("project")

            if (project != null) {
                builder.addAttribute("project", project)
            }

            builder.build()
        }
    }
}
package org.eve.infra.platforms.database

import jakarta.enterprise.context.ApplicationScoped
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.eve.domain.platforms.entities.Platform
import org.eve.infra.projects.database.ProjectJPA
import org.eve.utils.jpa.EveBaseJPA
import java.util.UUID

@Entity
@Table(name = "platforms")
class PlatformJPA {
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.UUID)
    var uuid: UUID? = null

    @field:Column(name = "name", nullable = false, length = 70)
    var name: String? = null

    @field:Column(name = "color", nullable = false, length = 10)
    var color: String? = null

    @field:ManyToOne(fetch = FetchType.LAZY)
    @field:JoinColumn(name = "project_uuid", nullable = false)
    var project: ProjectJPA? = null

    fun toPlatform(): Platform = Platform(
        uuid = this.uuid,
        name = this.name!!,
        color = this.color!!,
    )
}

@ApplicationScoped
class PlatformRepositoryJPA : EveBaseJPA<PlatformJPA, UUID>() {
    fun updatePlatform(platform: Platform) {
        this.update(
            "name = :name, color = :color WHERE uuid = :uuid",
            mapOf(
                "name" to platform.name,
                "color" to platform.color,
                "uuid" to platform.uuid
            )
        )
    }
}
package org.eve.domain.platforms.entities

import org.eve.domain.projects.entities.Project
import java.util.UUID

class Platform(
    var uuid: UUID? = null,
    var name: String? = "",
    var color: String? = "",
    var project: Project? = null
)
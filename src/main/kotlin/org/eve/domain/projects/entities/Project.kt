package org.eve.domain.projects.entities

import java.util.UUID

class Project (
    var uuid: UUID? = null,
    var title: String? = "",
    var description: String? = "",
    var color: String? = "",
)
package org.eve.utils.exceptions

import org.eve.utils.entities.DefaultError

val UNEXPECTED_ERROR = DefaultError("UNEXPECTED_ERROR", "An unexpected error has occurred")
val PROJECT_IS_REQUIRED = DefaultError("PROJECT_IS_REQUIRED", "Project is required for this request")
val PROJECT_NOT_FOUND = DefaultError("PROJECT_NOT_FOUND", "Project not found")
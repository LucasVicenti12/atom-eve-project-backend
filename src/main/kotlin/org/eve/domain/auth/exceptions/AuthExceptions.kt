package org.eve.domain.auth.exceptions

import org.eve.utils.entities.DefaultError

val AUTH_LOGIN_IS_EMPTY = DefaultError("AUTH_LOGIN_IS_EMPTY", "Login is empty")
val AUTH_PASSWORD_IS_EMPTY = DefaultError("AUTH_PASSWORD_IS_EMPTY", "Password is empty")
val AUTH_INVALID_CREDENTIALS = DefaultError("AUTH_INVALID_CREDENTIALS", "Invalid credentials")
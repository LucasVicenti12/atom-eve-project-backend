package org.eve.domain.users.exceptions

import org.eve.utils.entities.DefaultError

val USER_NOT_FOUND = DefaultError("USER_NOT_FOUND", "User not found")
val USER_USERNAME_IS_EMPTY = DefaultError("USER_USERNAME_IS_EMPTY", "User username is empty")
val USER_EMAIL_IS_EMPTY = DefaultError("USER_EMAIL_IS_EMPTY", "User email is empty")
val USER_NAME_IS_EMPTY = DefaultError("USER_NAME_IS_EMPTY", "User name is empty")
val USER_PASSWORD_IS_EMPTY = DefaultError("USER_PASSWORD_IS_EMPTY", "User password is empty")
val USER_ALREADY_EXISTS = DefaultError("USER_ALREADY_EXISTS", "User already exists")
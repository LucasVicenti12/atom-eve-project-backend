package org.eve.utils.entities

import jakarta.inject.Singleton
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Singleton
class PasswordEncoder: BCryptPasswordEncoder()
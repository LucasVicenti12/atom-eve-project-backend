package org.eve.utils.annotations

import jakarta.ws.rs.NameBinding

@NameBinding
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ProjectRequired
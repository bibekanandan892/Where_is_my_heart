package com.petpack.whereismyheart.data.model.auth.heart_status
import kotlinx.serialization.Serializable

@Serializable
data class LoginTokenResponse (
    val token: String? = null,
)


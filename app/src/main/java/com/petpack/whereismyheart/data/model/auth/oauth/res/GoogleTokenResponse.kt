package com.petpack.whereismyheart.data.model.auth.oauth.res

import kotlinx.serialization.Serializable

@Serializable
data class GoogleTokenResponse(
    val message: String? = null,
    val response: Response? = null,
    val success: Boolean? = null
)
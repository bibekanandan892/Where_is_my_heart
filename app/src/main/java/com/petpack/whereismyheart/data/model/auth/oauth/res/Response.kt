package com.petpack.whereismyheart.data.model.auth.oauth.res

import kotlinx.serialization.Serializable

@Serializable
data class Response(
    val token: String?
)
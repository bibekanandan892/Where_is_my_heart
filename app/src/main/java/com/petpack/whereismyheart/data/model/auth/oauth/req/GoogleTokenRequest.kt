package com.petpack.whereismyheart.data.model.auth.oauth.req

import kotlinx.serialization.Serializable

@Serializable
data class GoogleTokenRequest(
    val tokenId: String,
    val fcmToken : String? = null,
)
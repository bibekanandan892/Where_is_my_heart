package com.petpack.whereismyheart.data.model.auth.heart_status.res

@kotlinx.serialization.Serializable
data class HeartStatusResponse(
    val message: String?,
    val response: Response?,
    val success: Boolean?
)
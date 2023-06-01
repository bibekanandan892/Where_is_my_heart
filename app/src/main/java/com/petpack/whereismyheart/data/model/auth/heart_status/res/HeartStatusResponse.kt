package com.petpack.whereismyheart.data.model.auth.heart_status.res
import kotlinx.serialization.Serializable

@Serializable
data class HeartStatusResponse(
    val message: String?,
    val response: Response?,
    val success: Boolean?
)
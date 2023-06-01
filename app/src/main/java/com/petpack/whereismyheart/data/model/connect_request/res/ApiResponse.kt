package com.petpack.whereismyheart.data.model.connect_request.res

import kotlinx.serialization.Serializable
@Serializable
data class ApiResponse(
    val success: Boolean = false,
    val response: Response? = null,
    val message: String? = null
)


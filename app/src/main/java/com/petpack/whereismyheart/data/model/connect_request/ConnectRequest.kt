package com.petpack.whereismyheart.data.model.connect_request

import kotlinx.serialization.Serializable

@Serializable
data class ConnectRequest(
    val toHeartId : String? = null
)

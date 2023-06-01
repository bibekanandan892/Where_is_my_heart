package com.petpack.whereismyheart.data.model.user_details

import kotlinx.serialization.Serializable
@Serializable
data class ConnectionRequest(
    val id: String ? = null,
    val subId : String? = null,
    val name: String? = null,
    val emailAddress: String? = null,
    val userHeartId: String?= null,
    val profilePhoto: String? = null,
)

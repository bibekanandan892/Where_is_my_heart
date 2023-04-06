package com.petpack.whereismyheart.data.model.user_details

import kotlinx.serialization.Serializable
@Serializable
data class ConnectionRequest(
    val id: String ? = null,
    val subId : String?,
    val name: String?,
    val emailAddress: String?,
    val userHeartId: String?,
    val profilePhoto: String?,
)

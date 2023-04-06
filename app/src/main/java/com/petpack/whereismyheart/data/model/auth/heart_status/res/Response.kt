package com.petpack.whereismyheart.data.model.auth.heart_status.res

import com.petpack.whereismyheart.data.model.user_details.ConnectionRequest

@kotlinx.serialization.Serializable
data class Response(
    val connectedHeardId: String?,
    val emailAddress: String?,
    val id: String?,
    val listOfConnectRequest: List<ConnectionRequest?>?,
    val name: String?,
    val profilePhoto: String?,
    val subId: String?,
    val userHeartId: String?
)
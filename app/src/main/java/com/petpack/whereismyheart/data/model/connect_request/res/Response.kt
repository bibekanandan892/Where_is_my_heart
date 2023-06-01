package com.petpack.whereismyheart.data.model.connect_request.res
import com.petpack.whereismyheart.data.model.user_details.ConnectionRequest

@kotlinx.serialization.Serializable
data class Response(
    val connectUserPhoto: String?,
    val connectedHeardId: String?,
    val connectedUserEmail: String?,
    val connectedUserName: String?,
    val emailAddress: String?,
    val fcmToken: String?,
    val id: String?,
    val listOfConnectRequest: List<ConnectionRequest?>?,
    val name: String?,
    val profilePhoto: String?,
    val subId: String?,
    val userHeartId: String?
)
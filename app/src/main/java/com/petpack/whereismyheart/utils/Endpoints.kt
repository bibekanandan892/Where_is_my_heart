package com.petpack.whereismyheart.utils

import com.petpack.whereismyheart.utils.Constants.BASE_URL
import com.petpack.whereismyheart.utils.Constants.BASE_URL_IP

sealed class Endpoints(val route : String){
    object TokenVerification: Endpoints(route = "$BASE_URL/token_verification")
    object HeartStatus: Endpoints(route = "$BASE_URL/heart_status")
    object SendProposalRequest: Endpoints(route = "$BASE_URL/send_connect_request")
    object AcceptProposalRequest: Endpoints(route = "$BASE_URL/accept_connect_request")
    object Chatting : Endpoints(route = "$BASE_URL_IP/chat")
    object DisconnectHeart: Endpoints(route = "$BASE_URL/disconnect_heart")

}

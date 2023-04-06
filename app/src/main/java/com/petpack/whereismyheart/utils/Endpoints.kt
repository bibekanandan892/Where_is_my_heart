package com.petpack.whereismyheart.utils

import com.petpack.whereismyheart.utils.Constants.BASE_URL

sealed class Endpoints(val route : String){
    object TokenVerification: Endpoints(route = "$BASE_URL/token_verification")
    object HeartStatus: Endpoints(route = "$BASE_URL/heart_status")
}

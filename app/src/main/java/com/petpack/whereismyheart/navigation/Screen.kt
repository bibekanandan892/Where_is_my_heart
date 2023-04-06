package com.petpack.whereismyheart.navigation

sealed class Screen(val route : String){
    object Authentication: Screen(route = "authentication_screen")
    object HeartStatus: Screen(route = "heart_id_screen")
    object Chat: Screen(route = "chat_screen")
    object HeartLocation: Screen(route = "heart_location_screen")
}

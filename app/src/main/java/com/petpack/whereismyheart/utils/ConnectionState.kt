package com.petpack.whereismyheart.utils

sealed class ConnectionState{
    object Available : ConnectionState()
    object Unavailable : ConnectionState()
}

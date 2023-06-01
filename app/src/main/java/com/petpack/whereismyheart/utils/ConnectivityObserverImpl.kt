package com.petpack.whereismyheart.utils


import android.net.ConnectivityManager
import kotlinx.coroutines.flow.Flow

class ConnectivityObserverImpl(
    private val connectivityManager: ConnectivityManager
) : ConnectivityObserver {
    override val connectionState: Flow<ConnectionState>
        get() = connectivityManager.observeConnectivityAsFlow()
    override val currentConnectionState: ConnectionState
        get() = connectivityManager.currentConnectivityState

}
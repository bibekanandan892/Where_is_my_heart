package com.petpack.whereismyheart

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petpack.whereismyheart.utils.ConnectionState
import com.petpack.whereismyheart.utils.ConnectivityObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val connectivityObserver : ConnectivityObserver) : ViewModel() {
    var isConnectivityAvailable : MutableState<ConnectionState?> = mutableStateOf(null)
        private set

    init {
        observeConnectivity()
    }
    private fun observeConnectivity() {
        connectivityObserver.connectionState
            .distinctUntilChanged()
            .onEach {
                isConnectivityAvailable.value = it
            }
            .launchIn(viewModelScope)
    }
}
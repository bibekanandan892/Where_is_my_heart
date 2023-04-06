package com.petpack.whereismyheart.presentation.screen.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petpack.whereismyheart.domain.usecase.UseCases
import com.petpack.whereismyheart.presentation.screen.auth.state.GoogleTokenState
import com.petpack.whereismyheart.presentation.screen.auth.state.HeartStatusState
import com.petpack.whereismyheart.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthenticationViewModel @Inject constructor(private val useCases: UseCases) : ViewModel() {
    var authenticated = mutableStateOf(false)
        private set
    var loadingState = mutableStateOf(false)
        private set

    fun setLoading(loading: Boolean) {
        loadingState.value = loading
    }

    var jwtTokenState: MutableStateFlow<GoogleTokenState> = MutableStateFlow(GoogleTokenState())
        private set
    var heartStatusState: MutableStateFlow<HeartStatusState> = MutableStateFlow(HeartStatusState())
        private set

    fun getJwtWithGoogleToken(token: String) {
        viewModelScope.launch {
            useCases.tokenAuthenticationUseCase.invoke(token = token).collect { networkResult ->
                jwtTokenState.emit(
                    jwtTokenState.value.copy(
                        googleTokenResponse = null,
                        loading = false,
                        error = null
                    )
                )
                when (networkResult) {
                    is NetworkResult.Error -> {
                        jwtTokenState.emit(
                            jwtTokenState.value.copy(
                                googleTokenResponse = null,
                                loading = false,
                                error = networkResult.message
                            )
                        )
                    }
                    is NetworkResult.Loading -> {
                        jwtTokenState.emit(
                            jwtTokenState.value.copy(
                                googleTokenResponse = null,
                                loading = true,
                                error = null
                            )
                        )
                    }

                    is NetworkResult.Success -> {
                        jwtTokenState.emit(
                            jwtTokenState.value.copy(
                                googleTokenResponse = networkResult.data,
                                loading = false,
                                error = null
                            )
                        )

                    }
                }

            }
        }
    }

    fun saveJwtToken(token: String) {
        useCases.saveJwtTokenUseCase.invoke(token = token)
    }

    fun getJwtToken(): String? {
        return useCases.getJwtTokenUseCase.invoke()
    }

    fun getHeartStatus() {
        viewModelScope.launch {
            useCases.getHeartStatusUseCase.invoke().collect { networkResult ->
                heartStatusState.emit(
                    heartStatusState.value.copy(
                        heartStatusResponse = null,
                        loading = false,
                        error = null
                    )
                )
                when (networkResult) {
                    is NetworkResult.Error -> {
                        heartStatusState.emit(
                            heartStatusState.value.copy(
                                heartStatusResponse = null,
                                loading = false,
                                error = networkResult.message
                            )
                        )
                    }
                    is NetworkResult.Loading -> {
                        heartStatusState.emit(
                            heartStatusState.value.copy(
                                heartStatusResponse = null,
                                loading = true,
                                error = null
                            )
                        )
                    }

                    is NetworkResult.Success -> {
                        heartStatusState.emit(
                            heartStatusState.value.copy(
                                heartStatusResponse = networkResult.data,
                                loading = false,
                                error = null
                            )
                        )

                    }
                }

            }
        }

    }

}
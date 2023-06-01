package com.petpack.whereismyheart.presentation.screen.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petpack.whereismyheart.domain.usecase.UseCases
import com.petpack.whereismyheart.presentation.screen.auth.state.GoogleTokenState
import com.petpack.whereismyheart.presentation.screen.heart.state.HeartStatusState
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
    var heartStatusState: MutableStateFlow<HeartStatusState> = MutableStateFlow(HeartStatusState())
        private set


    var jwtTokenState: MutableStateFlow<GoogleTokenState> = MutableStateFlow(GoogleTokenState())
        private set


    fun getJwtWithGoogleToken(token: String,fcmToken: String) {
        viewModelScope.launch {
            useCases.tokenAuthenticationUseCase.invoke(token = token, fcmToken = fcmToken).collect { networkResult ->
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

    fun saveJwtToken(token: String) {
        useCases.saveJwtTokenUseCase.invoke(token = token)
    }
    fun getJwtToken(): String? {
        return useCases.getJwtTokenUseCase.invoke()
    }


    fun saveConnectedUserHeartId(heartId: String){
        useCases.saveConnectedHeartIdUseCase.invoke(heartId = heartId)
    }
    fun saveConnectUserName(name :String){
        useCases.saveConnectedUserNameUseCase.invoke(name = name)
    }
    fun saveConnectUserEmail(email :String){
        useCases.saveConnectedUserEmailUseCase.invoke(email = email )
    }

    fun saveConnectUserPhoto(photoUrl :String){
        useCases.saveConnectedPhotoUseCase.invoke(photoUrl = photoUrl)
    }

    fun getConnectedHeartId() : String?{
       return useCases.getConnectedHeartIdUseCase.invoke()
    }
    fun saveUserEmail(email: String){
        useCases.saveUserEmailUseCase.invoke(email = email)
    }
    fun saveUserHeartId(heartId : String){
        useCases.saveUserHeartIdUseCase.invoke(heartId = heartId)
    }

    fun saveUserFcm(fcmToken : String){
        useCases.saveFcmUseCase.invoke(fcmToken = fcmToken)
    }
    fun saveUserPhoto(photoUrl: String){
        useCases.saveUserPhotoUseCase.invoke(photoUrl = photoUrl)
    }
    fun saveUserName(name:String){
        useCases.saveUserNameUseCase.invoke(name = name)
    }
    fun getUserHeartId() : String?{
        return useCases.getUserHeartIdUseCase.invoke()
    }
    fun getUserFcm() : String?{
        return useCases.getFcmUseCase.invoke()
    }

}
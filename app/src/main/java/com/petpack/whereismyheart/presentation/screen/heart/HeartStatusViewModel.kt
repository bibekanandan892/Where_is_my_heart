package com.petpack.whereismyheart.presentation.screen.heart

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petpack.whereismyheart.data.model.connect_request.ConnectRequest
import com.petpack.whereismyheart.data.model.user_details.ConnectionRequest
import com.petpack.whereismyheart.domain.usecase.UseCases
import com.petpack.whereismyheart.presentation.screen.heart.state.HeartStatusState
import com.petpack.whereismyheart.presentation.screen.heart.state.ProposalResponseState
import com.petpack.whereismyheart.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HeartStatusViewModel @Inject constructor(private val useCases: UseCases) : ViewModel() {
    var heartStatusLoadingState = mutableStateOf(false)
        private set
    var proposalResponseLoadingState = mutableStateOf(false)
        private set
    var listOfConnectionRequest = mutableStateOf<MutableList<ConnectionRequest>>(mutableListOf())
        private set

    var heartStatusState: MutableStateFlow<HeartStatusState> = MutableStateFlow(HeartStatusState())
        private set

    var sendProposalState: MutableStateFlow<ProposalResponseState> =
        MutableStateFlow(ProposalResponseState())
        private set
    var acceptProposalState: MutableStateFlow<ProposalResponseState> =
        MutableStateFlow(ProposalResponseState())
        private set

    fun setListOfConnectionRequest(listOfConnection: List<ConnectionRequest>) {
        listOfConnectionRequest.value = listOfConnection.toMutableList()
    }

    init {
        getHeartStatus()
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

    fun sendProposalRequest(connectRequest: ConnectRequest) {
        viewModelScope.launch {
            useCases.sendProposalUseCase.invoke(connectRequest = connectRequest)
                .collect { networkResult ->
                    sendProposalState.emit(
                        sendProposalState.value.copy(
                            apiResponse = null,
                            loading = false,
                            error = null
                        )
                    )
                    when (networkResult) {
                        is NetworkResult.Error -> {
                            sendProposalState.emit(
                                sendProposalState.value.copy(
                                    apiResponse = null,
                                    loading = false,
                                    error = networkResult.message
                                )
                            )
                        }
                        is NetworkResult.Loading -> {
                            sendProposalState.emit(
                                sendProposalState.value.copy(
                                    apiResponse = null,
                                    loading = true,
                                    error = null
                                )
                            )
                        }

                        is NetworkResult.Success -> {
                            sendProposalState.emit(
                                sendProposalState.value.copy(
                                    apiResponse = networkResult.data,
                                    loading = false,
                                    error = null
                                )
                            )

                        }
                    }

                }
        }
    }

    fun acceptProposalRequest(connectRequest: ConnectRequest) {
        viewModelScope.launch {
            useCases.acceptProposalUseCase.invoke(connectRequest = connectRequest)
                .collect { networkResult ->
                    acceptProposalState.emit(
                        acceptProposalState.value.copy(
                            apiResponse = null,
                            loading = false,
                            error = null
                        )
                    )
                    when (networkResult) {
                        is NetworkResult.Error -> {
                            acceptProposalState.emit(
                                acceptProposalState.value.copy(
                                    apiResponse = null,
                                    loading = false,
                                    error = networkResult.message
                                )
                            )
                        }
                        is NetworkResult.Loading -> {
                            acceptProposalState.emit(
                                acceptProposalState.value.copy(
                                    apiResponse = null,
                                    loading = true,
                                    error = null
                                )
                            )
                        }

                        is NetworkResult.Success -> {
                            acceptProposalState.emit(
                                acceptProposalState.value.copy(
                                    apiResponse = networkResult.data,
                                    loading = false,
                                    error = null
                                )
                            )

                        }
                    }

                }
        }
    }

    fun getUserHeartId(): String? {
        return useCases.getUserHeartIdUseCase.invoke()
    }

    fun getUserName(): String? {
        return useCases.getUserNameUseCase.invoke()
    }

    fun getUserEmail(): String? {
        return useCases.getUserEmailUseCase.invoke()
    }

    fun getUserPhoto(): String? {
        return useCases.getUserPhotoUseCase.invoke()
    }

    fun getConnectHeartId(): String? {
        return useCases.getConnectedHeartIdUseCase.invoke()
    }

    fun saveUserHeartId(heartId: String) {
        useCases.saveUserHeartIdUseCase.invoke(heartId = heartId)
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

    fun saveUserEmail(email: String){
        useCases.saveUserEmailUseCase.invoke(email = email)
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
}
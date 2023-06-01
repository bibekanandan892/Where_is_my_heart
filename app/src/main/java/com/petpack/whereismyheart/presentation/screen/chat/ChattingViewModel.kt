package com.petpack.whereismyheart.presentation.screen.chat

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petpack.whereismyheart.data.model.chat.MessageEntity
import com.petpack.whereismyheart.data.model.chat.Payload
import com.petpack.whereismyheart.data.model.connect_request.ConnectRequest
import com.petpack.whereismyheart.data.model.read_receipt.MessageIdResponse
import com.petpack.whereismyheart.domain.repository.ChatSocketService
import com.petpack.whereismyheart.domain.repository.ReadReceiptService
import com.petpack.whereismyheart.domain.usecase.UseCases
import com.petpack.whereismyheart.presentation.screen.chat.state.ChatState
import com.petpack.whereismyheart.presentation.screen.chat.state.DisconnectHeartState
import com.petpack.whereismyheart.utils.ConnectionState
import com.petpack.whereismyheart.utils.ConnectivityObserver
import com.petpack.whereismyheart.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class ChattingViewModel @Inject constructor(
    private val useCases: UseCases,
    private val chatSocketService: ChatSocketService,
    private val readReceiptService: ReadReceiptService,
    private val connectivityObserver: ConnectivityObserver,
) : ViewModel() {

    val json = Json {
        ignoreUnknownKeys = true
    }
    var disconnectHeartState: MutableStateFlow<DisconnectHeartState> =
        MutableStateFlow(DisconnectHeartState())
        private set


    private val _state = mutableStateOf(ChatState())
    val state: State<ChatState> = _state

    private val _toastEvent = MutableSharedFlow<String>()
    val toastEvent = _toastEvent.asSharedFlow()

    fun getUserHeartId() = useCases.getUserHeartIdUseCase.invoke()


    fun getUserName() = useCases.getUserNameUseCase.invoke()

    fun getUserEmail() = useCases.getUserEmailUseCase.invoke()


    fun getUserPhoto() = useCases.getUserPhotoUseCase.invoke()


    fun getConnectHeartId() = useCases.getConnectedHeartIdUseCase.invoke()

    fun getConnectUserName() = useCases.getConnectedUserNameUseCase.invoke()

    fun getConnectUserEmail() = useCases.getConnectedUserEmailUseCase.invoke()
    fun getConnectUserPhoto() = useCases.getConnectedPhotoUseCase.invoke()

    fun clearConnectedHeartRequest() {
//        useCases.saveJwtTokenUseCase.invoke(token = "")
        useCases.saveConnectedHeartIdUseCase.invoke("")
        useCases.saveConnectedPhotoUseCase.invoke("")
        useCases.saveConnectedUserEmailUseCase.invoke("")
        useCases.saveConnectedUserNameUseCase.invoke("")
    }

    fun clearAllDetails() {
        viewModelScope.launch {
            useCases.deleteAllChatsUseCase.invoke()
        }
        useCases.saveConnectedPhotoUseCase.invoke("")
        useCases.saveConnectedUserEmailUseCase.invoke("")
        useCases.saveConnectedHeartIdUseCase.invoke("")
        useCases.saveConnectedUserNameUseCase.invoke("")
    }

    fun getAllChats(): Flow<List<MessageEntity?>?> {
        return chatSocketService.getAllChats()
    }

    fun connectToChat() {
        viewModelScope.launch {
            when (val result = chatSocketService.initSession()) {
                is NetworkResult.Success -> {
                    withContext(Dispatchers.IO) {
                        val unreadMessageTimeStampList =
                            chatSocketService.getUnreadMessage().map { messageEntity ->
                                messageEntity?.timestamp
                            }
                        val receiptPayload = Payload(
                            messageIdResponse = MessageIdResponse(
                                messageIdList = unreadMessageTimeStampList
                            )
                        )
                        val payloadString = json.encodeToString(receiptPayload)
                        chatSocketService.sendMessage(payloadString)
                    }
                    withContext(Dispatchers.IO) {
                        val unSentMessageList = chatSocketService.getUnSentMessage()
                        unSentMessageList.forEach {
                            val newMessageEntity = MessageEntity(
                                isMine = false,
                                toUserHeartId = it.toUserHeartId ?: "NA",
                                fromUserHeartId = it.fromUserHeartId ?: "NA",
                                message = it.message,
                                timestamp = it.timestamp,
                            )
                            chatSocketService.sendMessage(
                                isRetry = true,
                                messageEntity = newMessageEntity
                            )
                        }
                    }
                    chatSocketService.observeMessages()
                        .onEach { payload ->
                            payload.messageEntity?.let { messageEntity ->
                                chatSocketService.insertChat(messageEntity = messageEntity)
                                val receiptPayload = Payload(
                                    messageIdResponse = MessageIdResponse(
                                        messageIdList = listOf(messageEntity.timestamp)
                                    )
                                )
                                val payloadString = json.encodeToString(receiptPayload)
                                chatSocketService.sendMessage(payloadString)
                            }
                            payload.messageIdResponse?.let { messageIdResponse ->
                                messageIdResponse.messageIdList?.forEach { timeStamp ->
                                    timeStamp?.let {
                                        withContext(Dispatchers.IO) {
                                            val messageEntityList =
                                                readReceiptService.getMessagesByRecipient(
                                                    timestamp = it,
                                                    isMine = true
                                                )
                                            messageEntityList.forEach { messageEntity ->
                                                messageEntity.messageStatus = "R"
                                                messageEntity.isReadReceiptSent = true
                                                readReceiptService.update(messageEntity = messageEntity)
                                            }
                                        }

                                    }
                                }
                            }

                        }.launchIn(viewModelScope)
                }

                is NetworkResult.Error -> {
                    _toastEvent.emit(result.message ?: "Unknown error")
                }

                is NetworkResult.Loading -> {}
            }
        }
    }


    fun sendMessage(message: String) {
        viewModelScope.launch {
            if (message.isNotBlank()) {
                val messageEntity = MessageEntity(
                    isMine = false,
                    toUserHeartId = useCases.getConnectedHeartIdUseCase.invoke() ?: "NA",
                    fromUserHeartId = useCases.getUserHeartIdUseCase.invoke() ?: "NA",
                    message = message,
                    timestamp = System.currentTimeMillis(),
                )
                chatSocketService.sendMessage(messageEntity = messageEntity)

            }
        }
    }

    fun disconnectRequest(connectRequest: ConnectRequest) {
        viewModelScope.launch {
            useCases.disconnectHeartUseCase.invoke(connectRequest = connectRequest)
                .collect { networkResult ->
                    disconnectHeartState.emit(
                        disconnectHeartState.value.copy(
                            apiResponse = null,
                            loading = false,
                            error = null
                        )
                    )
                    when (networkResult) {
                        is NetworkResult.Error -> {
                            disconnectHeartState.emit(
                                disconnectHeartState.value.copy(
                                    apiResponse = null,
                                    loading = false,
                                    error = networkResult.message
                                )
                            )
                        }

                        is NetworkResult.Loading -> {
                            disconnectHeartState.emit(
                                disconnectHeartState.value.copy(
                                    apiResponse = null,
                                    loading = true,
                                    error = null
                                )
                            )
                        }

                        is NetworkResult.Success -> {
                            disconnectHeartState.emit(
                                disconnectHeartState.value.copy(
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
//    init {
//        observeConnectivity()
//    }

    fun observeConnectivity() {
        connectivityObserver.connectionState
            .distinctUntilChanged().onEach {
                when (it) {
                    ConnectionState.Available -> {
                        delay(2000)
                        connectToChat()
                    }

                    ConnectionState.Unavailable -> {
                        disconnect()
                    }
                }
            }
            .launchIn(viewModelScope)

    }

    override fun onCleared() {
        super.onCleared()
        disconnect()
    }

    fun disconnect() {
        viewModelScope.launch {
            chatSocketService.closeSession()
        }
    }


}
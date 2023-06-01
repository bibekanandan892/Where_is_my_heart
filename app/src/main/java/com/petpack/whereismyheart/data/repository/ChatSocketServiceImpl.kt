package com.petpack.whereismyheart.data.repository

import com.petpack.whereismyheart.data.local.ChatsDao
import com.petpack.whereismyheart.data.model.chat.MessageEntity
import com.petpack.whereismyheart.data.model.chat.Payload
import com.petpack.whereismyheart.domain.repository.ChatSocketService
import com.petpack.whereismyheart.utils.Constants.BASE_URL_IP
import com.petpack.whereismyheart.utils.Constants.PORT_NO
import com.petpack.whereismyheart.utils.NetworkResult
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.isActive
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ChatSocketServiceImpl(
    private val httpClient: HttpClient,
    private val chatsDao: ChatsDao
) : ChatSocketService {

     private var socket: WebSocketSession? = null
    override suspend fun initSession(): NetworkResult<Unit> {
        return try {
            socket = httpClient.webSocketSession(
                host = BASE_URL_IP,
                port = PORT_NO,
                path = "/chat"
            )
            if (socket?.isActive == true) {
                NetworkResult.Success(Unit)
            } else NetworkResult.Error(message = "Couldn't establish a connection.")
        } catch (e: Exception) {
            e.printStackTrace()
            NetworkResult.Error(message = e.localizedMessage ?: "Unknown Error", data = null)
        }
    }

    override suspend fun sendMessage(message: String) {

        try {
            socket?.send(Frame.Text(message))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun sendMessage(isRetry : Boolean,messageEntity: MessageEntity) {
        if (socket?.isActive == true) {
            if(!isRetry){
                val newMessage = messageEntity.copy(isMine = true).copy(messageStatus = "S")
                insertChat(messageEntity = newMessage)
            }
            val payload = Payload(messageEntity = messageEntity)
            val payloadString = Json.encodeToString(payload)
            sendMessage(payloadString)
        } else {
            val newMessage = messageEntity.copy(isMine = true).copy(messageStatus = "N")
            insertChat(messageEntity = newMessage)
        }

    }

    override fun observeMessages(): Flow<Payload> {
        return try {
            socket?.incoming
                ?.receiveAsFlow()
                ?.filter { it is Frame.Text }
                ?.map {
                    val json = (it as? Frame.Text)?.readText() ?: ""
                    val payload = Json.decodeFromString<Payload>(json)
                    payload
                } ?: flow { }
        } catch (e: Exception) {
            e.printStackTrace()
            flow { }
        }
    }

    override suspend fun closeSession() {
        socket?.close()
    }

    override suspend fun insertChat(messageEntity: MessageEntity) {
        chatsDao.insertChat(messageEntity = messageEntity)
    }


    override fun getAllChats(): Flow<List<MessageEntity?>?> {
        return chatsDao.getAllChats()
    }

    override suspend fun getUnreadMessage(): List<MessageEntity> {
        return chatsDao.getUnreadMessages(isMine = false, isReadReceiptSent = false)
    }

    override suspend fun getUnSentMessage(): List<MessageEntity>{
        return chatsDao.getUnSendMessages(isMine =  true, messageStatus = "N")
    }

    override suspend fun deleteAllChat(){
        chatsDao.deleteAll()
    }

}
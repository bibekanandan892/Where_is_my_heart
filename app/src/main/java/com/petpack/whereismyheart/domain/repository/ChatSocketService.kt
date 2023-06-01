package com.petpack.whereismyheart.domain.repository

import com.petpack.whereismyheart.data.model.chat.MessageEntity
import com.petpack.whereismyheart.data.model.chat.Payload
import com.petpack.whereismyheart.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

interface ChatSocketService {

    suspend fun initSession(): NetworkResult<Unit>

    suspend fun sendMessage(message: String)
    suspend fun sendMessage(isRetry : Boolean = false,messageEntity: MessageEntity)

    fun observeMessages(): Flow<Payload>

    suspend fun closeSession()


    suspend fun insertChat(messageEntity: MessageEntity)
    fun getAllChats(): Flow<List<MessageEntity?>?>

    suspend fun getUnreadMessage(): List<MessageEntity?>

    suspend fun deleteAllChat()
     suspend fun getUnSentMessage(): List<MessageEntity>


}
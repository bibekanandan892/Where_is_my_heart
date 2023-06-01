package com.petpack.whereismyheart.domain.repository

import com.petpack.whereismyheart.data.model.chat.MessageEntity
import com.petpack.whereismyheart.data.model.read_receipt.MessageIdResponse
import com.petpack.whereismyheart.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

interface ReadReceiptService {
    suspend fun initSession(): NetworkResult<Unit>

    suspend fun sendReceipt(messageIdResponse: String)

    fun observeReceipt(): Flow<MessageIdResponse>

    suspend fun closeSession()

    suspend fun update(messageEntity: MessageEntity)

   suspend fun  getMessagesByRecipient(timestamp : Long , isMine: Boolean = true): List<MessageEntity>
}
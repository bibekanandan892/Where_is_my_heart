package com.petpack.whereismyheart.domain.usecase.chat_usecase

import com.petpack.whereismyheart.data.model.chat.MessageEntity
import com.petpack.whereismyheart.domain.repository.ChatSocketService

class InsertChatUseCase (private val chatSocketService: ChatSocketService) {
    suspend operator fun invoke(messageEntity: MessageEntity) {
        chatSocketService.insertChat(messageEntity = messageEntity)
    }
}
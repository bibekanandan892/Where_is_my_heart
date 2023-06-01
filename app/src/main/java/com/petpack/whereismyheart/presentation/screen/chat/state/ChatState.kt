package com.petpack.whereismyheart.presentation.screen.chat.state

import com.petpack.whereismyheart.data.model.chat.MessageEntity

data class ChatState(
    val messages: List<MessageEntity> = emptyList(),
    val isLoading: Boolean = false
)

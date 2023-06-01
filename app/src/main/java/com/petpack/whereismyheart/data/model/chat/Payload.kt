package com.petpack.whereismyheart.data.model.chat

import com.petpack.whereismyheart.data.model.read_receipt.MessageIdResponse
import kotlinx.serialization.Serializable

@Serializable
data class Payload(
    val messageEntity: MessageEntity? = null,
    val messageIdResponse: MessageIdResponse? = null
)

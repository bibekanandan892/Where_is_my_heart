package com.petpack.whereismyheart.data.model.chat

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "Chat_Table")
data class MessageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int= 0,
    val isMine: Boolean = false,
    val fromUserHeartId: String,
    val toUserHeartId: String,
    val message: String,
    val image: String? = null,
    val timestamp: Long,
    var messageStatus : String? = null,
    var isReadReceiptSent: Boolean = false

)

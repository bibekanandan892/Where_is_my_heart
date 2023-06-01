package com.petpack.whereismyheart.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.petpack.whereismyheart.data.model.chat.MessageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatsDao {

    @Query("SELECT * from Chat_Table")
    fun getAllChats(): Flow<List<MessageEntity?>?>

    @Insert(onConflict = REPLACE)
    suspend fun addAllChats(listOfMessageEntity : List<MessageEntity>)

    @Insert(onConflict = REPLACE)
    suspend fun insertChat(messageEntity: MessageEntity)

    @Query("DELETE FROM Chat_Table")
    suspend fun deleteAll()

    @Update
    suspend fun update(messageEntity: MessageEntity)

    @Query("SELECT * FROM Chat_Table WHERE timestamp = :timestamp and isMine = :isMine")
    fun getMessagesByRecipient(timestamp : Long , isMine: Boolean = false): List<MessageEntity>


    @Query("SELECT * FROM Chat_Table WHERE isMine = :isMine and isReadReceiptSent = :isReadReceiptSent")
    fun getUnreadMessages(isMine: Boolean = false,isReadReceiptSent : Boolean = false): List<MessageEntity>


    @Query("SELECT * FROM Chat_Table WHERE messageStatus = :messageStatus and isMine = :isMine")
    fun getUnSendMessages(messageStatus : String = "N",isMine: Boolean = true): List<MessageEntity>

}
package com.petpack.whereismyheart.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.petpack.whereismyheart.data.model.chat.MessageEntity

@Database(entities = [MessageEntity::class], version = 1, exportSchema = false)
abstract class AppDataBase: RoomDatabase() {
    abstract fun chatDao(): ChatsDao
}
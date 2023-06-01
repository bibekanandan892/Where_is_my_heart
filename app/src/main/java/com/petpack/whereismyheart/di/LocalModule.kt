package com.petpack.whereismyheart.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.petpack.whereismyheart.data.local.AppDataBase
import com.petpack.whereismyheart.data.local.ChatsDao
import com.petpack.whereismyheart.utils.ConnectivityObserver
import com.petpack.whereismyheart.utils.ConnectivityObserverImpl
import com.petpack.whereismyheart.utils.connectivityManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object LocalModule {

    @Singleton
    @Provides
    @Synchronized
    fun provideAppDatabase(@ApplicationContext context: Context): AppDataBase =
        Room.databaseBuilder(
            context,
            AppDataBase::class.java,
            "Where Is My Heart Database"
        )
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    @Synchronized
    fun provideRechargePayBillDao(appDataBase: AppDataBase): ChatsDao = appDataBase.chatDao()


}
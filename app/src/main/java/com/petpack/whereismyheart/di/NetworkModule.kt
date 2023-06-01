package com.petpack.whereismyheart.di

import android.app.Application
import android.content.Context
import com.petpack.whereismyheart.data.local.AppDataBase
import com.petpack.whereismyheart.data.local.ChatsDao

import com.petpack.whereismyheart.data.repository.ChatSocketServiceImpl
import com.petpack.whereismyheart.data.repository.DataStoreOperationsImpl
import com.petpack.whereismyheart.data.repository.ReadReceiptServiceImpl
import com.petpack.whereismyheart.data.repository.UserRepositoryImpl
import com.petpack.whereismyheart.domain.repository.ChatSocketService
import com.petpack.whereismyheart.domain.repository.DataStoreOperations
import com.petpack.whereismyheart.domain.repository.ReadReceiptService
import com.petpack.whereismyheart.domain.repository.UserRepository
import com.petpack.whereismyheart.utils.ConnectivityObserver
import com.petpack.whereismyheart.utils.ConnectivityObserverImpl
import com.petpack.whereismyheart.utils.connectivityManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.kotlinx.serializer.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule{
    @Singleton
    @Provides
    fun provideHttpClient(dataStoreOperations: DataStoreOperations): HttpClient {


        return HttpClient(CIO) {
            install(WebSockets)
            install(ContentNegotiation) {
                json(contentType = ContentType.Application.Json)
            }
             install(HttpTimeout){
                 socketTimeoutMillis = 60000
                 requestTimeoutMillis = 60000
                 connectTimeoutMillis = 60000
             }
             defaultRequest {
                 contentType(ContentType.Application.Json)
                 accept(ContentType.Application.Json)
                 header("Authorization", "Bearer ${dataStoreOperations.getToken().toString()}")
                 header("userHeartId", value = "${dataStoreOperations.getUserHeartId()}")
             }
             install(Logging){
                 logger = Logger.ANDROID
                 level = LogLevel.ALL
             }
        }
    }
    @Singleton
    @Provides
    fun provideDataStoreOperation(@ApplicationContext context: Context): DataStoreOperations{
        return DataStoreOperationsImpl(context = context)
    }
    @Singleton
    @Provides
    fun provideChattingService(httpClient: HttpClient,chatsDao: ChatsDao):ChatSocketService{
        return ChatSocketServiceImpl(httpClient = httpClient, chatsDao = chatsDao)
    }

    @Singleton
    @Provides
    fun provideUserRepository(httpClient: HttpClient,dataStoreOperations: DataStoreOperations):UserRepository{
        return UserRepositoryImpl(httpClient = httpClient, dataStoreOperations = dataStoreOperations)
    }
    @Singleton
    @Provides
    fun provideReadReceiptServices(httpClient: HttpClient,chatsDao: ChatsDao):ReadReceiptService{
        return ReadReceiptServiceImpl(httpClient = httpClient, chatsDao = chatsDao)
    }

    @Singleton
    @Provides
    fun provideConnectivityObserver(application: Application): ConnectivityObserver {
        return ConnectivityObserverImpl(application.connectivityManager)
    }

}

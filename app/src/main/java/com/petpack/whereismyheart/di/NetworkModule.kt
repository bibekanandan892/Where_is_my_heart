package com.petpack.whereismyheart.di

import android.content.Context
import android.util.Log
import com.petpack.whereismyheart.data.remote.UserService
import com.petpack.whereismyheart.data.remote.UserServiceImpl
import com.petpack.whereismyheart.data.repository.DataStoreOperationsImpl
import com.petpack.whereismyheart.data.repository.UserRepositoryImpl
import com.petpack.whereismyheart.domain.repository.DataStoreOperations
import com.petpack.whereismyheart.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.auth.*
import io.ktor.client.features.auth.providers.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.websocket.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.Json
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule{
    @Singleton
    @Provides
    fun provideHttpClient(dataStoreOperations: DataStoreOperations): HttpClient {
        val json = Json {
            encodeDefaults= true
            ignoreUnknownKeys = true
            isLenient = true
        }

        return HttpClient(CIO) {
            install(WebSockets)
            install(JsonFeature) {
                serializer = KotlinxSerializer(json)

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
             }

             install(Logging){
                 logger = object: Logger{
                     override fun log(message: String) {
                         Log.d("network", "log: $message")
                     }
                 }
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
    fun provideUserRepository(httpClient: HttpClient,dataStoreOperations: DataStoreOperations):UserRepository{
        return UserRepositoryImpl(httpClient = httpClient, dataStoreOperations = dataStoreOperations)
    }
}

package com.petpack.whereismyheart.utils

import android.util.Log
import io.ktor.client.call.*
import io.ktor.client.network.sockets.*
import io.ktor.client.plugins.*
import io.ktor.network.sockets.SocketTimeoutException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import java.io.IOException

fun <T> handleResponse(call: suspend () -> T): Flow<NetworkResult<T>> {
    return flow {
        emit(NetworkResult.Loading())
        try {
            val response = call.invoke()
            emit(NetworkResult.Success(response))
        } catch (e: ClientRequestException) {
            val errorObj = JSONObject(e.response.body<String>())
            if(errorObj.has("message")){
                val errorMessagesObj = errorObj.getString("message")
                emit(NetworkResult.Error(errorMessagesObj.toString()))
            }else{
                emit(NetworkResult.Error(e.response.status.description))
            }
        } catch (e: ServerResponseException) {
            val errorObj = JSONObject(e.response.body<String>())
            if(errorObj.has("message")){
                val errorMessagesObj = errorObj.getString("message")
                emit(NetworkResult.Error(errorMessagesObj.toString()))
            }else{
                emit(NetworkResult.Error(e.response.status.description))
            }
        } catch (e: RedirectResponseException) {
            Log.d("handleResponse", "handleResponse: ${e.response.body<String>()}")
            val errorObj = JSONObject(e.response.body<String>())
            if(errorObj.has("message")){
                val errorMessagesObj = errorObj.getString("message")
                emit(NetworkResult.Error(errorMessagesObj.toString()))
            }else{
                emit(NetworkResult.Error(e.response.status.description))
            }
        } catch (e: ConnectTimeoutException) {
            emit(NetworkResult.Error("Connection Timeout"))
        } catch (e: SocketTimeoutException) {
            emit(NetworkResult.Error("Socket Timeout"))
        } catch (e: IOException) {
            emit(NetworkResult.Error(e.message ?: "Unknown IO Error"))
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message ?: "Unknown Error"))
        }
    }
}
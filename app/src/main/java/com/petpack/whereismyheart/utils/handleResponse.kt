package com.petpack.whereismyheart.utils

import io.ktor.client.features.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.network.sockets.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import io.ktor.network.sockets.ConnectTimeoutException
import org.json.JSONObject

fun <T> handleResponse(call: suspend () -> T): Flow<NetworkResult<T>> {
    return flow {
        emit(NetworkResult.Loading())
        try {
            val response = call.invoke()
            emit(NetworkResult.Success(response))
        } catch (e: HttpRequestTimeoutException) {
            emit(NetworkResult.Error("Request Timeout"))
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
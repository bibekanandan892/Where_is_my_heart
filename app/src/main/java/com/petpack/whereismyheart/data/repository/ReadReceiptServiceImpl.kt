package com.petpack.whereismyheart.data.repository

import android.util.Log
import com.petpack.whereismyheart.data.local.ChatsDao
import com.petpack.whereismyheart.data.model.chat.MessageEntity
import com.petpack.whereismyheart.data.model.read_receipt.MessageIdResponse
import com.petpack.whereismyheart.domain.repository.ReadReceiptService
import com.petpack.whereismyheart.utils.Constants.BASE_URL_IP
import com.petpack.whereismyheart.utils.Constants.PORT_NO
import com.petpack.whereismyheart.utils.Constants.WIMH
import com.petpack.whereismyheart.utils.NetworkResult
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.isActive
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlin.math.log


class ReadReceiptServiceImpl(
    private val httpClient: HttpClient,
    private val chatsDao: ChatsDao,
) : ReadReceiptService {
    private var readReceiptSocket: WebSocketSession? = null
    override suspend fun initSession(): NetworkResult<Unit> {
        return try {
            readReceiptSocket = httpClient.webSocketSession(
                host = BASE_URL_IP,
                port = PORT_NO,
                path = "/read_receipt"
            )
            if (readReceiptSocket?.isActive == true) {
                Log.d(WIMH, "initSession: Connceted success")
                NetworkResult.Success(Unit)
            } else NetworkResult.Error("Couldn't establish a Read receipt connection.")
        } catch (e: Exception) {
            e.printStackTrace()
            NetworkResult.Error(message = e.localizedMessage ?: "Unknown error", data = null)
        }
    }
    override suspend fun sendReceipt(messageIdResponse: String) {
        try {
            Log.d(WIMH, "IN SEND RECICPT FUCIONT $messageIdResponse")
            readReceiptSocket?.send(Frame.Text(messageIdResponse))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun observeReceipt(): Flow<MessageIdResponse> {
        return try {
            val jsonDecoder = Json {
                ignoreUnknownKeys = true
            }
            Log.d(WIMH, "observeReceipt:in observer before incoming $readReceiptSocket")
            readReceiptSocket?.incoming
                ?.receiveAsFlow()
                ?.filter {
                    Log.d(WIMH, "observeReceipt:in FILTER  incoming $it")
                    it is Frame.Text
                }
                ?.map {
                    Log.d(WIMH, "observeReceipt:in observer in coming$it ")
                    val json = (it as? Frame.Text)?.readText() ?: ""
                    val messageIdResponse = jsonDecoder.decodeFromString<MessageIdResponse>(json)
                    messageIdResponse
                } ?: flow {
                Log.d(WIMH, "observeReceipt: IN EMPTY FLOW  ")

            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d(WIMH, "observeReceipt in exception:$e ")
            flow { }
        }
    }

    override suspend fun closeSession() {
        Log.d(WIMH, "closeSession::::: THE RECIPT IS CLOSE SESSION IS CLOSE")
        readReceiptSocket?.close()
    }
    override suspend fun update(messageEntity: MessageEntity) {
        chatsDao.update(messageEntity = messageEntity)
    }
    override suspend fun getMessagesByRecipient(timestamp : Long, isMine: Boolean): List<MessageEntity> {
        return chatsDao.getMessagesByRecipient(timestamp= timestamp, isMine = isMine)
    }


}
package com.petpack.whereismyheart.data.repository

import com.petpack.whereismyheart.data.model.auth.heart_status.res.HeartStatusResponse
import com.petpack.whereismyheart.data.model.auth.oauth.req.GoogleTokenRequest
import com.petpack.whereismyheart.data.model.auth.oauth.res.GoogleTokenResponse
import com.petpack.whereismyheart.data.model.connect_request.ConnectRequest
import com.petpack.whereismyheart.data.model.connect_request.res.ApiResponse
import com.petpack.whereismyheart.data.model.user_details.ConnectionRequest
import com.petpack.whereismyheart.domain.repository.DataStoreOperations
import com.petpack.whereismyheart.domain.repository.UserRepository
import com.petpack.whereismyheart.utils.Endpoints
import com.petpack.whereismyheart.utils.NetworkResult
import com.petpack.whereismyheart.utils.handleResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(private val httpClient: HttpClient,private val dataStoreOperations: DataStoreOperations) : UserRepository {


    override fun tokenAuthentication(token: String,fcmToken: String): Flow<NetworkResult<GoogleTokenResponse>> {
        return handleResponse {
            httpClient.post{
                url(Endpoints.TokenVerification.route)
                setBody( GoogleTokenRequest(tokenId = token, fcmToken = fcmToken))
            }.body()
        }
    }

    override fun heartStatus(): Flow<NetworkResult<HeartStatusResponse>> {
        return handleResponse {
            httpClient.get(Endpoints.HeartStatus.route).body()
        }
    }

    override fun sendProposalRequest(connectRequest: ConnectRequest): Flow<NetworkResult<ApiResponse>> {
        return handleResponse<ApiResponse> {
            httpClient.post(urlString = Endpoints.SendProposalRequest.route){
                setBody(connectRequest)
            }.body()
        }
    }

    override fun acceptProposalRequest(connectRequest: ConnectRequest): Flow<NetworkResult<ApiResponse>> {
        return handleResponse {
            httpClient.post(urlString = Endpoints.AcceptProposalRequest.route){
                setBody(connectRequest)
            }.body()
        }
    }

    override fun disconnectHeart(connectRequest: ConnectRequest): Flow<NetworkResult<ApiResponse>> {
        return handleResponse {
            httpClient.post(urlString = Endpoints.DisconnectHeart.route){
                setBody(body = connectRequest)
            }.body()
        }
    }

    override fun saveJwtToken(token: String) {
        dataStoreOperations.saveToken(token = token)
    }

    override fun getJwtToken(): String? {
        return dataStoreOperations.getToken()
    }

    override fun saveUserHeartId(heartId: String) {
        dataStoreOperations.saveUserHeartId(heartId)
    }

    override fun getUserHeartId(): String? {
        return dataStoreOperations.getUserHeartId()
    }

    override fun saveConnectHeartId(heartId: String) {
        dataStoreOperations.saveConnectHeartId(heartId)
    }

    override fun getConnectHeartId(): String? {
        return dataStoreOperations.getConnectHeartId()
    }

    override fun saveUserName(name: String) {
        dataStoreOperations.saveUserName(name = name)
    }

    override fun getUserName(): String? {
        return dataStoreOperations.getUserName()
    }

    override fun saveUserEmail(email: String) {
        dataStoreOperations.saveUserEmail(email= email)
    }

    override fun getUserEmail(): String? {
        return dataStoreOperations.getUserEmail()
    }

    override fun saveConnectUserName(name: String) {
        dataStoreOperations.saveConnectUserName(name = name)
    }

    override fun getConnectUserName(): String? {
        return dataStoreOperations.getConnectUserName()
    }

    override fun saveConnectUserEmail(email: String) {
        dataStoreOperations.saveConnectUserEmail(email = email)
    }

    override fun getConnectUserEmail(): String? {
        return dataStoreOperations.getConnectUserEmail()
    }

    override fun saveConnectUserPhoto(photoUrl: String) {
        dataStoreOperations.saveConnectUserPhoto(photoUrl = photoUrl)
    }

    override fun getConnectUserPhoto(): String? {
        return dataStoreOperations.getConnectUserPhoto()
    }

    override fun saveUserPhoto(photoUrl: String) {
        return dataStoreOperations.saveUserPhoto(photoUrl = photoUrl)
    }

    override fun getUserPhoto(): String? {
      return dataStoreOperations.getUserPhoto()
    }

    override fun saveUserFcm(fcmToken: String) {
        dataStoreOperations.saveUserFcm(fcmToken = fcmToken)
    }

    override fun getUserFcm(): String? {
        return dataStoreOperations.getUserFcm()
    }

}
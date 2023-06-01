package com.petpack.whereismyheart.domain.repository

import com.petpack.whereismyheart.data.model.auth.heart_status.res.HeartStatusResponse
import com.petpack.whereismyheart.data.model.auth.oauth.res.GoogleTokenResponse
import com.petpack.whereismyheart.data.model.connect_request.ConnectRequest
import com.petpack.whereismyheart.data.model.connect_request.res.ApiResponse
import com.petpack.whereismyheart.data.model.user_details.ConnectionRequest
import com.petpack.whereismyheart.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun tokenAuthentication(token : String,fcmToken: String) : Flow<NetworkResult<GoogleTokenResponse>>
    fun heartStatus(): Flow<NetworkResult<HeartStatusResponse>>
    fun sendProposalRequest(connectRequest: ConnectRequest): Flow<NetworkResult<ApiResponse>>
    fun acceptProposalRequest(connectRequest: ConnectRequest): Flow<NetworkResult<ApiResponse>>
    fun disconnectHeart(connectRequest: ConnectRequest) : Flow<NetworkResult<ApiResponse>>
    fun saveJwtToken(token: String)
    fun getJwtToken(): String?
    fun saveUserHeartId(heartId : String)
    fun getUserHeartId(): String?
    fun saveConnectHeartId(heartId : String)
    fun getConnectHeartId(): String?


    fun saveUserName(name : String)
    fun getUserName(): String?
    fun saveUserEmail(email : String)
    fun getUserEmail(): String?
    fun saveConnectUserName(name : String)
    fun getConnectUserName(): String?
    fun saveConnectUserEmail(email : String)
    fun getConnectUserEmail(): String?
    fun saveConnectUserPhoto(photoUrl : String)
    fun getConnectUserPhoto(): String?
    fun saveUserPhoto(photoUrl : String)
    fun getUserPhoto(): String?

    fun saveUserFcm(fcmToken : String)
    fun getUserFcm(): String?
}
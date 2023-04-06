package com.petpack.whereismyheart.domain.repository

import com.petpack.whereismyheart.data.model.auth.heart_status.res.HeartStatusResponse
import com.petpack.whereismyheart.data.model.auth.oauth.res.GoogleTokenResponse
import com.petpack.whereismyheart.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun tokenAuthentication(token : String) : Flow<NetworkResult<GoogleTokenResponse>>
    fun heartStatus(): Flow<NetworkResult<HeartStatusResponse>>
    fun saveJwtToken(token: String)
    fun getJwtToken(): String?
    fun saveUserHeartId(heartId : String)
    fun getUserHeartId(): String?
    fun saveConnectHeartId(heartId : String)
    fun getConnectHeartId(): String?
}
package com.petpack.whereismyheart.data.remote

import com.petpack.whereismyheart.data.model.auth.heart_status.res.HeartStatusResponse
import com.petpack.whereismyheart.data.model.auth.oauth.res.GoogleTokenResponse
import com.petpack.whereismyheart.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

interface UserService {
     fun tokenAuthentication(token : String) : Flow<NetworkResult<GoogleTokenResponse>>
     fun heartStatus(token: String): Flow<NetworkResult<HeartStatusResponse>>
}
package com.petpack.whereismyheart.data.remote

import com.petpack.whereismyheart.data.model.auth.heart_status.res.HeartStatusResponse
import com.petpack.whereismyheart.data.model.auth.oauth.req.GoogleTokenRequest
import com.petpack.whereismyheart.data.model.auth.oauth.res.GoogleTokenResponse
import com.petpack.whereismyheart.utils.Endpoints
import com.petpack.whereismyheart.utils.NetworkResult
import com.petpack.whereismyheart.utils.handleResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.flow.Flow

class UserServiceImpl(private val httpClient: HttpClient) : UserService {
    override fun tokenAuthentication(token: String): Flow<NetworkResult<GoogleTokenResponse>> {
        return handleResponse {
                httpClient.post{
                url(Endpoints.TokenVerification.route)
                body = GoogleTokenRequest(tokenId = token)
            }
        }
    }

    override fun heartStatus(token: String): Flow<NetworkResult<HeartStatusResponse>> {
        return handleResponse {
            httpClient.get(Endpoints.HeartStatus.route){
                header(key = "Authorization",value = "Bearer $token")
            }
        }
    }
}
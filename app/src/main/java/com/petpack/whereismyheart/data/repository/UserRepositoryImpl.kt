package com.petpack.whereismyheart.data.repository

import com.petpack.whereismyheart.data.model.auth.heart_status.res.HeartStatusResponse
import com.petpack.whereismyheart.data.model.auth.oauth.req.GoogleTokenRequest
import com.petpack.whereismyheart.data.model.auth.oauth.res.GoogleTokenResponse
import com.petpack.whereismyheart.domain.repository.DataStoreOperations
import com.petpack.whereismyheart.domain.repository.UserRepository
import com.petpack.whereismyheart.utils.Endpoints
import com.petpack.whereismyheart.utils.NetworkResult
import com.petpack.whereismyheart.utils.handleResponse
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(private val httpClient: HttpClient,private val dataStoreOperations: DataStoreOperations) : UserRepository {

    override fun tokenAuthentication(token: String): Flow<NetworkResult<GoogleTokenResponse>> {
        return handleResponse {
            httpClient.post{
                url(Endpoints.TokenVerification.route)
                body = GoogleTokenRequest(tokenId = token)
            }
        }
    }

    override fun heartStatus(): Flow<NetworkResult<HeartStatusResponse>> {
        return handleResponse {
            httpClient.get(Endpoints.HeartStatus.route)
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

}
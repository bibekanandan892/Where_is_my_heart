package com.petpack.whereismyheart.domain.usecase.disconnect

import com.petpack.whereismyheart.data.model.auth.heart_status.res.HeartStatusResponse
import com.petpack.whereismyheart.data.model.connect_request.ConnectRequest
import com.petpack.whereismyheart.data.model.connect_request.res.ApiResponse
import com.petpack.whereismyheart.domain.repository.UserRepository
import com.petpack.whereismyheart.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

class DisconnectHeartUseCase (private val userRepository: UserRepository){
    operator fun invoke(connectRequest: ConnectRequest): Flow<NetworkResult<ApiResponse>> {
        return userRepository.disconnectHeart(connectRequest = connectRequest)
    }
}
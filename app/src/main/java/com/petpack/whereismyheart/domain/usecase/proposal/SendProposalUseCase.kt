package com.petpack.whereismyheart.domain.usecase.proposal

import com.petpack.whereismyheart.data.model.connect_request.ConnectRequest
import com.petpack.whereismyheart.data.model.connect_request.res.ApiResponse
import com.petpack.whereismyheart.data.model.user_details.ConnectionRequest
import com.petpack.whereismyheart.domain.repository.UserRepository
import com.petpack.whereismyheart.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

class SendProposalUseCase constructor(private val userRepository: UserRepository){
    operator fun invoke(
        connectRequest: ConnectRequest,
    ): Flow<NetworkResult<ApiResponse>> {
        return userRepository.sendProposalRequest(connectRequest = connectRequest)
    }
}
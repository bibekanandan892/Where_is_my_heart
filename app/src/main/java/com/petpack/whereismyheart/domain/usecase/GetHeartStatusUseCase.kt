package com.petpack.whereismyheart.domain.usecase

import com.petpack.whereismyheart.data.model.auth.heart_status.res.HeartStatusResponse
import com.petpack.whereismyheart.data.remote.UserService
import com.petpack.whereismyheart.domain.repository.UserRepository
import com.petpack.whereismyheart.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

class GetHeartStatusUseCase (private val userRepository: UserRepository) {
    operator fun invoke(
    ): Flow<NetworkResult<HeartStatusResponse>> {
        return userRepository.heartStatus()
    }
}
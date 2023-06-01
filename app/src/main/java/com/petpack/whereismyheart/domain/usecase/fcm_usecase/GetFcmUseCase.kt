package com.petpack.whereismyheart.domain.usecase.fcm_usecase

import com.petpack.whereismyheart.data.model.auth.heart_status.res.HeartStatusResponse
import com.petpack.whereismyheart.domain.repository.UserRepository
import com.petpack.whereismyheart.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

class GetFcmUseCase(private val userRepository: UserRepository) {
    operator fun invoke(
    ): String? {
        return userRepository.getUserFcm()
    }
}
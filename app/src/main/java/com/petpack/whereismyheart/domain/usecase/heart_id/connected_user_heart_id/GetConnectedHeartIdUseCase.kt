package com.petpack.whereismyheart.domain.usecase.heart_id.connected_user_heart_id

import com.petpack.whereismyheart.domain.repository.UserRepository

class GetConnectedHeartIdUseCase constructor(private val userRepository: UserRepository) {
    operator fun invoke(): String? {
        return userRepository.getConnectHeartId()
    }
}
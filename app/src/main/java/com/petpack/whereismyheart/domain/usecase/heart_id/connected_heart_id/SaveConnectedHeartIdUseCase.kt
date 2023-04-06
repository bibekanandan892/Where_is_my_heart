package com.petpack.whereismyheart.domain.usecase.heart_id.connected_heart_id

import com.petpack.whereismyheart.domain.repository.UserRepository

class SaveConnectedHeartIdUseCase constructor(private val userRepository: UserRepository) {
    operator fun invoke(heartId: String) {
        userRepository.saveConnectHeartId(heartId = heartId)
    }
}
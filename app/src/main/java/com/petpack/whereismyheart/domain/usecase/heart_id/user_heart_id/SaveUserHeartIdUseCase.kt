package com.petpack.whereismyheart.domain.usecase.heart_id.user_heart_id

import com.petpack.whereismyheart.domain.repository.UserRepository

class SaveUserHeartIdUseCase constructor(private val userRepository: UserRepository) {
    operator fun invoke(heartId : String) {
        userRepository.saveUserHeartId(heartId = heartId)
    }
}
package com.petpack.whereismyheart.domain.usecase.heart_id.user_heart_id

import com.petpack.whereismyheart.domain.repository.UserRepository

class GetUserIHeartIdUseCase constructor(private val userRepository: UserRepository) {
    operator fun invoke(): String? {
        return userRepository.getUserHeartId()
    }
}
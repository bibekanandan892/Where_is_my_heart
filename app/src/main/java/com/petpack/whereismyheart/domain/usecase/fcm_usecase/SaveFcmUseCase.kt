package com.petpack.whereismyheart.domain.usecase.fcm_usecase

import com.petpack.whereismyheart.domain.repository.UserRepository

class SaveFcmUseCase(private val userRepository: UserRepository) {
    operator fun invoke(
        fcmToken : String
    ) {
        userRepository.saveUserFcm(fcmToken = fcmToken)
    }
}
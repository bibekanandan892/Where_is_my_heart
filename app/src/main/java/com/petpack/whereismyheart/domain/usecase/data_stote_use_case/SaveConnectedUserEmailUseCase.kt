package com.petpack.whereismyheart.domain.usecase.data_stote_use_case

import com.petpack.whereismyheart.domain.repository.UserRepository

class SaveConnectedUserEmailUseCase constructor(private val userRepository: UserRepository) {
    operator fun invoke(email: String) {
        userRepository.saveUserEmail(email = email)
    }
}
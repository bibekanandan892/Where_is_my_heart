package com.petpack.whereismyheart.domain.usecase.data_stote_use_case

import com.petpack.whereismyheart.domain.repository.UserRepository
import okhttp3.internal.threadName

class SaveConnectedUserNameUseCase constructor(private val userRepository: UserRepository) {
    operator fun invoke(name: String) {
        userRepository.saveConnectUserName(name = name)
    }
}
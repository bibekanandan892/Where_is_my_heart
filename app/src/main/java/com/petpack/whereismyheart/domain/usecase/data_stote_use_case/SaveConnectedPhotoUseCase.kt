package com.petpack.whereismyheart.domain.usecase.data_stote_use_case

import com.petpack.whereismyheart.domain.repository.UserRepository

class SaveConnectedPhotoUseCase constructor(private val userRepository: UserRepository) {
    operator fun invoke(photoUrl: String) {
        userRepository.saveConnectUserPhoto(photoUrl = photoUrl)
    }
}
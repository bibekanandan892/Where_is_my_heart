package com.petpack.whereismyheart.domain.usecase.jwt_token

import com.petpack.whereismyheart.domain.repository.DataStoreOperations
import com.petpack.whereismyheart.domain.repository.UserRepository

class SaveJwtTokenUseCase(private val userRepository: UserRepository) {
    operator fun invoke(
        token : String
    ) {
        userRepository.saveJwtToken(token = token)
    }
}
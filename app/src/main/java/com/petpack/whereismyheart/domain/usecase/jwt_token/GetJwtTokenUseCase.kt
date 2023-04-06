package com.petpack.whereismyheart.domain.usecase.jwt_token

import com.petpack.whereismyheart.domain.repository.DataStoreOperations
import com.petpack.whereismyheart.domain.repository.UserRepository

class GetJwtTokenUseCase (private val userRepository: UserRepository) {
    operator fun invoke(): String? {
        return userRepository.getJwtToken()
    }
}
package com.petpack.whereismyheart.domain.usecase

import com.petpack.whereismyheart.data.model.auth.oauth.res.GoogleTokenResponse
import com.petpack.whereismyheart.domain.repository.UserRepository
import com.petpack.whereismyheart.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

class TokenAuthenticationUseCase(private val userRepository: UserRepository) {
    operator fun invoke(
        token: String,
        fcmToken : String
    ): Flow<NetworkResult<GoogleTokenResponse>> {
        return userRepository.tokenAuthentication(token = token,fcmToken = fcmToken)
    }
}
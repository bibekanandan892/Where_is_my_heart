package com.petpack.whereismyheart.di

import com.petpack.whereismyheart.data.remote.UserService
import com.petpack.whereismyheart.domain.repository.UserRepository
import com.petpack.whereismyheart.domain.usecase.GetHeartStatusUseCase
import com.petpack.whereismyheart.domain.usecase.TokenAuthenticationUseCase
import com.petpack.whereismyheart.domain.usecase.UseCases
import com.petpack.whereismyheart.domain.usecase.heart_id.connected_heart_id.GetConnectedHeartIdUseCase
import com.petpack.whereismyheart.domain.usecase.heart_id.connected_heart_id.SaveConnectedHeartIdUseCase
import com.petpack.whereismyheart.domain.usecase.heart_id.user_heart_id.GetUserIHeartIdUseCase
import com.petpack.whereismyheart.domain.usecase.heart_id.user_heart_id.SaveUserHeartIdUseCase
import com.petpack.whereismyheart.domain.usecase.jwt_token.GetJwtTokenUseCase
import com.petpack.whereismyheart.domain.usecase.jwt_token.SaveJwtTokenUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {
    @Provides
    @Singleton
    fun providesUseCases(userRepository: UserRepository): UseCases{
        return UseCases(
            tokenAuthenticationUseCase = TokenAuthenticationUseCase(userRepository = userRepository),
            getHeartStatusUseCase = GetHeartStatusUseCase(userRepository = userRepository),
            saveJwtTokenUseCase = SaveJwtTokenUseCase(userRepository = userRepository),
            getJwtTokenUseCase = GetJwtTokenUseCase(userRepository = userRepository),
            getConnectedHeartIdUseCase = GetConnectedHeartIdUseCase(userRepository = userRepository),
            getUserIHeartIdUseCase = GetUserIHeartIdUseCase(userRepository = userRepository),
            saveConnectedHeartIdUseCase = SaveConnectedHeartIdUseCase(userRepository = userRepository),
            saveUserHeartIdUseCase = SaveUserHeartIdUseCase(userRepository = userRepository)
        )
    }
}
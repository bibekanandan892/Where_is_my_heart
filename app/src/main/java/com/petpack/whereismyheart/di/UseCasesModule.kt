package com.petpack.whereismyheart.di

import com.petpack.whereismyheart.domain.repository.ChatSocketService
import com.petpack.whereismyheart.domain.repository.UserRepository
import com.petpack.whereismyheart.domain.usecase.GetHeartStatusUseCase
import com.petpack.whereismyheart.domain.usecase.TokenAuthenticationUseCase
import com.petpack.whereismyheart.domain.usecase.UseCases
import com.petpack.whereismyheart.domain.usecase.chat_usecase.DeleteAllChatsUseCase
import com.petpack.whereismyheart.domain.usecase.chat_usecase.InsertChatUseCase
import com.petpack.whereismyheart.domain.usecase.data_stote_use_case.*
import com.petpack.whereismyheart.domain.usecase.disconnect.DisconnectHeartUseCase
import com.petpack.whereismyheart.domain.usecase.fcm_usecase.GetFcmUseCase
import com.petpack.whereismyheart.domain.usecase.fcm_usecase.SaveFcmUseCase
import com.petpack.whereismyheart.domain.usecase.heart_id.connected_user_heart_id.GetConnectedHeartIdUseCase
import com.petpack.whereismyheart.domain.usecase.heart_id.connected_user_heart_id.SaveConnectedHeartIdUseCase
import com.petpack.whereismyheart.domain.usecase.heart_id.user_heart_id.GetUserIHeartIdUseCase
import com.petpack.whereismyheart.domain.usecase.heart_id.user_heart_id.SaveUserHeartIdUseCase
import com.petpack.whereismyheart.domain.usecase.jwt_token.GetJwtTokenUseCase
import com.petpack.whereismyheart.domain.usecase.jwt_token.SaveJwtTokenUseCase
import com.petpack.whereismyheart.domain.usecase.proposal.AcceptProposalUseCase
import com.petpack.whereismyheart.domain.usecase.proposal.SendProposalUseCase
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
    fun providesUseCases(userRepository: UserRepository,chatSocketService: ChatSocketService): UseCases{
        return UseCases(
            tokenAuthenticationUseCase = TokenAuthenticationUseCase(userRepository = userRepository),
            getHeartStatusUseCase = GetHeartStatusUseCase(userRepository = userRepository),
            saveJwtTokenUseCase = SaveJwtTokenUseCase(userRepository = userRepository),
            getJwtTokenUseCase = GetJwtTokenUseCase(userRepository = userRepository),
            getConnectedHeartIdUseCase = GetConnectedHeartIdUseCase(userRepository = userRepository),
            getUserHeartIdUseCase = GetUserIHeartIdUseCase(userRepository = userRepository),
            saveConnectedHeartIdUseCase = SaveConnectedHeartIdUseCase(userRepository = userRepository),
            saveUserHeartIdUseCase = SaveUserHeartIdUseCase(userRepository = userRepository),
            sendProposalUseCase = SendProposalUseCase(userRepository = userRepository),
            saveConnectedPhotoUseCase = SaveConnectedPhotoUseCase(userRepository = userRepository),
            getConnectedPhotoUseCase = GetConnectedPhotoUseCase(userRepository = userRepository),
            saveConnectedUserEmailUseCase = SaveConnectedUserEmailUseCase(userRepository = userRepository),
            getConnectedUserEmailUseCase = GetConnectedUserEmailUseCase(userRepository = userRepository),
            getConnectedUserNameUseCase = GetConnectedUserNameUseCase(userRepository = userRepository),
            getUserEmailUseCase = GetUserEmailUseCase(userRepository = userRepository),
            getUserNameUseCase = GetUserNameUseCase(userRepository = userRepository),
            getUserPhotoUseCase = GetUserPhotoUseCase(userRepository = userRepository),
            saveConnectedUserNameUseCase = SaveConnectedUserNameUseCase(userRepository = userRepository),
            saveUserEmailUseCase = SaveUserEmailUseCase(userRepository = userRepository),
            saveUserNameUseCase = SaveUserNameUseCase(userRepository = userRepository),
            saveUserPhotoUseCase = SaveUserPhotoUseCase(userRepository = userRepository),
            acceptProposalUseCase = AcceptProposalUseCase(userRepository = userRepository),
            getFcmUseCase = GetFcmUseCase(userRepository = userRepository),
            saveFcmUseCase = SaveFcmUseCase(userRepository = userRepository),
            disconnectHeartUseCase = DisconnectHeartUseCase(userRepository = userRepository),
            insertChatUseCase = InsertChatUseCase(chatSocketService = chatSocketService),
            deleteAllChatsUseCase = DeleteAllChatsUseCase(chatSocketService = chatSocketService)
        )
    }
}
package com.petpack.whereismyheart.domain.usecase

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

data class UseCases(
    val tokenAuthenticationUseCase: TokenAuthenticationUseCase,
    val getHeartStatusUseCase: GetHeartStatusUseCase,
    val getJwtTokenUseCase: GetJwtTokenUseCase,
    val saveJwtTokenUseCase: SaveJwtTokenUseCase,
    val getConnectedHeartIdUseCase: GetConnectedHeartIdUseCase,
    val getUserHeartIdUseCase: GetUserIHeartIdUseCase,
    val saveConnectedHeartIdUseCase: SaveConnectedHeartIdUseCase,
    val saveUserHeartIdUseCase: SaveUserHeartIdUseCase,
    val sendProposalUseCase: SendProposalUseCase,
    val saveConnectedUserEmailUseCase: SaveConnectedUserEmailUseCase,
    val saveConnectedUserNameUseCase: SaveConnectedUserNameUseCase,
    val getConnectedUserEmailUseCase: GetConnectedUserEmailUseCase,
    val getConnectedUserNameUseCase: GetConnectedUserNameUseCase,
    val saveUserNameUseCase: SaveUserNameUseCase,
    val saveUserEmailUseCase: SaveUserEmailUseCase,
    val getUserNameUseCase: GetUserNameUseCase,
    val getUserEmailUseCase: GetUserEmailUseCase,
    val saveUserPhotoUseCase: SaveUserPhotoUseCase,
    val saveConnectedPhotoUseCase: SaveConnectedPhotoUseCase,
    val getUserPhotoUseCase: GetUserPhotoUseCase,
    val getConnectedPhotoUseCase: GetConnectedPhotoUseCase,
    val acceptProposalUseCase: AcceptProposalUseCase,
    val saveFcmUseCase: SaveFcmUseCase,
    val getFcmUseCase: GetFcmUseCase,
    val disconnectHeartUseCase: DisconnectHeartUseCase,
    val insertChatUseCase: InsertChatUseCase,
    val deleteAllChatsUseCase: DeleteAllChatsUseCase
)

package com.petpack.whereismyheart.domain.usecase

import com.petpack.whereismyheart.domain.usecase.heart_id.connected_heart_id.GetConnectedHeartIdUseCase
import com.petpack.whereismyheart.domain.usecase.heart_id.connected_heart_id.SaveConnectedHeartIdUseCase
import com.petpack.whereismyheart.domain.usecase.heart_id.user_heart_id.GetUserIHeartIdUseCase
import com.petpack.whereismyheart.domain.usecase.heart_id.user_heart_id.SaveUserHeartIdUseCase
import com.petpack.whereismyheart.domain.usecase.jwt_token.GetJwtTokenUseCase
import com.petpack.whereismyheart.domain.usecase.jwt_token.SaveJwtTokenUseCase

data class UseCases(
    val tokenAuthenticationUseCase: TokenAuthenticationUseCase,
    val getHeartStatusUseCase: GetHeartStatusUseCase,
    val getJwtTokenUseCase: GetJwtTokenUseCase,
    val saveJwtTokenUseCase: SaveJwtTokenUseCase,
    val getConnectedHeartIdUseCase: GetConnectedHeartIdUseCase,
    val getUserIHeartIdUseCase: GetUserIHeartIdUseCase,
    val saveConnectedHeartIdUseCase: SaveConnectedHeartIdUseCase,
    val saveUserHeartIdUseCase: SaveUserHeartIdUseCase
)

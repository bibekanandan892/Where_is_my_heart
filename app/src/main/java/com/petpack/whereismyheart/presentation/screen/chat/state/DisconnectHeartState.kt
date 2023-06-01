package com.petpack.whereismyheart.presentation.screen.chat.state

import com.petpack.whereismyheart.data.model.auth.heart_status.res.HeartStatusResponse
import com.petpack.whereismyheart.data.model.connect_request.res.ApiResponse

data class DisconnectHeartState(
    var apiResponse: ApiResponse? = null,
    val loading: Boolean? = null,
    var error: String? = null
)

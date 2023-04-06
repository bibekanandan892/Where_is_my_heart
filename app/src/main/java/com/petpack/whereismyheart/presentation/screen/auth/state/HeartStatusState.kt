package com.petpack.whereismyheart.presentation.screen.auth.state

import com.petpack.whereismyheart.data.model.auth.heart_status.res.HeartStatusResponse
import com.petpack.whereismyheart.data.model.auth.oauth.res.GoogleTokenResponse

data class HeartStatusState(
    var heartStatusResponse: HeartStatusResponse? = null,
    val loading: Boolean? = null,
    val error: String? = null
)

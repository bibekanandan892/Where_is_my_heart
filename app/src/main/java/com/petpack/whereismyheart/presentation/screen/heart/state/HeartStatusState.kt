package com.petpack.whereismyheart.presentation.screen.heart.state

import com.petpack.whereismyheart.data.model.auth.heart_status.res.HeartStatusResponse
import com.petpack.whereismyheart.data.model.auth.oauth.res.GoogleTokenResponse

data class HeartStatusState(
    var heartStatusResponse: HeartStatusResponse? = null,
    val loading: Boolean? = null,
    var error: String? = null
)

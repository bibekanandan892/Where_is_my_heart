package com.petpack.whereismyheart.presentation.screen.auth.state

import com.petpack.whereismyheart.data.model.auth.oauth.res.GoogleTokenResponse

data class GoogleTokenState(
    var googleTokenResponse: GoogleTokenResponse? = null,
    val loading: Boolean? = null,
    val error: String? = null
)

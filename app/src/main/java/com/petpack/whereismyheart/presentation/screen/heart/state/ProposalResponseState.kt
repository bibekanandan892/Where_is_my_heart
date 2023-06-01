package com.petpack.whereismyheart.presentation.screen.heart.state

import com.petpack.whereismyheart.data.model.connect_request.res.ApiResponse


data class ProposalResponseState(
    var apiResponse: ApiResponse? = null,
    val loading: Boolean? = null,
    var error: String? = null,
)

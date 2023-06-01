package com.petpack.whereismyheart.presentation.screen.heart.pages

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.petpack.whereismyheart.data.model.user_details.ConnectionRequest
import com.petpack.whereismyheart.presentation.components.PeopleRow
import com.petpack.whereismyheart.presentation.components.ui_status.ShowError
import com.petpack.whereismyheart.presentation.components.ui_status.ShowLoading

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AcceptProposalPage(
    listOfConnectRequest: List<ConnectionRequest?>,
    heartStatusLoadingState: Boolean,
    onAcceptClick: (String) -> Unit = {},
    acceptProposalLoading: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(color = MaterialTheme.colorScheme.surfaceColorAtElevation(10.dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        
        LazyColumn(modifier = Modifier.fillMaxSize()){
            items(listOfConnectRequest){connectionRequest->
                connectionRequest?.let {
                    PeopleRow(connectionRequest = it, onAcceptClick  = onAcceptClick)
                }
            }
        }
        if(listOfConnectRequest.isEmpty()){
            ShowError(message = "No Proposal", backGroundColor = MaterialTheme.colorScheme.surfaceColorAtElevation(10.dp))
        }
        ShowLoading(isLoading = (heartStatusLoadingState || acceptProposalLoading))

    }
}
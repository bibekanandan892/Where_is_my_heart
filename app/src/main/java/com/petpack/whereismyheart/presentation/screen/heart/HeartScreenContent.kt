package com.petpack.whereismyheart.presentation.screen.heart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import com.google.accompanist.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.petpack.whereismyheart.data.model.user_details.ConnectionRequest
import com.petpack.whereismyheart.presentation.screen.heart.pages.AcceptProposalPage
import com.petpack.whereismyheart.presentation.screen.heart.pages.ProposalPage
import com.petpack.whereismyheart.presentation.screen.heart.pages.HeartIdPage
import com.petpack.whereismyheart.utils.Constants.PAGING_INDICATOR_SPACING
import com.petpack.whereismyheart.utils.Constants.PAGING_INDICATOR_WIDTH

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HeartScreenContent(
    userHeartId: String,
    onLongPressOnId: (message: String) -> Unit,
    onSendProposalClick: (heartId: String) -> Unit,
    proposalResponseLoadingState: Boolean,
    listOfConnectRequest: List<ConnectionRequest?>,
    heartStatusLoadingState: Boolean,
    onAcceptClick: (String) -> Unit = {},
    acceptProposalLoading: Boolean
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.BottomCenter
    ) {
        HorizontalPager(
            count = 3,
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 100.dp, bottom = 70.dp),
            verticalAlignment = Alignment.Top,
        ) { page ->
            when (page) {
                0 -> {
                    HeartIdPage(
                        heartId = userHeartId,
                        onLongPressOnId = onLongPressOnId
                    )
                }
                1 -> {
                    ProposalPage(
                        onSendProposal = onSendProposalClick,
                        proposalResponseLoadingState =proposalResponseLoadingState
                    )
                }
                2 -> {
                    AcceptProposalPage(
                        heartStatusLoadingState = heartStatusLoadingState,
                        listOfConnectRequest = listOfConnectRequest,
                        onAcceptClick = onAcceptClick,
                        acceptProposalLoading = acceptProposalLoading
                    )
                }
            }
        }
        HorizontalPagerIndicator(
            modifier = Modifier
                .padding(bottom = 30.dp)
                .align(alignment = Alignment.BottomCenter),
            pagerState = pagerState,
            activeColor = MaterialTheme.colorScheme.onSurface,
            inactiveColor = MaterialTheme.colorScheme.surfaceColorAtElevation(5.dp),
            indicatorWidth = PAGING_INDICATOR_WIDTH,
            spacing = PAGING_INDICATOR_SPACING
        )
    }


}
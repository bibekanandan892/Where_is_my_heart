package com.petpack.whereismyheart.presentation.screen.heart

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.petpack.whereismyheart.data.model.user_details.ConnectionRequest
import com.petpack.whereismyheart.presentation.components.NavigationDrawer
import com.stevdzasan.messagebar.ContentWithMessageBar
import com.stevdzasan.messagebar.MessageBarState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeartStatusScreen(
    messageBarState: MessageBarState,
    onMenuClicked: () -> Unit,
    onReloadRequest: () -> Unit = {},
    onSignOutClicked: () -> Unit,
    onLongPressOnId: (String) -> Unit,
    drawerState: DrawerState,
    onSendProposalClick: (heartId: String) -> Unit,
    heartStatusLoadingState: Boolean,
    proposalResponseLoadingState: Boolean,
    userName: String,
    userProfilePhoto: String,
    userEmailAddress: String,
    userHeartId: String,
    connectionRequestList: List<ConnectionRequest?>,
    onAcceptClick: (String) -> Unit = {},
    acceptProposalLoading: Boolean,
) {

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    NavigationDrawer(
        drawerState = drawerState,
        profilePhoto = userProfilePhoto,
        name = userName,
        emailAddress = userEmailAddress,
        onSignOutClicked = onSignOutClicked,
        content = {
            ContentWithMessageBar(
                messageBarState = messageBarState, modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .statusBarsPadding()
                    .navigationBarsPadding()
            ) {
                Scaffold(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surface)
                        .statusBarsPadding()
                        .navigationBarsPadding(),
                    topBar = {
                        HeartScreenTopBar(
                            scrollBehavior = scrollBehavior,
                            onMenuClicked = onMenuClicked,
                            onReloadRequest = onReloadRequest,
                            isLoading = heartStatusLoadingState
                        )
                    },
                    content = {
                        HeartScreenContent(
                            userHeartId = userHeartId,
                            onLongPressOnId = onLongPressOnId,
                            onSendProposalClick = onSendProposalClick,
                            heartStatusLoadingState = heartStatusLoadingState,
                            proposalResponseLoadingState = proposalResponseLoadingState,
                            listOfConnectRequest = connectionRequestList,
                            onAcceptClick = onAcceptClick,
                            acceptProposalLoading = acceptProposalLoading

                        )

                    },
                    contentWindowInsets = ScaffoldDefaults
                        .contentWindowInsets
                        .exclude(WindowInsets.navigationBars)
                        .exclude(WindowInsets.ime)
                )
            }
        //            }
        },
        disconnectLoadingState = false,
    )

}


package com.petpack.whereismyheart.presentation.screen.chat

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.petpack.whereismyheart.data.model.chat.MessageEntity
import com.petpack.whereismyheart.presentation.components.NavigationDrawer
import com.petpack.whereismyheart.presentation.screen.chat.conversation.*
import com.stevdzasan.messagebar.ContentWithMessageBar
import com.stevdzasan.messagebar.MessageBarState
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ChattingScreen(
    messageBarState: MessageBarState,
    drawerState: DrawerState, userName: String,
    userProfilePhoto: String,
    userEmailAddress: String,
    userHeartId: String,
    onSignOutClicked: () -> Unit,
    onMenuClicked: () -> Unit,
    connectUserProfileImage: String?,
    connectUserName: String?,
    onSendClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    chatsList: List<MessageEntity?>?,
    onDisconnectClicked: () -> Unit,
    disconnectLoadingState: Boolean?
) {
    NavigationDrawer(
        drawerState = drawerState,
        onSignOutClicked = onSignOutClicked,
        name = userName,
        profilePhoto = userProfilePhoto,
        emailAddress = userEmailAddress,
        onDisconnectClicked = onDisconnectClicked,
        disconnectLoadingState =disconnectLoadingState
    ) {
        ContentWithMessageBar(
            messageBarState = messageBarState,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            val scrollState = rememberLazyListState()
            val topBarState = rememberTopAppBarState()
            val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topBarState)
            val scope = rememberCoroutineScope()
            Scaffold(
                topBar = {
                    ChannelNameBar(
                        connectUserName = connectUserName,
                        connectUserProfileImage = connectUserProfileImage,
                        onNavIconPressed = onMenuClicked,
                        scrollBehavior = scrollBehavior,
                    )
                },
                // Exclude ime and navigation bar padding so this can be added by the UserInput composable
                contentWindowInsets = ScaffoldDefaults
                    .contentWindowInsets
                    .exclude(WindowInsets.navigationBars)
                    .exclude(WindowInsets.ime),
                modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
            ) { paddingValues ->
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    Messages(
                        chatsList = chatsList?.reversed(),
                        modifier = Modifier.weight(1f),
                        scrollState = scrollState
                    )
                    UserInput(
                        onMessageSent = onSendClick,
                        resetScroll = {
                            scope.launch {
                                scrollState.scrollToItem(0)
                            }
                        },
                        // let this element handle the padding so that the elevation is shown behind the
                        // navigation bar
                        modifier = Modifier
                            .navigationBarsPadding()
                            .imePadding()

                    )
                }
            }
        }
    }


}
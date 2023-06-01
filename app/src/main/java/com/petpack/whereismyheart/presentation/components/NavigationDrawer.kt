package com.petpack.whereismyheart.presentation.components

import androidx.compose.material3.*
import androidx.compose.runtime.*

@Composable
fun NavigationDrawer(
    drawerState: DrawerState,
    profilePhoto: String?,
    name: String?,
    emailAddress: String?,
    onSignOutClicked: () -> Unit,
    onDisconnectClicked: () -> Unit = {},
    disconnectLoadingState: Boolean?,
    content: @Composable () -> Unit,
) {

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                content = {
                    JetChatDrawerContent(
                        onDisconnectClicked = onDisconnectClicked,
                        onSignOutClicked = onSignOutClicked,
                        onChatClicked = {

                        },
                        emailAddress = emailAddress,
                        name = name,
                        profilePhoto = profilePhoto,
                        disconnectLoadingState = disconnectLoadingState
                    )

                }
            )
        },
        content = content
    )
}
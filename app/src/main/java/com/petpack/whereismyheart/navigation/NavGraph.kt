package com.petpack.whereismyheart.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.petpack.whereismyheart.presentation.screen.auth.AuthenticationScreen
import com.petpack.whereismyheart.presentation.screen.auth.AuthenticationViewModel
import com.petpack.whereismyheart.presentation.screen.heart.HeartStatusScreen
import com.stevdzasan.messagebar.rememberMessageBarState
import com.stevdzasan.onetap.rememberOneTapSignInState

@Composable
fun SetupNavGraph(
    startDestination: String,
    navController: NavHostController,
    authenticationViewModel: AuthenticationViewModel,
  ) {
    NavHost(navController = navController, startDestination = startDestination) {
        authenticationRoute(
            navigateToHeartStatus = {
                navController.popBackStack()
                navController.navigate(Screen.HeartStatus.route)
            },
            authenticationViewModel = authenticationViewModel
        )
        heartRoute()
        chatRoute()
        heartLocationRoute()
    }
}

fun NavGraphBuilder.authenticationRoute(
    authenticationViewModel: AuthenticationViewModel,
    navigateToHeartStatus: () -> Unit,
) {
    composable(route = Screen.Authentication.route) {
        val loadingState by authenticationViewModel.loadingState
        val oneTapState = rememberOneTapSignInState()
        val messageBarState = rememberMessageBarState()
        val jwtTokenState = authenticationViewModel.jwtTokenState.collectAsStateWithLifecycle()
        val heartStatusState = authenticationViewModel.heartStatusState.collectAsStateWithLifecycle()
        AuthenticationScreen(
            loadingState = loadingState,
            oneTapState = oneTapState,
            messageBarState = messageBarState,
            onButtonClicked = {
                oneTapState.open()
                authenticationViewModel.setLoading(true)
            },
            onSuccessfulSignIn = { tokenId ->
                authenticationViewModel.setLoading(false)
                authenticationViewModel.getJwtWithGoogleToken(token = tokenId)
                messageBarState.addSuccess("Successfully Authenticated!")
            },
            onFailedFirebaseSignIn = {
                messageBarState.addError(it)
                authenticationViewModel.setLoading(false)
            },
            onDialogDismissed = { message ->
                messageBarState.addError(Exception(message))
                authenticationViewModel.setLoading(false)
            },
            onReceivedJwt = {
                authenticationViewModel.saveJwtToken(token = it)
                navigateToHeartStatus.invoke()

            },
            jwtTokenState = jwtTokenState,
            heartStatusState = heartStatusState,
            navigateToChat = navigateToHeartStatus
        )
    }
}

fun NavGraphBuilder.heartRoute() {

    composable(route = Screen.HeartStatus.route) {
        val messageBarState = rememberMessageBarState()
        HeartStatusScreen(messageBarState = messageBarState,)
    }
}

fun NavGraphBuilder.chatRoute() {
    composable(route = Screen.Chat.route) {

    }
}

fun NavGraphBuilder.heartLocationRoute() {
    composable(route = Screen.HeartLocation.route) {

    }
}
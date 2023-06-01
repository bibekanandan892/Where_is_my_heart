package com.petpack.whereismyheart.navigation

import android.app.Activity
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.petpack.whereismyheart.data.model.connect_request.ConnectRequest
import com.petpack.whereismyheart.presentation.components.DisplayAlertDialog
import com.petpack.whereismyheart.presentation.screen.auth.AuthenticationScreen
import com.petpack.whereismyheart.presentation.screen.auth.AuthenticationViewModel
import com.petpack.whereismyheart.presentation.screen.chat.ChattingScreen
import com.petpack.whereismyheart.presentation.screen.chat.ChattingViewModel
import com.petpack.whereismyheart.presentation.screen.heart.HeartStatusScreen
import com.petpack.whereismyheart.presentation.screen.heart.HeartStatusViewModel
import com.petpack.whereismyheart.utils.ConnectionState
import com.petpack.whereismyheart.utils.Constants.WIMH
import com.stevdzasan.messagebar.rememberMessageBarState
import com.stevdzasan.onetap.rememberOneTapSignInState
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
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
            authenticationViewModel = authenticationViewModel,
            navigateToChattingScreen = {
                navController.popBackStack()
                navController.navigate(Screen.Chat.route)
            }
        )
        heartRoute() {
            navController.popBackStack()
            navController.navigate(Screen.Chat.route)
        }
        chatRoute(){
            navController.popBackStack()
            navController.navigate(Screen.HeartStatus.route)
        }
        heartLocationRoute()
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun NavGraphBuilder.authenticationRoute(
    authenticationViewModel: AuthenticationViewModel,
    navigateToHeartStatus: () -> Unit,
    navigateToChattingScreen: () -> Unit,
) {
    composable(route = Screen.Authentication.route) {
        val loadingState by authenticationViewModel.loadingState
        val oneTapState = rememberOneTapSignInState()
        val messageBarState = rememberMessageBarState()
        val jwtTokenState = authenticationViewModel.jwtTokenState.collectAsStateWithLifecycle()
        val heartStatusState =
            authenticationViewModel.heartStatusState.collectAsStateWithLifecycle()
        val context = LocalContext.current
        val activity = context as Activity

        LaunchedEffect(key1 = heartStatusState.value) {
            heartStatusState.value.heartStatusResponse?.let { heartStatusResponse ->
                if (heartStatusResponse.success == true) {
                    messageBarState.addSuccess(heartStatusResponse.message.toString())
                    heartStatusResponse.response?.let { response ->
                        if ((response.connectedHeardId != null) && (response.connectUserPhoto != null) && (response.connectedUserEmail != null) && (response.connectedUserName != null)) {
                            authenticationViewModel.saveConnectedUserHeartId(heartId = response.connectedHeardId)
                            authenticationViewModel.saveConnectUserEmail(email = response.connectedUserEmail)
                            authenticationViewModel.saveConnectUserName(name = response.connectedUserName)
                            authenticationViewModel.saveConnectUserPhoto(photoUrl = response.connectUserPhoto)
                            authenticationViewModel.saveUserEmail(
                                email = response.emailAddress ?: ""
                            )
                            authenticationViewModel.saveUserHeartId(
                                heartId = response.userHeartId ?: ""
                            )
                            authenticationViewModel.saveUserName(name = response.name ?: "")
                            authenticationViewModel.saveUserPhoto(
                                photoUrl = response.profilePhoto ?: ""
                            )
                            navigateToChattingScreen.invoke()
                        } else {
                            authenticationViewModel.saveUserEmail(
                                email = response.emailAddress ?: ""
                            )
                            authenticationViewModel.saveUserHeartId(
                                heartId = response.userHeartId ?: ""
                            )
                            authenticationViewModel.saveUserName(name = response.name ?: "")
                            authenticationViewModel.saveUserPhoto(
                                photoUrl = response.profilePhoto ?: ""
                            )
                            navigateToHeartStatus.invoke()
                        }
                    }
                } else if (heartStatusResponse.success == false) {
                    messageBarState.addSuccess(heartStatusResponse.message.toString())
                }
                authenticationViewModel.setLoading(false)
                heartStatusState.value.heartStatusResponse = null
            }
        }
        LaunchedEffect(key1 = jwtTokenState.value) {
            jwtTokenState.value.googleTokenResponse?.let { googleTokenResponse ->
                googleTokenResponse.response?.token?.let { jwtToken ->
                    authenticationViewModel.saveJwtToken(token = jwtToken)
                    authenticationViewModel.getHeartStatus()
                    jwtTokenState.value.googleTokenResponse = null
                }
            }
            jwtTokenState.value.error?.let {
                messageBarState.addError(java.lang.Exception(it))
                authenticationViewModel.setLoading(false)
                jwtTokenState.value.error = null

            }
        }

        AuthenticationScreen(
            loadingState = loadingState,
            oneTapState = oneTapState,
            messageBarState = messageBarState,
            onButtonClicked = {
                oneTapState.open()
                authenticationViewModel.setLoading(true)

            },
            onSuccessfulSignIn = { tokenId ->
                Log.d(WIMH, "google auth token: $tokenId")
                authenticationViewModel.getJwtWithGoogleToken(
                    token = tokenId,
                    authenticationViewModel.getUserFcm() ?: "NA"
                )
                messageBarState.addSuccess("Successfully Authenticated!")
            },
            onDialogDismissed = { message ->
                messageBarState.addError(Exception(message))
                authenticationViewModel.setLoading(false)
            },
            navigateToChat = navigateToHeartStatus,
        )
    }
}

fun NavGraphBuilder.heartRoute(navigateToChattingScreen: () -> Unit) {
    composable(route = Screen.HeartStatus.route) {
        val messageBarState = rememberMessageBarState()
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        val heartStatusViewModel: HeartStatusViewModel = hiltViewModel()
        val heartStatusState =
            heartStatusViewModel.heartStatusState.collectAsStateWithLifecycle()
        val sendProposalState =
            heartStatusViewModel.sendProposalState.collectAsStateWithLifecycle()
        val acceptProposalState =
            heartStatusViewModel.acceptProposalState.collectAsStateWithLifecycle()
        var signOutDialogOpened by rememberSaveable { mutableStateOf(false) }
        var confirmAcceptDialog by rememberSaveable { mutableStateOf(false) }
        val connectAcceptHeartId = rememberSaveable {
            mutableStateOf("")
        }
        LaunchedEffect(key1 = heartStatusState.value) {
            heartStatusState.value.heartStatusResponse?.let { heartStatusResponse ->
                if (heartStatusResponse.success == true) {
                    heartStatusResponse.response?.let { response ->
                        if ((response.connectedHeardId != null) && (response.connectUserPhoto != null) && (response.connectedUserEmail != null) && (response.connectedUserName != null)) {
                            heartStatusViewModel.saveConnectedUserHeartId(heartId = response.connectedHeardId)
                            heartStatusViewModel.saveConnectUserEmail(email = response.connectedUserEmail)
                            heartStatusViewModel.saveConnectUserName(name = response.connectedUserName)
                            heartStatusViewModel.saveConnectUserPhoto(photoUrl = response.connectUserPhoto)
                            heartStatusViewModel.saveUserEmail(
                                email = response.emailAddress ?: ""
                            )
                            heartStatusViewModel.saveUserHeartId(
                                heartId = response.userHeartId ?: ""
                            )
                            heartStatusViewModel.saveUserName(name = response.name ?: "")
                            heartStatusViewModel.saveUserPhoto(
                                photoUrl = response.profilePhoto ?: ""
                            )
                            messageBarState.addSuccess(heartStatusResponse.message.toString())
                            navigateToChattingScreen.invoke()
                        } else {
                            messageBarState.addSuccess(heartStatusResponse.message.toString())
                        }
                        heartStatusState.value.heartStatusResponse = null
                    }
                } else {
                    messageBarState.addError(Exception(heartStatusResponse.message))
                }
            }
            heartStatusState.value.error?.let {
                messageBarState.addError(Exception(it))
                heartStatusState.value.error = null
            }
        }
        LaunchedEffect(key1 = sendProposalState.value) {
            sendProposalState.value.apiResponse?.let {
                if (it.success) {
                    messageBarState.addSuccess(it.message ?: "NA")
                } else {
                    messageBarState.addError(Exception(it.message))
                }
                sendProposalState.value.apiResponse = null
            }
            sendProposalState.value.error?.let {
                messageBarState.addError(Exception(it))
                sendProposalState.value.error = null
            }
        }
        LaunchedEffect(key1 = acceptProposalState.value) {
            acceptProposalState.value.apiResponse?.let {
                if (it.success && it.response != null) {
                    if ((it.response.connectedHeardId != null) && (it.response.connectUserPhoto != null) && (it.response.connectedUserEmail != null) && (it.response.connectedUserName != null)) {
                        heartStatusViewModel.saveConnectedUserHeartId(heartId = it.response.connectedHeardId)
                        heartStatusViewModel.saveConnectUserEmail(email = it.response.connectedUserEmail)
                        heartStatusViewModel.saveConnectUserName(name = it.response.connectedUserName)
                        heartStatusViewModel.saveConnectUserPhoto(photoUrl = it.response.connectUserPhoto)
                        heartStatusViewModel.saveUserEmail(
                            email = it.response.emailAddress ?: ""
                        )
                        heartStatusViewModel.saveUserHeartId(
                            heartId = it.response.userHeartId ?: ""
                        )
                        heartStatusViewModel.saveUserName(name = it.response.name ?: "")
                        heartStatusViewModel.saveUserPhoto(
                            photoUrl = it.response.profilePhoto ?: ""
                        )
                        navigateToChattingScreen.invoke()
                    } else {
                        messageBarState.addError(java.lang.Exception("User Not Connected"))
                    }
                } else {
                    messageBarState.addError(java.lang.Exception(it.message ?: "Error"))
                }
                acceptProposalState.value.apiResponse = null
            }
            acceptProposalState.value.error?.let {
                messageBarState.addError(Exception(it))
                acceptProposalState.value.error = null
            }
        }


        HeartStatusScreen(
            messageBarState = messageBarState,
            onMenuClicked = {
                scope.launch {
                    drawerState.open()
                }
            },
            drawerState = drawerState,
            onSignOutClicked = {
                signOutDialogOpened = true
            },
            onReloadRequest = {
                heartStatusViewModel.getHeartStatus()
            },
            onLongPressOnId = {
                messageBarState.addSuccess("Copied to Clipboard")
            },
            onSendProposalClick = { heartId ->
                if (heartId.length != 36) {
                    messageBarState.addError(Exception("Invalid Heart Id"))
                } else {
                    heartStatusViewModel.sendProposalRequest(
                        connectRequest = ConnectRequest(
                            toHeartId = heartId
                        )
                    )
                }
            },
            heartStatusLoadingState = sendProposalState.value.loading ?: false,
            proposalResponseLoadingState = heartStatusState.value.loading ?: false,
            userName = heartStatusViewModel.getUserName() ?: "NA",
            userEmailAddress = heartStatusViewModel.getUserEmail() ?: "NA",
            userProfilePhoto = heartStatusViewModel.getUserPhoto() ?: "NA",
            userHeartId = heartStatusViewModel.getUserHeartId() ?: "NA",
            connectionRequestList = heartStatusViewModel.heartStatusState.value.heartStatusResponse?.response?.listOfConnectRequest
                ?: listOf(),
            onAcceptClick = { heartId ->
                connectAcceptHeartId.value = heartId
                confirmAcceptDialog = true
            },
            acceptProposalLoading = acceptProposalState.value.loading ?: false
        )

        DisplayAlertDialog(
            title = "Accept Request",
            message = "Are you sure you want to accept the request?",
            dialogOpened = confirmAcceptDialog,
            onDialogClosed = { confirmAcceptDialog = false },
            onYesClicked = {
                heartStatusViewModel.acceptProposalRequest(connectRequest = ConnectRequest(toHeartId = connectAcceptHeartId.value))
            }
        )
        DisplayAlertDialog(
            title = "Sign Out",
            message = "Are you sure you want to Sign Out from your Google Account?",
            dialogOpened = signOutDialogOpened,
            onDialogClosed = { signOutDialogOpened = false },
            onYesClicked = {
                scope.launch {

                }
            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.chatRoute(navigateToHeartStatus : () -> Unit) {
    composable(route = Screen.Chat.route) {
        val messageBarState = rememberMessageBarState()
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val chattingViewModel: ChattingViewModel = hiltViewModel()
        var signOutDialogOpened by rememberSaveable { mutableStateOf(false) }
        val scope = rememberCoroutineScope()
        val chatsList by chattingViewModel.getAllChats().collectAsStateWithLifecycle(
            initialValue = emptyList()
        )
        val disconnectHeartState by chattingViewModel.disconnectHeartState.collectAsStateWithLifecycle()
        val context = LocalContext.current
        LaunchedEffect(key1 = true) {
            chattingViewModel.toastEvent.collectLatest { message ->
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            }
        }
        LaunchedEffect(key1 = disconnectHeartState){
            disconnectHeartState.apiResponse?.let {
                if(it.success){
                    messageBarState.addSuccess(message = "${it.message}")
                    chattingViewModel.clearAllDetails()
                    navigateToHeartStatus.invoke()
                }else{
                    messageBarState.addError(exception = Exception( "${it.message}"))
                }
            }
            disconnectHeartState.error?.let {
                messageBarState.addError(exception = Exception(it))
            }
        }

        LaunchedEffect(key1 = true){
            chattingViewModel.observeConnectivity()
        }
        ChattingScreen(
            messageBarState = messageBarState,
            drawerState = drawerState,
            userName = chattingViewModel.getUserName() ?: "NA",
            userEmailAddress = chattingViewModel.getUserEmail() ?: "NA",
            userProfilePhoto = chattingViewModel.getUserPhoto() ?: "NA",
            userHeartId = chattingViewModel.getUserHeartId() ?: "NA",
            onSignOutClicked = {
                signOutDialogOpened = true
            },
            onMenuClicked = {
                scope.launch {
                    drawerState.open()
                }
            },
            connectUserProfileImage = chattingViewModel.getConnectUserPhoto(),
            connectUserName = chattingViewModel.getConnectUserName(),
            onSendClick = {
                chattingViewModel.sendMessage(it)
            },
            chatsList = chatsList,
            onDisconnectClicked = {
                chattingViewModel.disconnectRequest(connectRequest = ConnectRequest(toHeartId = chattingViewModel.getConnectHeartId().toString()))
            },
            disconnectLoadingState = disconnectHeartState.loading
        )
    }
}


fun NavGraphBuilder.heartLocationRoute() {
    composable(route = Screen.HeartLocation.route) {

    }
}
package com.petpack.whereismyheart.presentation.screen.auth
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import com.petpack.whereismyheart.presentation.screen.auth.state.GoogleTokenState
import com.petpack.whereismyheart.presentation.screen.auth.state.HeartStatusState
import com.petpack.whereismyheart.utils.Constants.CLIENT_ID
import com.stevdzasan.messagebar.ContentWithMessageBar
import com.stevdzasan.messagebar.MessageBarState
import com.stevdzasan.onetap.OneTapSignInState
import com.stevdzasan.onetap.OneTapSignInWithGoogle
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthenticationScreen(
    loadingState: Boolean,
    oneTapState: OneTapSignInState,
    messageBarState: MessageBarState,
    onButtonClicked: () -> Unit,
    onSuccessfulSignIn: (String) -> Unit,
    onFailedFirebaseSignIn: (Exception) -> Unit,
    onDialogDismissed: (String) -> Unit,
    navigateToChat: () -> Unit,
    jwtTokenState: State<GoogleTokenState>,
    heartStatusState: State<HeartStatusState>,
    onReceivedJwt: (token : String) -> Unit
) {
    Scaffold(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .statusBarsPadding()
            .navigationBarsPadding(),
        content = {
            ContentWithMessageBar(messageBarState = messageBarState) {
                AuthenticationContent(
                    jwtTokenState = jwtTokenState,
                    loadingState = loadingState,
                    onButtonClicked = onButtonClicked,
                    heartStatusState = heartStatusState,
                    onReceivedJwt = onReceivedJwt
                )
            }
        }
    )



    OneTapSignInWithGoogle(
        state = oneTapState,
        clientId = CLIENT_ID,
        onTokenIdReceived = { tokenId ->
            Log.d("tokenId", "AuthenticationScreen: $tokenId")
            onSuccessfulSignIn.invoke(tokenId)

        },
        onDialogDismissed = { message ->
            Log.d("message", "AuthenticationScreen: $message")
            onDialogDismissed(message)
        }
    )


}
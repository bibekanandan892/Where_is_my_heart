package com.petpack.whereismyheart.presentation.screen.auth


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.petpack.whereismyheart.R
import com.petpack.whereismyheart.presentation.components.GoogleButton
import com.petpack.whereismyheart.presentation.screen.auth.state.GoogleTokenState
import com.petpack.whereismyheart.presentation.screen.auth.state.HeartStatusState

@Composable
fun AuthenticationContent(
    loadingState: Boolean,
    onButtonClicked: () -> Unit,
    jwtTokenState: State<GoogleTokenState>,
    heartStatusState: State<HeartStatusState>,
    onReceivedJwt: (token: String) -> Unit
) {
    LaunchedEffect(key1 = jwtTokenState.value){
        jwtTokenState.value.googleTokenResponse?.let { googleTokenResponse ->
            googleTokenResponse.response?.token?.let {jwtToken->
                onReceivedJwt.invoke(jwtToken)
                jwtTokenState.value.googleTokenResponse = null
            }
        }
    }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .weight(9f)
                .fillMaxWidth()
                .padding(all = 40.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            heartStatusState.value.error?.let {
                Text(text = it.toString())
            }
            jwtTokenState.value.error?.let {
                Text(text = it.toString())
            }


            Column(
                modifier = Modifier.weight(weight = 10f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier.size(120.dp),
                    painter = painterResource(id = R.drawable.google_logo),
                    contentDescription = "Google Logo"
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = stringResource(id = R.string.auth_title), style = TextStyle(textAlign = TextAlign.Center),
                    fontSize = MaterialTheme.typography.titleLarge.fontSize
                )
                Text(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
                    text = stringResource(id = R.string.auth_subtitle),
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize
                )
            }
            Column(
                modifier = Modifier.weight(weight = 2f),
                verticalArrangement = Arrangement.Bottom
            ) {
                if(jwtTokenState.value.loading == true){
                    CircularProgressIndicator()
                }else{
                    GoogleButton(
                        loadingState = loadingState,
                        onClick = onButtonClicked
                    )
                }

            }
        }
    }
}
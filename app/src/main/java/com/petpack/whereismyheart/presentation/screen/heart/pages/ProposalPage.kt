package com.petpack.whereismyheart.presentation.screen.heart.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.petpack.whereismyheart.presentation.components.Proposal
import com.petpack.whereismyheart.presentation.components.ProposeButton

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun ProposalPage(
    onSendProposal: (String) -> Unit,
    proposalResponseLoadingState: Boolean,
) {
    val keyboard = LocalSoftwareKeyboardController.current
    val heartIdState = remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(color = MaterialTheme.colorScheme.surfaceColorAtElevation(10.dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        ) {
            Proposal()
        }

        Spacer(modifier = Modifier.height(30.dp))
        OutlinedTextField(modifier = Modifier
            .imePadding(),value = heartIdState.value, onValueChange = {
            heartIdState.value = it
        }, label = {
            Text(text = "Your Loved One Heart Id")
        }, singleLine = true, keyboardActions = KeyboardActions(onDone = {
            keyboard?.hide()
        }), keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done))
        if (proposalResponseLoadingState) {
            CircularProgressIndicator()
        } else {
            ProposeButton() {
                onSendProposal.invoke(heartIdState.value)
            }
        }


    }
}
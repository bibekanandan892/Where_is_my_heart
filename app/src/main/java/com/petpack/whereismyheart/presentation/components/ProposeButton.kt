package com.petpack.whereismyheart.presentation.components
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.*

import com.petpack.whereismyheart.utils.Constants.EXTRA_LARGE_PADDING

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ProposeButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Row(modifier = modifier.padding(horizontal = EXTRA_LARGE_PADDING),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center) {
            Button(modifier = Modifier.fillMaxWidth().bounceClick{

            } , colors = ButtonDefaults.buttonColors(
                backgroundColor =MaterialTheme.colorScheme.onPrimaryContainer,
                contentColor = MaterialTheme.colorScheme.surface
            ), onClick = onClick) {
                Text(text = "Send Proposal")
            }

    }
}
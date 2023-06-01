package com.petpack.whereismyheart.presentation.screen.heart.pages

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.petpack.whereismyheart.presentation.components.Heart

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HeartIdPage(heartId: String?, onLongPressOnId: (message: String) -> Unit) {
    val clipboard = LocalClipboardManager.current

    Column(
        modifier = Modifier
            .padding(horizontal = 30.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(color = MaterialTheme.colorScheme.surfaceColorAtElevation(10.dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Column(
            modifier = Modifier
                .weight(9f)
                .fillMaxWidth()
                .padding(vertical = 40.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column(
                modifier = Modifier.weight(weight = 8f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(200.dp)
                ) {
                    Heart()
                }
            }
            Column(
                modifier = Modifier
                    .weight(weight = 6f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Heart Id", style = MaterialTheme.typography.headlineLarge)
                Spacer(modifier = Modifier.height(15.dp))
                Text(text = buildAnnotatedString {
                    append(heartId ?: "NA")
                },
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontStyle = FontStyle.Normal,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.combinedClickable(
                        onLongClick = {
                            onLongPressOnId.invoke(heartId ?: "NA")
                            clipboard.setText(buildAnnotatedString {
                                append(
                                    text = heartId ?: "NA"
                                )
                            })
                        },
                        onClick = {

                        }
                    ))
            }
        }

    }
}
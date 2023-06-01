package com.petpack.whereismyheart.presentation.components.ui_status

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ShowError(message: String?,backGroundColor : Color = MaterialTheme.colorScheme.background){
    message?.let {
        Box(modifier = Modifier.fillMaxSize().background(backGroundColor), contentAlignment = Alignment.Center) {
            Text(text = it, color = MaterialTheme.colorScheme.onSurface)
        }
    }
}
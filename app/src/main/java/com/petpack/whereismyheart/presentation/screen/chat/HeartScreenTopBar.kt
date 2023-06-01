package com.petpack.whereismyheart.presentation.screen.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.petpack.whereismyheart.presentation.components.bounceClick


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChattingScreenTopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    onMenuClicked: () -> Unit,
    connectProfilePhotoUrl : String?,
    connectUserName :String?
) {
    TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(5.dp)
    ),
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            IconButton(onClick = onMenuClicked) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Hamburger Menu Icon",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        title = {
            Row (modifier = Modifier){
                Image(painter = rememberAsyncImagePainter(model = connectProfilePhotoUrl), contentDescription = "Profile Image", modifier = Modifier.clip(
                    CircleShape))
                Spacer(modifier = Modifier.width(14.dp))
                Text(text = connectUserName ?: "")
            }

        },
        actions = {

        }
    )
}
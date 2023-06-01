package com.petpack.whereismyheart.presentation.screen.heart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.petpack.whereismyheart.presentation.components.bounceClick


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeartScreenTopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    onMenuClicked: () -> Unit,
    onReloadRequest: () -> Unit,
    isLoading: Boolean,
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
            Text(text = "Heart Status")
        },
        actions = {
            IconButton(onClick = {}) {
                if (isLoading) {
                    CircularProgressIndicator()
                } else {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Person icon",
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.bounceClick {
                            onReloadRequest.invoke()
                        }
                    )
                }

            }
        }
    )
}
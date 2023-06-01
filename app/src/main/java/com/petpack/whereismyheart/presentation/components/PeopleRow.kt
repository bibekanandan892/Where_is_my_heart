package com.petpack.whereismyheart.presentation.components
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.petpack.whereismyheart.data.model.user_details.ConnectionRequest

@ExperimentalAnimationApi

@Composable
fun PeopleRow(connectionRequest: ConnectionRequest,
              onAcceptClick: (String) -> Unit = {} ) {
    Card(modifier = Modifier
        .padding(4.dp)
        .fillMaxWidth()
        .padding(horizontal = 5.dp)
        .padding(vertical = 2.dp)
        .bounceClick {
            onAcceptClick(connectionRequest.userHeartId ?: "NA")
        }
        .background(
            color = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(corner = CornerSize(16.dp))
        ) ) {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start) {
            Surface(modifier = Modifier
                .padding(12.dp)
                .size(100.dp),
                shape = RoundedCornerShape(corner = CornerSize(16.dp))) {
                Image(painter = rememberAsyncImagePainter(model = connectionRequest.profilePhoto), contentScale = ContentScale.Fit,
                    contentDescription = "profilePhoto", modifier = Modifier.clip(RoundedCornerShape(corner = CornerSize(16.dp))))
            }
            Column(modifier = Modifier.padding(4.dp)) {
                Text(text = connectionRequest.name?:"NA",
                    style = MaterialTheme.typography.headlineLarge)
                Text(text = "Email: ${connectionRequest.emailAddress}",
                    style = MaterialTheme.typography.bodySmall)
            }


        }



    }


}
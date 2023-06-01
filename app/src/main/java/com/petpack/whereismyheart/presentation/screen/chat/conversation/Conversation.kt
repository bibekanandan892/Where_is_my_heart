@file:OptIn(ExperimentalMaterial3Api::class)

package com.petpack.whereismyheart.presentation.screen.chat.conversation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.petpack.whereismyheart.presentation.components.JetChatAppBar
import com.petpack.whereismyheart.R
import com.petpack.whereismyheart.data.model.chat.MessageEntity
import com.petpack.whereismyheart.presentation.components.FunctionalityNotAvailablePopup
import com.petpack.whereismyheart.utils.getTime
import kotlinx.coroutines.launch
import java.util.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChannelNameBar(
    connectUserName: String?,
    connectUserProfileImage: String?,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    onNavIconPressed: () -> Unit = { },
) {
    var functionalityNotAvailablePopupShown by remember { mutableStateOf(false) }
    if (functionalityNotAvailablePopupShown) {
        FunctionalityNotAvailablePopup { functionalityNotAvailablePopupShown = false }
    }
    JetChatAppBar(
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        onNavIconPressed = onNavIconPressed,
        title = {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                //profile Photo
//                Image(
//                    painter = rememberAsyncImagePainter(model = connectUserProfileImage),
//                    contentDescription = "Profile Image",
//                    modifier = Modifier.clip(
//                        CircleShape
//                    )
//                )
                Spacer(modifier = Modifier.width(8.dp))
                // Channel name
                Text(
                    text = connectUserName ?: "NA",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        },
        actions = {
            // Search icon
            Icon(
                imageVector = Icons.Outlined.Search,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .clickable(onClick = { functionalityNotAvailablePopupShown = true })
                    .padding(horizontal = 12.dp, vertical = 16.dp)
                    .height(24.dp),
                contentDescription = stringResource(id = R.string.search)
            )
            // Info icon
            Icon(
                imageVector = Icons.Outlined.Info,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .clickable(onClick = { functionalityNotAvailablePopupShown = true })
                    .padding(horizontal = 12.dp, vertical = 16.dp)
                    .height(24.dp),
                contentDescription = stringResource(id = R.string.info)
            )
        }
    )
}

const val ConversationTestTag = "ConversationTestTag"

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Messages(
    chatsList: List<MessageEntity?>?,
    scrollState: LazyListState,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()
    Box(modifier = modifier) {
        LazyColumn(
            reverseLayout = true,
            state = scrollState,
            modifier = Modifier
                .testTag(ConversationTestTag)
                .fillMaxSize()
        ) {
            chatsList?.forEachIndexed { index, messageEntity ->
                val prevAuthor = chatsList.getOrNull(index + 1)?.isMine
                val isLastMessageByAuthor = prevAuthor != messageEntity?.isMine
                messageEntity?.let {
                    item {
                        Message(
                            messageEntity = messageEntity,
                            isLastMessageByAuthor = isLastMessageByAuthor,
                        )
                    }
                }
            }
        }
        // Jump to bottom button shows up when user scrolls past a threshold.
        // Convert to pixels:
        val jumpThreshold = with(LocalDensity.current) {
            JumpToBottomThreshold.toPx()
        }

        // Show the button if the first visible item is not the first one or if the offset is
        // greater than the threshold.
        val jumpToBottomButtonEnabled by remember {
            derivedStateOf {
                scrollState.firstVisibleItemIndex != 0 ||
                        scrollState.firstVisibleItemScrollOffset > jumpThreshold
            }
        }

        JumpToBottom(
            // Only show if the scroller is not at the bottom
            enabled = jumpToBottomButtonEnabled,
            onClicked = {
                scope.launch {
                    scrollState.animateScrollToItem(0)
                }
            },
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Message(
    messageEntity: MessageEntity,
    isLastMessageByAuthor: Boolean,
) {
    val borderColor = if (messageEntity.isMine) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.tertiary
    }
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = if (isLastMessageByAuthor) 10.dp else 0.dp),
        horizontalArrangement = if (!messageEntity.isMine) Arrangement.Start else Arrangement.End
    ) {
        Row(
            modifier = Modifier.width((screenWidth * 0.8).dp),
            horizontalArrangement = if (!messageEntity.isMine) Arrangement.Start else Arrangement.End
        ) {
            AuthorAndTextMessage(
                messageEntity = messageEntity,
                isUserMe = messageEntity.isMine,
                isLastMessageByAuthor = isLastMessageByAuthor,
                modifier = Modifier
                    .padding(end = 16.dp, start = 16.dp)
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AuthorAndTextMessage(
    messageEntity: MessageEntity,
    isUserMe: Boolean,
    modifier: Modifier = Modifier,
    isLastMessageByAuthor: Boolean,
) {
    Column(modifier = modifier) {

        ChatItemBubble(messageEntity, isUserMe, isLastMessageByAuthor = isLastMessageByAuthor)

        // Between bubbles
        Spacer(modifier = Modifier.height(2.dp))

    }
}

@Composable
private fun AuthorNameTimestamp(msg: Message) {
    // Combine author and timestamp for a11y.
    Row(modifier = Modifier.semantics(mergeDescendants = true) {}) {
        Text(
            text = msg.author,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .alignBy(LastBaseline)
                .paddingFrom(LastBaseline, after = 8.dp) // Space to 1st bubble
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = msg.timestamp,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.alignBy(LastBaseline),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}


@Composable
fun DayHeader(dayString: String) {
    Row(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .height(16.dp)
    ) {
        DayHeaderLine()
        Text(
            text = dayString,
            modifier = Modifier.padding(horizontal = 16.dp),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        DayHeaderLine()
    }
}

@Composable
private fun RowScope.DayHeaderLine() {
    Divider(
        modifier = Modifier
            .weight(1f)
            .align(Alignment.CenterVertically),
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatItemBubble(
    messageEntity: MessageEntity,
    isUserMe: Boolean,
    isLastMessageByAuthor: Boolean,
) {
    val backgroundBubbleColor = if (isUserMe) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }

    val chatBubbleShape = if (isUserMe) {
        if (isLastMessageByAuthor) {
            RoundedCornerShape(
                topStart = 12.dp,
                topEnd = 2.dp,
                bottomEnd = 12.dp,
                bottomStart = 12.dp
            )
        } else {
            RoundedCornerShape(
                topStart = 12.dp,
                topEnd = 12.dp,
                bottomEnd = 12.dp,
                bottomStart = 12.dp
            )
        }
    } else {
        if (isLastMessageByAuthor) {
            RoundedCornerShape(
                topStart = 2.dp,
                topEnd = 12.dp,
                bottomEnd = 12.dp,
                bottomStart = 12.dp
            )
        } else {
            RoundedCornerShape(
                topStart = 12.dp,
                topEnd = 12.dp,
                bottomEnd = 12.dp,
                bottomStart = 12.dp
            )
        }
    }

    Column {
        Surface(
            color = backgroundBubbleColor,
            shape = chatBubbleShape
        ) {
            ClickableMessage(
                messageEntity = messageEntity,
                isUserMe = isUserMe,
            )
        }

        messageEntity.image?.let {
            Spacer(modifier = Modifier.height(4.dp))
            Surface(
                color = backgroundBubbleColor,
                shape = chatBubbleShape
            ) {
                Image(
                    painter = rememberAsyncImagePainter(it),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(160.dp),
                    contentDescription = stringResource(id = R.string.attached_image)
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ClickableMessage(
    messageEntity: MessageEntity,
    isUserMe: Boolean,
) {
    val clipboard = LocalClipboardManager.current

    val codeSnippetBackground =
        if (isUserMe) {
                MaterialTheme.colorScheme.inverseOnSurface
        } else {
            MaterialTheme.colorScheme.onSurface
        }
    val styledMessage = messageFormatter(
        text = messageEntity.message,
        primary = isUserMe
    )
    Box(contentAlignment = Alignment.BottomEnd) {
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.combinedClickable(onLongClick = {
                clipboard.setText(buildAnnotatedString {
                    append(
                        text = styledMessage
                    )
                })
            }, onClick = {

            })
        ) {
            Text(
                text = styledMessage,
                style = MaterialTheme.typography.bodyLarge.copy(color = LocalContentColor.current),
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
            )
            Spacer(modifier = Modifier.width(60.dp))

        }
        Row {
            Text(
                text = getTime(messageEntity.timestamp),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(end = 5.dp, bottom = 3.dp),
                color = codeSnippetBackground
            )
            if (isUserMe) {
                Image(
                    painter = when (messageEntity.messageStatus) {
                        "N" -> {
                            painterResource(id = R.drawable.clock_icon)
                        }
                        "S" -> {
                            painterResource(id = R.drawable.single_tick_icon)
                        }
                        "R" -> {
                            painterResource(id = R.drawable.double_tick_icon)
                        }
                        else -> {
                            painterResource(id = R.drawable.ic_jetchat)
                        }
                    }, contentDescription = "message Status", modifier = Modifier.size(15.dp)
                )
            }
        }


    }
}

private val JumpToBottomThreshold = 56.dp

/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.petpack.whereismyheart.data

import com.petpack.whereismyheart.presentation.screen.chat.conversation.ConversationUiState
import com.petpack.whereismyheart.presentation.screen.chat.conversation.Message
import com.petpack.whereismyheart.R
import com.petpack.whereismyheart.data.EMOJIS.EMOJI_CLOUDS
import com.petpack.whereismyheart.data.EMOJIS.EMOJI_FLAMINGO
import com.petpack.whereismyheart.data.EMOJIS.EMOJI_MELTING
import com.petpack.whereismyheart.data.EMOJIS.EMOJI_PINK_HEART
import com.petpack.whereismyheart.data.EMOJIS.EMOJI_POINTS
import com.petpack.whereismyheart.data.model.chat.MessageEntity




/**
 * Example colleague profile
 */
//val colleagueProfile = ProfileScreenState(
//    userId = "12345",
//    photo = R.drawable.someone_else,
//    name = "Taylor Brooks",
//    status = "Away",
//    displayName = "taylor",
//    position = "Senior Android Dev at Openlane",
//    twitter = "twitter.com/taylorbrookscodes",
//    timeZone = "12:25 AM local time (Eastern Daylight Time)",
//    commonChannels = "2"
//)

/**
 * Example "me" profile.
 */
//val meProfile = ProfileScreenState(
//    userId = "me",
//    photo = R.drawable.ali,
//    name = "Ali Conors",
//    status = "Online",
//    displayName = "aliconors",
//    position = "Senior Android Dev at Yearin\nGoogle Developer Expert",
//    twitter = "twitter.com/aliconors",
//    timeZone = "In your timezone",
//    commonChannels = null
//)

object EMOJIS {
    // EMOJI 15
    const val EMOJI_PINK_HEART = "\uD83E\uDE77"

    // EMOJI 14 🫠
    const val EMOJI_MELTING = "\uD83E\uDEE0"

    // ANDROID 13.1 😶‍🌫️
    const val EMOJI_CLOUDS = "\uD83D\uDE36\u200D\uD83C\uDF2B️"

    // ANDROID 12.0 🦩
    const val EMOJI_FLAMINGO = "\uD83E\uDDA9"

    // ANDROID 12.0  👉
    const val EMOJI_POINTS = " \uD83D\uDC49"
}

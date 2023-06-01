package com.petpack.whereismyheart.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.airbnb.lottie.compose.*
import com.petpack.whereismyheart.R

@Composable
fun Proposal(){
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.proposal))
    val progress by animateLottieCompositionAsState(composition = composition, iterations = LottieConstants.IterateForever)
    LottieAnimation(
        composition = composition,
        progress = progress
    )
}
package com.petpack.whereismyheart

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.petpack.whereismyheart.navigation.Screen
import com.petpack.whereismyheart.navigation.SetupNavGraph
import com.petpack.whereismyheart.presentation.screen.auth.AuthenticationViewModel
import com.petpack.whereismyheart.ui.theme.WhereIsMyHeartTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            val authenticationViewModel: AuthenticationViewModel = hiltViewModel()
            val startRoute = if(authenticationViewModel.getJwtToken()!="") Screen.HeartStatus.route else Screen.Authentication.route
            val navController = rememberNavController()
            Log.d("jwtToken", "onCreate: ${authenticationViewModel.getJwtToken().toString()}")
            WhereIsMyHeartTheme {
                SetupNavGraph(
                    startDestination = startRoute,
                    navController = navController,
                    authenticationViewModel = authenticationViewModel
                )
            }
        }
    }
}


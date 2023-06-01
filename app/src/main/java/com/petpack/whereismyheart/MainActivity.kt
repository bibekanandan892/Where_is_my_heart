package com.petpack.whereismyheart

import android.Manifest.permission.POST_NOTIFICATIONS
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.petpack.whereismyheart.navigation.Screen
import com.petpack.whereismyheart.navigation.SetupNavGraph
import com.petpack.whereismyheart.presentation.components.ConnectivityStatus
import com.petpack.whereismyheart.presentation.screen.auth.AuthenticationViewModel
import com.petpack.whereismyheart.ui.theme.WhereIsMyHeartTheme
import com.petpack.whereismyheart.utils.Constants.WIMH
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("SuspiciousIndentation")
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        if (ContextCompat.checkSelfPermission(
                this,
                POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val launcher = registerForActivityResult<String, Boolean>(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean? ->
                if (isGranted == false) {

                }
            }
            launcher.launch(POST_NOTIFICATIONS)
        }



        setContent {

            val authenticationViewModel: AuthenticationViewModel = hiltViewModel()
            val mainViewModel : MainViewModel = hiltViewModel()
            val isConnectivityAvailable by mainViewModel.isConnectivityAvailable
            Log.d(WIMH, "Jwt token: ${authenticationViewModel.getJwtToken()}")
            LaunchedEffect(key1 = true) {
                if (authenticationViewModel.getUserFcm() == "" || authenticationViewModel.getUserFcm() == null) {
                    FirebaseMessaging.getInstance().token.addOnCompleteListener(
                        OnCompleteListener { task ->
                            if (!task.isSuccessful) {
                                Log.w(
                                    WIMH,
                                    "Fetching FCM registration token failed",
                                    task.exception
                                )
                                return@OnCompleteListener
                            }
                            val token = task.result
                            Log.d(WIMH, "Fcm Token: $token")

                            authenticationViewModel.saveUserFcm(fcmToken = token)
                        })
                } else {
                    Log.d(WIMH, "Fcm Token: ${authenticationViewModel.getUserFcm()}")
                }
            }
            val startRoute =
                if (authenticationViewModel.getJwtToken() != "" && authenticationViewModel.getConnectedHeartId() != "" && authenticationViewModel.getUserHeartId() != "") {
                    Screen.Chat.route
                } else if (authenticationViewModel.getJwtToken() != "") {
                    Screen.HeartStatus.route
                } else {
                    Screen.Authentication.route
                }
            val navController = rememberNavController()
            WhereIsMyHeartTheme {
                Column {
                    isConnectivityAvailable?.let {
                        ConnectivityStatus(it)
                    }
                    SetupNavGraph(
                        startDestination = startRoute,
                        navController = navController,
                        authenticationViewModel = authenticationViewModel,
                    )
                }

            }
        }
    }


}


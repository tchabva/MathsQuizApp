package uk.learning.mathquiz.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import uk.learning.mathquiz.R

/*
The splash screen for the application
 */

@Composable
fun SplashScreen(navController: NavController){

    /*
    remember the Lottie composition, which accepts the lottie composition result.
     */
    val composition by rememberLottieComposition(
        //Where we put our lottie file
        LottieCompositionSpec.RawRes(R.raw.placeholder_logo_splash)
    )

    //To control the animation
    val logoAnimationState = animateLottieCompositionAsState(
        composition = composition,
        iterations = 1
    )

    //The background column for the Splash Screen
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        //The Lottie Splash Screen Animation
        LottieAnimation(
            modifier = Modifier.size(200.dp),
            composition = composition,
            progress = { logoAnimationState.progress }
        )

        //This will move us to the Home Screen and pop the Splash screen from the BackStack
        if (logoAnimationState.isAtEnd && logoAnimationState.isPlaying){
            navController.popBackStack("Splash", true)
            navController.navigate("Home")
        }
    }
}

@Preview
@Composable
fun SplashScreenPreview(){
    SplashScreen(rememberNavController())
}
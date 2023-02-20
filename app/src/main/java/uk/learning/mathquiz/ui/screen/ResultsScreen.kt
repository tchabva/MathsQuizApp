package uk.learning.mathquiz.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import uk.learning.mathquiz.R
import kotlin.properties.Delegates

@Composable
fun ResultsScreen(
    navController: NavController,
    userName: String,
    correctAnswers: String
) {
    //This will create the context for the toast.
    var operatorImage by Delegates.notNull<Int>()
    var resultText by remember { mutableStateOf("Hey, Congratulations") }


    //Sets the text and images displayed on the Results Screen UI dependent on the user's score
    when (correctAnswers.toInt()) {
        0 -> {
            resultText = stringResource(R.string.results_0_text)
            operatorImage = (R.drawable.ic_results_0)
        }
        in 1..4 -> {
            resultText = stringResource(R.string.results_1_4_text)
            operatorImage = (R.drawable.ic_results_1_4)
        }
        in 5..7 -> {
            resultText = stringResource(R.string.results_5_7_text)
            operatorImage = (R.drawable.ic_results_5_7)
        }
        in 8..9 -> {
            resultText = stringResource(R.string.results_8_9_text)
            operatorImage = (R.drawable.ic_results_8_9)
        }
        in 10..11 -> {
            resultText = stringResource(R.string.results_10_11_text)
            operatorImage = (R.drawable.ic_results_10_11)
        }
        12 -> {
            resultText = stringResource(R.string.results_12_text)
            operatorImage = (R.drawable.ic_trophy)
        }
        else -> {
            resultText = ""
            operatorImage = (R.drawable.ic_results_1_4)
        }
    }

    //Column containing the UI of the Results Screen
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.green))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        //Results
        Image(
            modifier = Modifier.size(200.dp),
            painter = painterResource(operatorImage),
            contentDescription = stringResource(R.string.result_image),
            contentScale = ContentScale.Fit
        )
        Column(
            Modifier.fillMaxHeight(0.5f),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            //Results Text
            ResultsScreenText(text = resultText, size = 40, color = R.color.yellow)

            //UserName
            ResultsScreenText(text = userName, size = 48, color = R.color.orange)

            //Test Result Score Text
            ResultsScreenText(
                text = stringResource(id = R.string.result_score, correctAnswers),
                size = 32,
                color = R.color.white
            )
        }

        //Button Arrangement column
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            //Play Again Button
            ResultsScreenButton(
                textResource = R.string.play_again_btn,
                verticalPadding = 16,
                onClick = {
                    //Will navigate back to the Landing page and pop the BackStack
                    navController.popBackStack("Landing/{userName}", false)
                })

            //Home Button
            ResultsScreenButton(
                textResource = R.string.go_home_btn,
                verticalPadding = 32,
                onClick = {
                    //Will navigate back to the Home page and pop the BackStack
                    navController.popBackStack("Home", false)
                }
            )

            //History Button
            ResultsScreenButton(
                textResource = R.string.test_history_btn_text,
                verticalPadding = 16,
                onClick = {
                    //Navigates to the Test History Page
                    navController.navigate("History")
                }
            )
        }
    }
}

@Composable
fun ResultsScreenText(text: String, size: Int, color: Int) {
    Text(
        text = text,
        fontFamily = FontFamily.Serif,
        color = colorResource(color),
        fontSize = size.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        lineHeight = 40.sp
    )
}

//Button Function, That will navigation to the appropriate destination
@Composable
fun ResultsScreenButton(textResource: Int, verticalPadding: Int, onClick: () -> Unit = {}) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = verticalPadding.dp),
        colors = ButtonDefaults.buttonColors(colorResource(R.color.yellow)),
        elevation = ButtonDefaults.buttonElevation(16.dp),
        onClick = {
            onClick()
        }
    ) {
        Text(
            stringResource(textResource),
            fontSize = 32.sp,
            fontFamily = FontFamily.Serif,
            color = Color.Black,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview
@Composable
fun ResultsScreenPreview() {
    ResultsScreen(rememberNavController(), "Tawanda", "10")
}
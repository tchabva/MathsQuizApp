package uk.learning.mathquiz.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import uk.learning.mathquiz.R
import uk.learning.mathquiz.ui.theme.Blue
import uk.learning.mathquiz.ui.theme.GreenMain
import uk.learning.mathquiz.ui.theme.Purple
import uk.learning.mathquiz.ui.theme.Red

@Composable
fun LandingScreen(navController: NavController, userName: String){

    //Main Column
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        
        //Hi Text
        LandingPageText(stringResource(R.string.landing_page_txt), Blue)
        
        //userName String text
        LandingPageText(userName, Red)

        //userName String text
        LandingPageText(stringResource(R.string.pick), Purple)

        //Column for the operator buttons
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            OperatorButton(
                navController = navController,
                image = R.drawable.ic_multiplication_logo,
                operator = stringResource(R.string.multiplication),
                userName = userName
            )

            //or Text
            LandingPageText(stringResource(R.string.or), Color.Black)

            OperatorButton(
                navController = navController,
                image = R.drawable.ic_division_logo,
                operator = stringResource(R.string.division),
                userName = userName
            )
        }
    }
}


@Composable
fun LandingPageText(textValue: String, color: Color){
    //userName String text
    Text(
        text = textValue,
        fontSize = 62.sp,
        color = color,
        fontWeight = FontWeight.ExtraBold,
        fontFamily = FontFamily.Serif
    )
}

//Function for the operator buttons
@Composable
fun OperatorButton(
    navController: NavController,
    image: Int,
    operator: String,
    userName: String
){
    //Operator Button
    Button(
        modifier = Modifier
            .size(200.dp),
        colors = ButtonDefaults.buttonColors(GreenMain),
        shape = RoundedCornerShape(8.dp),
        elevation = ButtonDefaults.buttonElevation(16.dp),
        onClick = {
            navController.navigate("NumberTable/${userName}/${operator}")
        }
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(image),
            contentDescription = operator,
            contentScale = ContentScale.Fit
        )
    }
}

@Preview
@Composable
fun LandingScreenPreview (){
    LandingScreen(navController = rememberNavController(), userName = "Tawanda")
}
package uk.learning.mathquiz.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
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
import uk.learning.mathquiz.models.NumberModel
import uk.learning.mathquiz.ui.numberTableScreen.NumberTableState
import uk.learning.mathquiz.ui.numberTableScreen.NumberTableViewModel
import uk.learning.mathquiz.ui.quizQuestionScreen.Operator
import kotlin.properties.Delegates

@Composable
fun NumberTableScreen(
    navController: NavController,
    userName: String,
    operator: String,
    numberTableViewModel: NumberTableViewModel
) {

    var operatorImage by Delegates.notNull<Int>()

    val currentState = numberTableViewModel.state.collectAsState()

    val numberList = numberTableViewModel.numberList

    operatorImage = when (currentState.value) {
        is NumberTableState.DivisionState -> {
            R.drawable.ic_division_logo
        }
        is NumberTableState.MultiplicationState -> {
            R.drawable.ic_multiplication_logo
        }
    }

    //The base column
    Column(
        modifier = Modifier
            .background(colorResource(R.color.green))
            .padding(16.dp)
    ) {

        //Top Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            //LHS Operator Image
            Image(
                modifier = Modifier.size(40.dp),
                painter = painterResource(operatorImage),
                contentDescription = operator,
                contentScale = ContentScale.Fit
            )
            //userName String text
            Text(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = stringResource(R.string.pick_a_number),
                fontSize = 32.sp,
                color = colorResource(R.color.orange),
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.Serif
            )
            //RHS Operator Image
            Image(
                modifier = Modifier.size(40.dp),
                painter = painterResource(operatorImage),
                contentDescription = operator,
                contentScale = ContentScale.Fit
            )
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {

            //Lazy vertical grid that will populate based on the numbers in the numberTable List
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalArrangement = Arrangement.spacedBy(32.dp)

            ) {
                items(numberList.size) {
                        index ->
                    NumberButton(
                        number = numberList[index],
                    ){
                        numberTableViewModel.onNumberPressed(
                            navController = navController,
                            userName = userName,
                            operator = operator,
                            number = numberList[index].id
                        )
                    }
                }
            }
        }
    }
}

//Function that will create the button numbers
@Composable
fun NumberButton(
    number: NumberModel,
    onClick: () -> Unit = {}
) {

    //Operator Button
    Button(
        modifier = Modifier
            .size(90.dp),
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        shape = RoundedCornerShape(8.dp),
        elevation = ButtonDefaults.buttonElevation(2.dp),
        onClick = {
            onClick()
        }
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = number.image),
            contentDescription = stringResource(id = number.numberString),
            contentScale = ContentScale.Fit
        )
    }
}

@Preview
@Composable
fun NumberTablePreview() {
    NumberTableScreen(
        navController = rememberNavController(),
        userName = "Tawa",
        operator = stringResource(id = R.string.division),
        numberTableViewModel = NumberTableViewModel(Operator.MULTIPLICATION)
    )
}
package uk.learning.mathquiz.ui.screen

import android.app.Application
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import uk.learning.mathquiz.R
import uk.learning.mathquiz.data.MathsQuizDBViewModel
import uk.learning.mathquiz.data.MathsQuizDbViewModelFactory
import uk.learning.mathquiz.data.TestResult

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(navController: NavController){

    var operatorIcon by remember { mutableStateOf(Icons.Default.MoreVert) }
    val context = LocalContext.current
    val mMathsQuizDBViewModel: MathsQuizDBViewModel = viewModel(
        factory = MathsQuizDbViewModelFactory(context.applicationContext as Application)
    )

    val resultsList = mMathsQuizDBViewModel.allTestResults.observeAsState(listOf()).value

    //Top AppBar Scaffold
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(colorResource(id = R.color.orange)),
                title = {
                    Text(
                        text = stringResource(id = R.string.test_history_label),
                        fontFamily = FontFamily.Serif,
                        color = Color.Black,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, 
                            contentDescription = stringResource(id = R.string.history_appbar_back)
                        )
                    }
                },
                //This will contain all of the icons at the end of the AppBar
                actions = {
//                    var menuExpanded by remember { mutableStateOf(false) }
//                    //The filter for the operators TODO
//                    IconButton(onClick = { menuExpanded = true }) {
//                        Icon(operatorIcon,
//                            contentDescription = stringResource(id = R.string.operator_filter)
//                        )
//                    }
//
//
//                    //The filter for the numbers TODO
//                    IconButton(onClick = { }) {
//                        Icon(Icons.Default.List,
//                            contentDescription = stringResource(id = R.string.number_filter)
//                        )
//                    }
                }
            )
        }
    ) {
        //Column containing the LazyColumn
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(it)
        ) {
            //The list of Test History Items that are in the DB
            LazyColumn {
                items(resultsList.size){
                    index ->
                    TestHistoryItem(resultsList[index])
                }
            }
        }
    }
}

//Test History Item
@Composable
fun TestHistoryItem(result: TestResult) {
    //Background card
    Card(
        Modifier
            .padding(start = 8.dp, end = 8.dp, top = 8.dp)
            .border(
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(2.dp, color = Color.Black)
            )
    ) {
        Box(Modifier.background(colorResource(R.color.grey))) {
            //Row containing all of the relevant information
            Row(
                Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                //Operator Image
                Image(
                    modifier = Modifier.size(20.dp),
                    painter = when (result.operator) {
                        stringResource(R.string.multiplication) -> painterResource(id = R.drawable.ic_multiplication_logo)
                        stringResource(R.string.division) -> painterResource(R.drawable.ic_division_logo)
                        else -> {
                            painterResource(id = R.drawable.ic_logo)
                        }
                    },
                    contentDescription = when (result.operator) {
                        stringResource(R.string.multiplication) -> stringResource(R.string.multiplication)
                        stringResource(R.string.division) -> stringResource(R.string.division)
                        else -> stringResource(id = androidx.compose.ui.R.string.default_error_message)
                    }
                )

                //Number Image
                Image(
                    modifier = Modifier.size(20.dp),
                    painter = getNumberImage(number = result.number),
                    contentDescription = getNumberDescription(number = result.number)
                )
                //Name Text
                Text(
                    modifier = Modifier.weight(1f),
                    text = result.name,
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Serif,
                    color = colorResource(R.color.orange),
                    textAlign = TextAlign.Center
                )
                //Score Text
                Text(
                    modifier = Modifier.padding(end = 8.dp),
                    text = stringResource(id = R.string.item_history_score_text, result.score),
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Serif,
                    color = Color.Black
                )
                //Date Text
                Text(
                    text = result.date,
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Serif,
                    color = colorResource(R.color.green)
                )
            }
        }
    }
}

//This function gets the right number for the each Test Result
@Composable
private fun getNumberImage(number: String): Painter{
    return when(number){
        stringResource(id = R.string.number_1) -> painterResource(id = R.drawable.ic_number_1)
        stringResource(id = R.string.number_2) -> painterResource(id = R.drawable.ic_number_2)
        stringResource(id = R.string.number_3) -> painterResource(id = R.drawable.ic_number_3)
        stringResource(id = R.string.number_4) -> painterResource(id = R.drawable.ic_number_4)
        stringResource(id = R.string.number_5) -> painterResource(id = R.drawable.ic_number_5)
        stringResource(id = R.string.number_6) -> painterResource(id = R.drawable.ic_number_6)
        stringResource(id = R.string.number_7) -> painterResource(id = R.drawable.ic_number_7)
        stringResource(id = R.string.number_8) -> painterResource(id = R.drawable.ic_number_8)
        stringResource(id = R.string.number_9) -> painterResource(id = R.drawable.ic_number_9)
        stringResource(id = R.string.number_10) -> painterResource(id = R.drawable.ic_number_10)
        stringResource(id = R.string.number_11) -> painterResource(id = R.drawable.ic_number_11)
        stringResource(id = R.string.number_12) -> painterResource(id = R.drawable.ic_number_12)
        else -> {painterResource(id = R.drawable.ic_clear)}
    }
}
//This function gets the right content description for each number
@Composable
private fun getNumberDescription(number: String): String{
    return when(number){
        stringResource(id = R.string.number_1) ->  stringResource(id = R. string.number_1)
        stringResource(id = R.string.number_2) ->  stringResource(id = R. string.number_2)
        stringResource(id = R.string.number_3) ->  stringResource(id = R. string.number_3)
        stringResource(id = R.string.number_4) ->  stringResource(id = R. string.number_4)
        stringResource(id = R.string.number_5) ->  stringResource(id = R. string.number_5)
        stringResource(id = R.string.number_6) ->  stringResource(id = R. string.number_6)
        stringResource(id = R.string.number_7) ->  stringResource(id = R. string.number_7)
        stringResource(id = R.string.number_8) ->  stringResource(id = R. string.number_8)
        stringResource(id = R.string.number_9) ->  stringResource(id = R. string.number_9)
        stringResource(id = R.string.number_10) ->  stringResource(id = R. string.number_10)
        stringResource(id = R.string.number_11) ->  stringResource(id = R. string.number_11)
        stringResource(id = R.string.number_12) ->  stringResource(id = R. string.number_12)
        else -> {"Error"}
    }
}

@Preview
@Composable
fun HistoryScreenPreview(){
    HistoryScreen(rememberNavController())
}
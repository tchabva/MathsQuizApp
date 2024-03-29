package uk.learning.mathquiz.ui.resultsHistoryScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
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
import uk.learning.mathquiz.data.TestResult
import uk.learning.mathquiz.ui.theme.Blue
import uk.learning.mathquiz.ui.theme.GreenSecond
import uk.learning.mathquiz.ui.theme.Orange
import uk.learning.mathquiz.ui.theme.Purple
import uk.learning.mathquiz.ui.theme.Red
import uk.learning.mathquiz.ui.theme.Yellow2

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(navController: NavController){
    //Variables
    var operatorIcon by remember { mutableStateOf(R.drawable.ic_logo) }
    var numberIcon = (painterResource(id = R.drawable.ic_filter_alt))
    var iconTint by remember{ mutableStateOf(Color.LightGray) }
    var numberTint by remember{ mutableStateOf(Color.Black) }
    val multiplicationString = stringResource(id = R.string.multiplication)
    val divisionString = stringResource(id = R.string.division)
    val historyViewModel: HistoryViewModel = viewModel()
    val currentState = historyViewModel.state.collectAsState()
    var showClearIcon by remember { mutableStateOf(false) }
    var showOperatorDialog by remember{ mutableStateOf(false) }
    var showNumberListDialog by remember{ mutableStateOf(false) }

    //Collects the filtered result from the ViewModel
    val resultsList = historyViewModel.getFilteredResults().observeAsState(listOf()).value

    //Dictates what we see based on the state of the filter
    when(currentState.value){
        is HistoryScreenState.FiltersActive -> {
            showClearIcon = true
            operatorIcon = when(historyViewModel.storedOperator){
                stringResource(R.string.multiplication) -> R.drawable.ic_multiplication_logo
                stringResource(R.string.division) -> R.drawable.ic_division_logo
                else -> {
                    R.drawable.ic_logo
                }
            }
            numberIcon = getNumberImage(number = historyViewModel.storedNumber.toString())
            numberTint = getNumberIconColor(number = historyViewModel.storedNumber.toString())
            iconTint = if(historyViewModel.storedOperator == stringResource(id = R.string.multiplication)){
                Blue
            }else{
                Red
            }
        }
        is HistoryScreenState.NoFilter -> {
            showClearIcon = false
            operatorIcon = R.drawable.ic_logo
            numberIcon = (painterResource(id = R.drawable.ic_numbers_icon_button))
            iconTint = Color.LightGray
        }
    }

    //Top AppBar Scaffold
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(Purple),
                title = {
                    Text(
                        text = stringResource(id = R.string.test_history_label),
                        fontFamily = FontFamily.Serif,
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.history_appbar_back),
                            tint = Color.White
                        )
                    }
                },
                //This will contain all of the icons at the end of the AppBar
                actions = {
                    /*
                    Will show the clear filter Icon button if the HistoryState is in the active
                    state and will clear the filter and revert the state back when clicked
                     */
                    if (showClearIcon){
                        IconButton(onClick = {
                            historyViewModel.filterByNumber(null)
                            historyViewModel.filterByOperator(null)
                        }) {
                            Icon(
                                Icons.Default.Clear,
                                tint = Color.White,
                                contentDescription = stringResource(id = R.string.operator_filter)
                            )
                        }
                    }

                    //Icon button for the Operator Dialog
                    IconButton(onClick = { showOperatorDialog = true }) {
                        Icon(
                            painter = painterResource(id = operatorIcon),
                            contentDescription = stringResource(id = R.string.operator_filter),
                            tint = iconTint
                        )
                    }

                    //The filter for the Number Dialog
                    IconButton(onClick = { showNumberListDialog = true }) {
                        Icon(
                            painter = numberIcon,
                            contentDescription = stringResource(id = R.string.number_filter),
                            tint = numberTint
                        )
                    }
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

    //Operator Dialog and filter
    if(showOperatorDialog){
        OperatorDialog(
            //Operator dialog buttons
            onDismissRequest = { showOperatorDialog = false },
            onAllOperatorsClick = {
                historyViewModel.filterByOperator(null)
                showOperatorDialog = false
            },
            onMultiplicationClick = {
                historyViewModel.filterByOperator(multiplicationString)
                showOperatorDialog = false
            },
            onDivisionClick = {
                historyViewModel.filterByOperator(divisionString)
                showOperatorDialog = false
            }
        )
    }

    //Number Dialog and filter
    if(showNumberListDialog){
        NumbersDialog(
            //Number dialog buttons
            onDismissRequest = { showNumberListDialog = false },
            onNumberSelected = { number ->
                historyViewModel.filterByNumber(number.toString())
                showNumberListDialog = false
            },
            onAllNumbersClick = {
                historyViewModel.filterByNumber(null)
                showNumberListDialog = false
            }
        )
    }
}

//Test History Item Composable function
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
private fun getNumberIconColor(number: String): Color{
    return when(number){
        stringResource(id = R.string.number_1) -> Color.Red
        stringResource(id = R.string.number_2) -> Color.Yellow
        stringResource(id = R.string.number_3) -> Color.Blue
        stringResource(id = R.string.number_4) -> GreenSecond
        stringResource(id = R.string.number_5) -> Color.DarkGray
        stringResource(id = R.string.number_6) -> Yellow2
        stringResource(id = R.string.number_7) -> Blue
        stringResource(id = R.string.number_8) -> Orange
        stringResource(id = R.string.number_9) -> Color.Magenta
        stringResource(id = R.string.number_10) -> Red
        stringResource(id = R.string.number_11) -> Color.Green
        stringResource(id = R.string.number_12) -> Color.White
        else -> {Color.Black}
    }
}

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
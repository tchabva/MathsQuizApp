package uk.learning.mathquiz.ui.homePageScreen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import uk.learning.mathquiz.R
import uk.learning.mathquiz.ui.theme.GreenMain
import uk.learning.mathquiz.ui.theme.Purple
/*
The home page, where users can enter their name
 */

@Composable
fun HomeScreen(navController: NavController) {

    val homeViewModel: HomeViewModel = viewModel()
    val state by homeViewModel.state.collectAsState()
    var userName by remember{ mutableStateOf("") }
    var showContinueAsButton by remember{ mutableStateOf(false) }

    when(state){
        is HomeScreenState.EmptyDb -> {
            showContinueAsButton = false

//            //Background column for the entire HomeScreen when the Database is Empty
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(Color.White)
//                    .padding(16.dp),
//                horizontalAlignment = Alignment.CenterHorizontally,
//
//                ) {
//
//                Spacer(modifier = Modifier.height(36.dp))
//
//                //Maths Quiz Text
//                HomeScreenText(
//                    textResource = R.string.app_name_internal,
//                    size = 62,
//                    colour = Purple,
//                    weight = FontWeight.ExtraBold
//                )
//
//                Spacer(modifier = Modifier.height(44.dp))
//
//                //Card details function
//                EnterNameCard(navController)
//
//                //Column containing Test History Button
//                Column(
//                    modifier = Modifier.fillMaxSize(),
//                    verticalArrangement = Arrangement.Center
//                ) {
//                    //Test History Button
//                    HomeScreenButton(
//                        textResource = R.string.test_history_btn_text,
//                        padding = 8
//                    ) {
//                        navController.navigate("History")
//                    }
//                }
//            }
        }
        is HomeScreenState.DbNotEmpty -> {
            showContinueAsButton = true
            userName = homeViewModel.latestTestResult.observeAsState().value?.name.toString()

//            //Background column for the entire HomeScreen when the Database is not Empty
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(Color.White)
//                    .padding(16.dp),
//                horizontalAlignment = Alignment.CenterHorizontally,
//
//                ) {
//
//                Spacer(modifier = Modifier.height(36.dp))
//
//                //Maths Quiz Text
//                HomeScreenText(
//                    textResource = R.string.app_name_internal,
//                    size = 62,
//                    colour = Purple,
//                    weight = FontWeight.ExtraBold
//                )
//
//                Spacer(modifier = Modifier.height(44.dp))
//
//                //Card details function
//                EnterNameCard(navController)
//
//                //Column containing the Continue As and Test History Buttons
//                Column(
//                    modifier = Modifier.fillMaxSize(),
//                    verticalArrangement = Arrangement.Center
//                ) {
//                    //Continue as Button
//                    HomeScreenButton(
//                        textResource = R.string.continue_as_btn_txt,
//                        textArg = userName,
//                        padding = 8
//                    ) {
//                        navController.navigate("Landing/${userName}")
//                    }
//
//                    Spacer(modifier = Modifier.height(32.dp))
//
//                    //Test History Button
//                    HomeScreenButton(
//                        textResource = R.string.test_history_btn_text,
//                        padding = 8
//                    ) {
//                        navController.navigate("History")
//                    }
//                }
//            }
        }
    }

    //Background column for the entire HomeScreen when the Database is not Empty
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {

        Spacer(modifier = Modifier.height(36.dp))

        //Maths Quiz Text
        HomeScreenText(
            textResource = R.string.app_name_internal,
            size = 62,
            colour = Purple,
            weight = FontWeight.ExtraBold
        )

        Spacer(modifier = Modifier.height(44.dp))

        //Card details function
        EnterNameCard(navController)

        //Column containing the Continue As and Test History Buttons
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {

            //Will show the "Continue as" button when there is an entry in the DB
            if(showContinueAsButton){
                //Continue as Button
                HomeScreenButton(
                    textResource = R.string.continue_as_btn_txt,
                    textArg = userName,
                    padding = 8
                ) {
                    navController.navigate("Landing/${userName}")
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            //Test History Button
            HomeScreenButton(
                textResource = R.string.test_history_btn_text,
                padding = 8
            ) {
                navController.navigate("History")
            }
        }
    }
}

//Enter name card
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnterNameCard(navController: NavController) {
    //This will create the context for the toast.
    val context = LocalContext.current
    //Takes care of what is currently in focus in the application
    val localFocusManager = LocalFocusManager.current

    //variables
    var nameState by remember { mutableStateOf("") }
    var userName by rememberSaveable { mutableStateOf("") }

    //The Card background
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(16.dp),
        border = BorderStroke(4.dp, Purple)
    ) {

        Box(
            modifier = Modifier
                .background(Color.White)
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(8.dp))

                //Welcome Text
                HomeScreenText(
                    textResource = R.string.card_welcome_txt,
                    size = 32,
                    colour = Purple,
                    weight = FontWeight.SemiBold
                )

                //Please Enter Name Text
                HomeScreenText(
                    textResource = R.string.card_instruction,
                    size = 16,
                    colour = Purple,
                    weight = FontWeight.SemiBold
                )

                //The EnterName OutlinedTextField
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    shape = RoundedCornerShape(8.dp),
                    value = nameState,
                    onValueChange = { nameState = it },
                    textStyle = TextStyle(
                        fontFamily = FontFamily.Serif,
                        fontSize = 16.sp
                    ),
                    label = {
                        HomeScreenText(
                            textResource = R.string.text_field_label,
                            size = 16,
                            colour = Purple,
                            weight = FontWeight.Medium
                        )
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = Purple,
                        focusedBorderColor = Purple,
                        focusedLabelColor = Purple,
                        containerColor = Color.White,
                        cursorColor = GreenMain,
                        unfocusedLabelColor = Purple,
                        unfocusedBorderColor = Purple,
                        selectionColors = TextSelectionColors(
                            handleColor = GreenMain,
                            backgroundColor = GreenMain
                        )
                    ),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done,
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            //Will close the keyboard and remove the focus from the searchbar
                            localFocusManager.clearFocus()
                        }
                    ),
                    singleLine = true
                )

                //The Start Button
                HomeScreenButton(
                    textResource = R.string.card_btn_text,
                    padding = 8
                ) {
                    userName = nameState
                    if (userName.isNotBlank()) {
                        localFocusManager.clearFocus()
                        println(userName)
                        nameState = ""
                        navController.navigate("Landing/${userName}")
                    } else {
                        clickToast(context, "Please enter your name!")
                    }
                }
                //Adds some space at the bottom of the card
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

//Pass the context through the function
fun clickToast(context: Context, toast: String) {
    Toast.makeText(context, toast, Toast.LENGTH_SHORT).show()
}

//Home Buttons Function, That will navigation to the appropriate destination
@Composable
fun HomeScreenButton(
    textResource: Int,
    textArg: String? = null,
    padding: Int,
    onClick: () -> Unit = {}
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding.dp),
        colors = ButtonDefaults.buttonColors(GreenMain),
        elevation = ButtonDefaults.buttonElevation(16.dp),
        onClick = {
            onClick()
        }
    ) {
        Text(
            if (textArg != null) stringResource(textResource, textArg) else
            stringResource(textResource),
            fontSize = 28.sp,
            fontFamily = FontFamily.Serif,
            color = Color.Black,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun HomeScreenText(textResource: Int, size: Int, colour: Color, weight: FontWeight) {
    Text(
        text = stringResource(textResource),
        fontFamily = FontFamily.Serif,
        color = colour,
        fontSize = size.sp,
        fontWeight = weight,
        textAlign = TextAlign.Center,
        lineHeight = 1.sp
    )
}

@Preview
@Composable
fun HomePagePreview() {
    HomeScreen(rememberNavController())
}
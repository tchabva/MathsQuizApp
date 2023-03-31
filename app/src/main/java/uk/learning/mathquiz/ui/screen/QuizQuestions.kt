@file:OptIn(ExperimentalMaterial3Api::class)

package uk.learning.mathquiz.ui.screen

import android.app.Application
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
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
import uk.learning.mathquiz.ui.quizQuestionScreen.Operator
import uk.learning.mathquiz.ui.quizQuestionScreen.QuizQuestionState
import uk.learning.mathquiz.ui.quizQuestionScreen.QuizQuestionViewModel
import uk.learning.mathquiz.ui.theme.Blue
import uk.learning.mathquiz.ui.theme.GreenMain
import uk.learning.mathquiz.ui.theme.Red


@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun QuizQuestionsScreen(
    navController: NavController,
    userName: String,
    quizQuestionViewModel: QuizQuestionViewModel,
    openDialog: MutableState<Boolean>,
    number: Int,
    operator: String

) {
    //variables
    var questionText by remember { mutableStateOf("") }
    var buttonText by remember { mutableStateOf("") }
    val answerBox = quizQuestionViewModel.userAnswer.collectAsState()
    var answerBoxFocusable by remember { mutableStateOf(true) }
    var answerBoxBackground by remember { mutableStateOf(Color.White) }
    var enabledState by remember { mutableStateOf(true) }
    var progressbarText by remember { mutableStateOf("") }
    var textColor by remember{ mutableStateOf(Color.Black) }
    //Focus Requester
    val focusRequester = remember { FocusRequester() }

    //The ProgressBar Indicator
    var progress by remember { mutableStateOf(0f) }
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    )

    //To allows to add a test Result into the DB
    val context = LocalContext.current
    val mMathsQuizDBViewModel: MathsQuizDBViewModel = viewModel(
        factory = MathsQuizDbViewModelFactory(context.applicationContext as Application)
    )

    //Takes care of what is currently in focus in the application
    val localFocusManager = LocalFocusManager.current

    //Collecting the state from the viewModel, using a more convenient variable
    val currentState = quizQuestionViewModel.state.collectAsState()

    //This will determine the colour of the Quiz depending on the operation
    if(operator == stringResource(id = R.string.division)){
        textColor = Red
    }else if (operator == stringResource(id = R.string.multiplication)){
        textColor = Blue
    }

    /*This will determine what the user will see dependent on the state of the quiz from the
    viewModel
     */
    when (val state = currentState.value) {
        //The state the quiz enters after submitting all the the last question.
        is QuizQuestionState.AnswerState -> {
            enabledState = false
            answerBoxFocusable = true
            questionText = state.currentText
            buttonText = stringResource(R.string.btn_next_question_txt)
            answerBoxBackground = getBoxColor(state.correct)
        }
        //The state of the Quiz after answering the last question
        is QuizQuestionState.FinishedState -> {
            enabledState = false
            questionText = state.currentText
            buttonText = stringResource(R.string.btn_finish_txt)
            answerBoxBackground = getBoxColor(state.correct)
        }
        //The initial state of the quiz and the state enters are every AnswerState
        is QuizQuestionState.QuestionState -> {
            enabledState = true
            questionText = state.currentText
            buttonText = stringResource(R.string.submit_btn_text)
            progressbarText = "${state.currentQuestionNumber}/${state.totalQuestions}"
            progress = state.currentQuestionNumber.toFloat() / state.totalQuestions
            answerBoxBackground = Color.White
            LaunchedEffect(Unit) {
                focusRequester.requestFocus()
            }
        }
    }


    //The UI of QuizQuestions Screen
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Question Text
        Text(
            modifier = Modifier.padding(top = 64.dp),
            text = questionText,
            fontFamily = FontFamily.Serif,
            color = textColor,
            fontSize = 56.sp,
            fontWeight = FontWeight.SemiBold
        )
        //Rowing containing the progressbar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            LinearProgressIndicator(
                progress = animatedProgress,
                modifier = Modifier.weight(.9f),
                color = textColor
            )
            //Progressbar text
            Text(
                modifier = Modifier.weight(0.15f),
                text = progressbarText,
                fontFamily = FontFamily.Serif,
                color = textColor,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.End
            )
        }
        //The Answer OutlinedTextField
        OutlinedTextField(
            value = answerBox.value,
            onValueChange = quizQuestionViewModel::setAnswer,
            placeholder = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = stringResource(R.string.answer_box_label),
                    color = Color.LightGray,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 32.sp,
                )
            },
            enabled = enabledState,
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .background(answerBoxBackground)
                .border(BorderStroke(8.dp, color = textColor))
                .focusable(answerBoxFocusable)
                .focusRequester(focusRequester),
            textStyle = TextStyle(
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 32.sp,
                textAlign = TextAlign.Center,
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = colorResource(R.color.orange),
                selectionColors = TextSelectionColors(
                    handleColor = colorResource(R.color.yellow),
                    backgroundColor = colorResource(R.color.orange),
                ),
                textColor = Color.Black,
                disabledTextColor = Color.Black
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    //Will close the keyboard if the User clicks on Done
                    localFocusManager.clearFocus()
                },
            ),
        )
        //The Submit/NextQuestion/Finish Quiz button
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            colors = ButtonDefaults.buttonColors(textColor),
            elevation = ButtonDefaults.buttonElevation(16.dp),
            onClick = {
                /*Calls the buttonPressed function from the viewModel and changes the state of the
                viewModel and thus the UI
                 */
                quizQuestionViewModel.buttonPressed(answerBox.value)

                //When statement for the when the quiz on the last question
                when (val state = currentState.value) {
                    /*When in the Finished state pressing the button will navigate to the Results
                    Screen
                     */
                    is QuizQuestionState.FinishedState -> {
                        localFocusManager.clearFocus()
                        //Pops the Backstack entries of the QuizScreen and the NumberTable.

                        /*
                        This will collect the current date from the system and then add the latest
                        test result into the DB when the user clicks on the "Finish" button
                         */
                        quizQuestionViewModel.getDate()
                        insertResultToDB(
                            number = number.toString(),
                            operator = operator,
                            userName = userName,
                            score = state.totalCorrectAnswers.toString(),
                            date = quizQuestionViewModel.currentDate,
                            mMathsQuizDBViewModel = mMathsQuizDBViewModel
                        )
                        navController.popBackStack(
                            "NumberTable/{userName}/{operator}",
                            inclusive = true
                        )
                        navController.popBackStack(
                            "QuizQuestions/{userName}/{operator}/{number}",
                            inclusive = true
                        )
                        navController.navigate("Results/${userName}/${state.totalCorrectAnswers}")
                    }
                    else -> Unit
                }
            }
        ) {
            Text(
                buttonText,
                fontSize = 32.sp,
                fontFamily = FontFamily.Serif,
                color = Color.Black,
                fontWeight = FontWeight.SemiBold
            )
        }
    }

    //This will make the openDialog value true when the user attempts to go back and close the dialog
    BackHandler(
        enabled = true,
        onBack = {
            openDialog.value = true
        }
    )
    //Will open the QuitQuizDialog if the openDialog value is true
    if (openDialog.value) {
        QuitQuizDialog(openDialog = openDialog, navController = navController)
    }
}

//Function changes the background colour of the answerBox Textfield dependent on the user answer
@Composable
private fun getBoxColor(isCorrect: Boolean): Color {
    return if (isCorrect) {
        GreenMain
    } else {
        Color.Red
    }
}

private fun insertResultToDB(
    number: String,
    operator: String,
    userName: String,
    score: String,
    date: String,
    mMathsQuizDBViewModel: MathsQuizDBViewModel
) {
    val testResult = TestResult(
        id = 0,
        number = number,
        operator = operator,
        name = userName,
        score = score,
        date = date
    )

    //Will insert a test results into the DB
    mMathsQuizDBViewModel.insertTestResult(testResult)
}

//UI Screen Preview
@RequiresApi(Build.VERSION_CODES.N)
@Preview
@Composable
fun QuizQuestionsPreview() {
    QuizQuestionsScreen(
        navController = rememberNavController(),
        userName = "Tawa",
        quizQuestionViewModel = QuizQuestionViewModel(4, Operator.DIVISION, 12),
        openDialog = remember { mutableStateOf(false) },
        number = 4,
        operator = "Division"
    )
}
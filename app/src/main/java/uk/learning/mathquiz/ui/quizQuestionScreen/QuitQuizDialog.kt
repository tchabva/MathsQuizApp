package uk.learning.mathquiz.ui.quizQuestionScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import uk.learning.mathquiz.R
import uk.learning.mathquiz.ui.theme.GreenMain
import uk.learning.mathquiz.ui.theme.Purple

@Composable
fun QuitQuizDialog(
    openDialog: MutableState<Boolean>,
    navController: NavController,
) {
    Dialog(
        onDismissRequest = { openDialog.value = false },
        //This will prevent the dialog from closing when the user clicks outside of the dialog area
        properties = DialogProperties(dismissOnClickOutside = false)
    ) {
        QuitQuizDialogUI(
            openDialog = openDialog,
            navController = navController
        )
    }
}

@Composable
fun QuitQuizDialogUI(
    modifier: Modifier = Modifier,
    openDialog: MutableState<Boolean>,
    navController: NavController,
) {

    //The background for the Dialog
    Card {
        Column(
            modifier
                .background(Purple)
                .padding(16.dp)
        ) {
            //Dialog Header Text
            Text(
                text = stringResource(R.string.dialog_text_heading),
                fontFamily = FontFamily.Serif,
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
            //Text For Dialog
            Text(
                text = stringResource(R.string.quiz_dialog_body_txt),
                fontFamily = FontFamily.Serif,
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold
            )

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                //Yes Button
                QuizDialogButton(
                    textResource = R.string.quiz_question_dialog_yes,
                    onClick = {
                        //Will Navigate back to the landing page and pop the backstack
                        navController.popBackStack("Landing/{userName}", false)
                        openDialog.value = false
                    }
                )
                //No Button
                //Will close the dialog and the user can continue with the quiz
                QuizDialogButton(
                    textResource = R.string.quiz_question_dialog_no,
                    onClick = { openDialog.value = false }
                )
            }
        }
    }
}

//The button function for the dialog
@Composable
fun QuizDialogButton(textResource: Int, onClick: () -> Unit ={}) {
    //The Button composable
    Button(
        modifier = Modifier.size(width = 120.dp, height = 50.dp),
        colors = ButtonDefaults.buttonColors(GreenMain),
        elevation = ButtonDefaults.buttonElevation(16.dp),
        onClick = { onClick() }
    ) {
        Text(
            stringResource(textResource),
            fontSize = 16.sp,
            fontFamily = FontFamily.Serif,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview
@Composable
fun DialogPreview() {
    QuitQuizDialog(
        openDialog = remember { mutableStateOf(true) },
        navController = rememberNavController(),
    )
}


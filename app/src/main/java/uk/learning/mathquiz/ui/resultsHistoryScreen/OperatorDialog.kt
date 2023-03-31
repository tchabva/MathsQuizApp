package uk.learning.mathquiz.ui.resultsHistoryScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import uk.learning.mathquiz.R
import uk.learning.mathquiz.ui.theme.Blue
import uk.learning.mathquiz.ui.theme.Purple
import uk.learning.mathquiz.ui.theme.Red


@Composable
fun OperatorDialog(
    onDismissRequest: () -> Unit,
    onAllOperatorsClick: () -> Unit,
    onMultiplicationClick: () -> Unit,
    onDivisionClick: () -> Unit
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .wrapContentSize()
                .padding(4.dp)
        ) {
            //Operator Button
            Button(
                modifier = Modifier
                    .size(110.dp),
                colors = ButtonDefaults.buttonColors(Color.White),
                shape = RoundedCornerShape(8.dp),
                elevation = ButtonDefaults.buttonElevation(16.dp),
                border = BorderStroke(4.dp, Purple),
                onClick = {
                    onAllOperatorsClick()
                }
            ) {
                Text(
                    text = stringResource(id = R.string.all_operators_filter_txt),
                    fontFamily = FontFamily.Serif,
                    color = Purple,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    lineHeight = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Multiplication Filter Option
            DialogButton(
                onClick = { onMultiplicationClick() },
                image = R.drawable.ic_multiplication_logo,
                contDes = R.string.multiplication,
                borderColor = Blue
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Division Filter Option
            DialogButton(
                onClick = { onDivisionClick() },
                image = R.drawable.ic_division_logo,
                contDes = R.string.division,
                borderColor = Red
            )
        }
    }
}

//Maybe use instead of images
@Composable
fun DialogButton(
    onClick: () -> Unit,
    image: Int,
    contDes: Int,
    borderColor: Color
){
    //Operator Button
    Button(
        modifier = Modifier
            .size(110.dp),
        colors = ButtonDefaults.buttonColors(Color.White),
        shape = RoundedCornerShape(8.dp),
        elevation = ButtonDefaults.buttonElevation(16.dp),
        border = BorderStroke(4.dp, borderColor),
        onClick = {
           onClick()
        }
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(image),
            contentDescription = stringResource(id = contDes),
            contentScale = ContentScale.Fit
        )
    }
}
@Preview(showBackground = true)
@Composable
fun OperatorDialogPreview(){
    OperatorDialog(
        onAllOperatorsClick = { /*TODO*/ },
        onMultiplicationClick = { /*TODO*/ },
        onDivisionClick = {},
        onDismissRequest = {}
    )
}
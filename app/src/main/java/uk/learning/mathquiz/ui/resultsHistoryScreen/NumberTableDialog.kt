package uk.learning.mathquiz.ui.resultsHistoryScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import uk.learning.mathquiz.ui.theme.GreenMain

@Composable
fun NumbersDialog(
    onDismissRequest: () -> Unit,
    onNumberSelected: (Int) -> Unit,
    onAllNumbersClick: () -> Unit,
){
    val numbers = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
    
    Dialog(onDismissRequest = {  onDismissRequest() }) {
        Surface(
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier.background(Color.Transparent)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(4.dp)
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                //All Numbers Button
                Button(
                    modifier = Modifier.width(124.dp),
                    colors = ButtonDefaults.buttonColors(Color.White),
                    shape = RoundedCornerShape(8.dp),
                    elevation = ButtonDefaults.buttonElevation(16.dp),
                    border = BorderStroke(4.dp, GreenMain),
                    onClick = {
                        onAllNumbersClick()
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.all_numbers_filter_txt),
                        fontFamily = FontFamily.Serif,
                        color = GreenMain,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        lineHeight = 18.sp
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val pairs = numbers.chunked(2) //Splits the list into pairs
                    for (pair in pairs){
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ){

                            for (number in pair){
                                val resId = when(number){
                                    1 -> R.drawable.ic_number_1
                                    2 -> R.drawable.ic_number_2
                                    3 -> R.drawable.ic_number_3
                                    4 -> R.drawable.ic_number_4
                                    5 -> R.drawable.ic_number_5
                                    6 -> R.drawable.ic_number_6
                                    7 -> R.drawable.ic_number_7
                                    8 -> R.drawable.ic_number_8
                                    9 -> R.drawable.ic_number_9
                                    10 -> R.drawable.ic_number_10
                                    11 -> R.drawable.ic_number_11
                                    12 -> R.drawable.ic_number_12
                                    else -> R.drawable.ic_number_1
                                }
                                Image(
                                    painter = painterResource(id = resId),
                                    contentDescription = "Number $number",
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .clickable { onNumberSelected(number) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun NumberDialogPreview(){
    NumbersDialog(onDismissRequest = {  }, onNumberSelected = {}, onAllNumbersClick = {})
}
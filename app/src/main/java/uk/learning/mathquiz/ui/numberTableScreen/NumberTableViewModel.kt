package uk.learning.mathquiz.ui.numberTableScreen

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import uk.learning.mathquiz.models.NumberModel
import uk.learning.mathquiz.models.Numbers
import uk.learning.mathquiz.ui.quizQuestionScreen.Operator

class NumberTableViewModel (
    operator: Operator,
): ViewModel(){

    val numberList: List<NumberModel> = Numbers.numberTableList

    //The State Variable
    private val _state = when(operator){
        Operator.MULTIPLICATION -> MutableStateFlow<NumberTableState>(
            NumberTableState.MultiplicationState(Operator.MULTIPLICATION)
        )
        Operator.DIVISION ->MutableStateFlow<NumberTableState>(
            NumberTableState.DivisionState(Operator.DIVISION)
        )
    }
    val state: StateFlow<NumberTableState> = _state

    fun onNumberPressed(
        navController: NavController,
        userName: String,
        operator: String,
        number: Int
    ) {

        navController.navigate("QuizQuestions/${userName}/${operator}/${number}")
    }
}
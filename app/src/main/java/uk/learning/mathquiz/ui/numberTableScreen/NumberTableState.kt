package uk.learning.mathquiz.ui.numberTableScreen

import uk.learning.mathquiz.ui.quizQuestionScreen.Operator

/*
This creates the different states that the NumberTableScreen Composable can be in. These will
then be used by the viewModel to them alter the view in the composable
 */
sealed class NumberTableState(
    val operator: Operator,

){
    //The Multiply Operator State
    class MultiplicationState(
        operator: Operator = Operator.MULTIPLICATION
    ): NumberTableState(operator)

    //The Multiply Operator State
    class DivisionState(
        operator: Operator = Operator.DIVISION
    ): NumberTableState(operator)
}

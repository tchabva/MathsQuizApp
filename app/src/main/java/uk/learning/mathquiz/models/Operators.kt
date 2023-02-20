package uk.learning.mathquiz.models

import uk.learning.mathquiz.R

object Operators {
    val operatorList = listOf<OperatorModel>(
        OperatorModel(
            id = 1,
            icon = R.drawable.ic_division_logo,
            contentDescription = R.string.multiplication
        ),
        OperatorModel(
            id = 2,
            icon = R.drawable.ic_division_logo,
            contentDescription = R.string.division
        )
    )
}
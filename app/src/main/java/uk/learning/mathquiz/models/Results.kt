package uk.learning.mathquiz.models

import uk.learning.mathquiz.data.TestResult

object Results {
    val resultsList = listOf<TestResult>(
        TestResult(
            number = "9",
            operator = "Multiplication",
            name = "Alexander",
            score = "11",
            date = "29/12/2022"
        ),
        TestResult(
            number = "7",
            operator = "Division",
            name = "Alexander",
            score = "1",
            date = "29/12/2022"
        )
    )
}
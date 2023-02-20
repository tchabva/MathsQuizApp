package uk.apps.mathsquiz.utils

import uk.apps.mathsquiz.R

object Constants {
    const val USER_NAME: String ="user_name"
    const val TOTAL_QUESTIONS: String ="total_questions"
    const val CORRECT_ANSWERS: String ="correct_answers"
    const val OPERATION: String = "operation"
    const val NUMBER: String = "number"

    const val ALL_OPERATORS: String = "All"
    const val ALL_NUMBERS: String = "All"

    const val NUMBER_ONE: String = R.string.number_1.toString()

    //The Operator Constants for the Filter
    fun operators():ArrayList<String>{
        val list = ArrayList<String>()
        list.add(R.string.multiplication.toString())
        list.add(R.string.division.toString())

        return list
    }
}
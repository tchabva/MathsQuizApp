package uk.learning.mathquiz.models

data class CurrentUser(
    val userName: String?,
    val operator: String,
    val correctAnswers: Int
)

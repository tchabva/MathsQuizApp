package uk.learning.mathquiz.models

data class NewQuestion (
    val id: Int,
    val question: String,
    val correctAnswer: Int,
    val generatedAnswer: String
)

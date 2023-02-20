package uk.apps.mathsquiz.engine.model.entities

data class NewQuestion(

    val id: Int,
    val question: String,
    val correctAnswer: Int,
    val generatedAnswer: String
)

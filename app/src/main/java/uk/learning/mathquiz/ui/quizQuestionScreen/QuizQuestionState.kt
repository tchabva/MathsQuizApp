package uk.learning.mathquiz.ui.quizQuestionScreen

/*
This creates the different states that the QuizQuestionsScreen Composable can be in and these will
then be used by the viewModel to then alter the view in the composable.
 */

sealed class QuizQuestionState(
    val currentText: String,
    val totalQuestions: Int,
    val currentQuestionNumber: Int
) {
    //The state after the user has submitted their answer to the question
    class AnswerState(
        currentText: String,
        totalQuestions: Int,
        currentQuestionNumber: Int,
        val correct: Boolean
    ) :QuizQuestionState(currentText, totalQuestions, currentQuestionNumber)

    //The state when the User is able to submit a question
    class QuestionState(
        currentText: String,
        totalQuestions: Int,
        currentQuestionNumber: Int
    ) :QuizQuestionState(currentText, totalQuestions, currentQuestionNumber)

    //The last state in the quiz which will then move to the ResultsScreen composable
    class FinishedState(
        currentText: String,
        totalQuestions: Int,
        currentQuestionNumber: Int,
        val correct: Boolean,
        val totalCorrectAnswers: Int
    ) :QuizQuestionState(currentText, totalQuestions, currentQuestionNumber)

}
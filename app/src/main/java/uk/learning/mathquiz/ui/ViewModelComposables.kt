package uk.learning.mathquiz.ui


import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.EntryPointAccessors
import uk.learning.mathquiz.MainActivity
import uk.learning.mathquiz.ui.quizQuestionScreen.Operator
import uk.learning.mathquiz.ui.quizQuestionScreen.QuizQuestionViewModel

@Composable
fun quizQuestionViewModel (
    number: Int,
    operator: Operator,
    totalQuestions: Int = 12
): QuizQuestionViewModel {
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        MainActivity.ViewModelFactoryProvider::class.java
    ).quizQuestionViewModelFactory()

    return viewModel(factory = QuizQuestionViewModel.providesFactory(factory, number, operator, totalQuestions))
}
package uk.learning.mathquiz.ui.quizQuestionScreen
//STATE BASED UI
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import uk.learning.mathquiz.models.NewQuestion
import java.util.*


class QuizQuestionViewModel @AssistedInject constructor(
    @Assisted("number") number: Int,
    @Assisted operator: Operator,
    @Assisted("totalQuestions") val totalQuestions: Int,
): ViewModel(){

    private val mQuestionList = mutableListOf<NewQuestion>()

    //This will create the mQuestionList when the viewModel is instantiated.
    init {

        //Logic for generating the questions
        when(operator){
            Operator.MULTIPLICATION -> {
                //clears the list
                mQuestionList.clear()

                //This will generate a questions list for the multiplications
                for(i in 1..totalQuestions){
                    val generatedQuestion = "$i×${number} = ?"
                    val correctAnswer = number*i
                    val generatedAnswer = "$i×${number} = $correctAnswer"

                    mQuestionList.add(NewQuestion(i,generatedQuestion,correctAnswer,generatedAnswer))
                    mQuestionList.shuffle()
                }
            }
            Operator.DIVISION -> {
                //clears the list
                mQuestionList.clear()

                //This will generations the questions for the Divisions
                for (i in 1..totalQuestions){
                    val multipliedNumber = number*i
                    val generatedQuestion = "$multipliedNumber÷$number = ?"
                    val generatedAnswer = "$multipliedNumber÷$number = $i"

                    mQuestionList.add(NewQuestion(i,generatedQuestion,i,generatedAnswer))
                    mQuestionList.shuffle()
                }
            }
        }
    }

    //The state variable
    private val _state = MutableStateFlow<QuizQuestionState>(
        QuizQuestionState.QuestionState(mQuestionList[0].question,12,1)
    )
    val state: StateFlow<QuizQuestionState> = _state

    var userScore = ""
    private var totalCorrectQuestions = 0
    private var currentQuestion = 0
    //The code for switching the state depending on the status of the quiz
    //userInput is the answer entered by the user.
    fun buttonPressed(userInput: String?){

        when(state.value){
            //When the state is in the Answer State
            is QuizQuestionState.AnswerState -> {

                currentQuestion++

                _state.value = QuizQuestionState.QuestionState(
                    currentText = mQuestionList[currentQuestion].question,
                    totalQuestions = totalQuestions,
                    currentQuestionNumber = currentQuestion+1
                )
                //This will clear the answerBox from the user's last answer
                _userAnswer.value = ""
            }
            is QuizQuestionState.FinishedState -> {

            }
            is QuizQuestionState.QuestionState -> {

                val isCorrect = mQuestionList[currentQuestion].correctAnswer == userInput?.toIntOrNull()


                if (isCorrect){
                    totalCorrectQuestions++
                }

                if (currentQuestion+1 == totalQuestions){
                    _state.value = QuizQuestionState.FinishedState(
                        currentText = mQuestionList[currentQuestion].generatedAnswer,
                        totalQuestions = totalQuestions,
                        currentQuestionNumber = currentQuestion+1,
                        correct = isCorrect,
                        totalCorrectAnswers = totalCorrectQuestions
                    )
                }else{
                    _state.value = QuizQuestionState.AnswerState(
                        currentText = mQuestionList[currentQuestion].generatedAnswer,
                        totalQuestions = totalQuestions,
                        currentQuestionNumber = currentQuestion+1,
                        correct = isCorrect
                    )
                }
            }
        }
    }


    private val _userAnswer = MutableStateFlow("")
    val userAnswer = _userAnswer.asStateFlow()

    fun setAnswer(userAnswer: String){
        _userAnswer.value = userAnswer
    }

    var currentDate = ""
    //Gets the current date
    @RequiresApi(Build.VERSION_CODES.N)
    fun getDate(){

        //Create a DateFormatter for displaying date in specified format.
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        val calendar = Calendar.getInstance() //Get the current instance from the calendar
        val dateTime = calendar.time //get the date and time of the system

        currentDate = formatter.format(dateTime).toString()
    }

    // It's a factory of this viewmodel, we need
    // to annotate this factory interface
    // with @AssistedFactory in order to
    // let Dagger-Hilt know about it
    @AssistedFactory
    interface QuizQuestionViewModelFactory {
        fun create(
            @Assisted("number") number: Int,
            operator: Operator,
            @Assisted("totalQuestions") totalQuestions: Int
        ): QuizQuestionViewModel
    }

    // Suppressing unchecked cast warning
    @Suppress("UNCHECKED_CAST")
    companion object {

        // putting this function inside
        // companion object so that we can
        //access it from outside i.e. from fragment/activity
        fun providesFactory(
            assistedFactory: QuizQuestionViewModelFactory,
            number: Int,
            operator: Operator,
            totalQuestions: Int = 12
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {

                // using our ArticlesFeedViewModelFactory's create function
                // to actually create our viewmodel instance
                return assistedFactory.create(number, operator, totalQuestions) as T
            }
        }
    }
}
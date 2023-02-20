package uk.learning.mathquiz.ui.quizQuestionScreen

import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert
import org.junit.Assert
import org.junit.Test


class QuizQuestionViewModelShould {
    //sut = system under test
    lateinit var sut : QuizQuestionViewModel
    @Test
    fun initializeInQuestionStateWithQuestionOne(){
        //Given
        sut = QuizQuestionViewModel(4, Operator.MULTIPLICATION,12)
        //When
        val state = sut.state.value
        //Then
        MatcherAssert.assertThat(state, instanceOf(QuizQuestionState.QuestionState::class.java))
        Assert.assertEquals(state.currentQuestionNumber,1)
        Assert.assertEquals(state.totalQuestions,12)
    }

    @Test
    fun shouldCountAllCorrectAnswers(){
        //Given
        sut = QuizQuestionViewModel(3,Operator.MULTIPLICATION, totalQuestions = 3)
        //When
        val state = sut.state.value
        val answer = doCalculation(Operator.MULTIPLICATION,state.currentText)
        sut.buttonPressed(answer.toString())
        val state2 = sut.state.value as QuizQuestionState.AnswerState
        //Then
        MatcherAssert.assertThat(state2, instanceOf(QuizQuestionState.AnswerState::class.java))

        Assert.assertEquals(state2.currentQuestionNumber,1)
        Assert.assertEquals(state2.totalQuestions,3)
        Assert.assertEquals(state2.correct, true)
        //When
        sut.buttonPressed(null)
        val state3 = sut.state.value
        val answer2 = doCalculation(Operator.MULTIPLICATION, state3.currentText)
        sut.buttonPressed(answer2.toString())
        val state4 = sut.state.value as QuizQuestionState.AnswerState
        //Then
        Assert.assertEquals(state4.currentQuestionNumber,2)
        Assert.assertEquals(state4.totalQuestions,3)
        Assert.assertEquals(state4.correct, true)
        //When
        sut.buttonPressed(null)
        val state5 = sut.state.value
        val answer3 = doCalculation(Operator.MULTIPLICATION, state5.currentText)
        sut.buttonPressed(answer3.toString())
        val state6 = sut.state.value as QuizQuestionState.FinishedState
        //Then
        Assert.assertEquals(state6.currentQuestionNumber,3)
        Assert.assertEquals(state6.totalQuestions,3)
        Assert.assertEquals(state6.correct, true)
        Assert.assertEquals(state6.totalCorrectAnswers, 3)
    }


    fun doCalculation(operator: Operator, question: String): Int{

        var expr = question.split("=")[0]
        expr = expr.replace(" ","")
        return when (operator){
            Operator.MULTIPLICATION -> {
                val (numberOne, number2) = expr.split("×").map{it.toInt()}
                numberOne*number2

            }
            Operator.DIVISION -> {
                val (numberOne, number2) = expr.split("/").map{it.toInt()}
                numberOne/number2
            }
        }
    }
}
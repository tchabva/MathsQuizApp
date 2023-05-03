package uk.learning.mathquiz.ui.resultsHistoryScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import uk.learning.mathquiz.data.MathsQuizDBViewModel

class HistoryViewModel(application: Application): AndroidViewModel(application) {

    private val mathsQuizVM = MathsQuizDBViewModel(application)
    private val _allTestResults = mathsQuizVM.allTestResults
    val allTestResults = _allTestResults

}
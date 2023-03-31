package uk.learning.mathquiz.ui.homePageScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import uk.learning.mathquiz.data.MathsQuizDBViewModel
import uk.learning.mathquiz.data.TestResult


class HomeViewModel(application: Application): AndroidViewModel(application) {

    private val mathsQuizVM = MathsQuizDBViewModel(application)
    private val allTestResults = mathsQuizVM.allTestResults
    var isEmpty = MutableLiveData<Boolean>()
    private var _latestTestResult = MutableLiveData<TestResult>()
    val latestTestResult:LiveData<TestResult> = _latestTestResult

    //This runs when the ViewModel is called and checks if the view model is empty or not
    init {
        allTestResults.observeForever { items ->
            when(items.isEmpty()){
                true -> isEmpty.value = true
                false -> {
                    isEmpty.value = false
                    _latestTestResult.value = items.firstOrNull()
                }
            }
        }
    }

    private val _state = when(isEmpty.value){
        true -> {
            MutableStateFlow<HomeScreenState>(HomeScreenState.EmptyDbState())
        }
        else ->{
            MutableStateFlow<HomeScreenState>(HomeScreenState.DbNotEmpty())

        }
    }


    val state: StateFlow<HomeScreenState> = _state
}
package uk.learning.mathquiz.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MathsQuizDBViewModel(application: Application) : AndroidViewModel(application) {

    private val _allTestResults: LiveData<List<TestResult>>
    private val repository: MathsQuizDBRepository

    init {
        val mathsQuizDbDAO = MathsQuizDatabase.getInstance(application).mathsQuizDAO()
        repository = MathsQuizDBRepository(mathsQuizDbDAO)
        _allTestResults = repository.allResultsList
    }

    val allTestResults: LiveData<List<TestResult>> = _allTestResults

    fun insertTestResult(testResult: TestResult){
        viewModelScope.launch{
            repository.addTestResult(testResult)
        }
    }

    fun clearTestResults(){
        viewModelScope.launch {
            repository.deleteAllResults()
        }
    }
}

class MathsQuizDbViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(MathsQuizDBViewModel::class.java)){
            return MathsQuizDBViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
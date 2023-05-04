package uk.learning.mathquiz.ui.resultsHistoryScreen


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import uk.learning.mathquiz.data.MathsQuizDBViewModel
import uk.learning.mathquiz.data.TestResult
import uk.learning.mathquiz.ui.homePageScreen.HomeScreenState

class HistoryViewModel(application: Application) : AndroidViewModel(application) {

    private val mathsQuizVM = MathsQuizDBViewModel(application)
    private val allTestResults: LiveData<List<TestResult>> = mathsQuizVM.allTestResults

    //Filtered results
    private var filteredResults: LiveData<List<TestResult>> = allTestResults

    //Filter variables
    var storedOperator: String? = null
    var storedNumber: String? = null

    private val _state = MutableStateFlow<HistoryScreenState>(HistoryScreenState.NoFilter())

    val state: StateFlow<HistoryScreenState> = _state

    init {
        //Observe the allTestResults LiveData and update the filteredResults when it changes
        allTestResults.observeForever { results ->
            filteredResults = applyFilters(results)
        }
        stateFunction()
    }

    private fun stateFunction(){
        _state.value = if(storedOperator == null && storedNumber == null){
            HistoryScreenState.NoFilter()
        } else{
            HistoryScreenState.FiltersActive(storedOperator.toString(), storedNumber.toString())
        }
    }

    /**
     * Apply filters to the results and return the filtered list as LiveData
     */
    private fun applyFilters(results: List<TestResult>): LiveData<List<TestResult>> {
        //Apply operator filter
        val filteredByOperator = if (storedOperator != null) {
            results.filter { it.operator == storedOperator }

        } else {
            results
        }

        //Apply number filter
        val filteredByNumber = if (storedNumber != null) {
            filteredByOperator.filter { it.number == storedNumber }
        } else {
            filteredByOperator
        }

        stateFunction()

        return filteredByNumber.toLiveData()
    }

    /**
     * Update the storedOperator variable and apply filters
     */
    fun filterByOperator(operator: String?) {
        storedOperator = operator
        filteredResults = applyFilters(allTestResults.value ?: emptyList())
    }

    /**
     * Update the storedNumber variable and apply filters
     */
    fun filterByNumber(number: String?) {
        storedNumber = number
        filteredResults = applyFilters(allTestResults.value ?: emptyList())
    }

    /**
     * Get the filtered results as LiveData
     */
    fun getFilteredResults(): LiveData<List<TestResult>> {
        return filteredResults
    }
}

/**
 * Extension function to convert List<T> to LiveData<List<T>>
 */
fun <T> List<T>.toLiveData(): LiveData<List<T>> {
    return MutableLiveData<List<T>>().apply {
        value = this@toLiveData
    }
}
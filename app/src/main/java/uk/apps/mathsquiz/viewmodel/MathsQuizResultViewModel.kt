package uk.apps.mathsquiz.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uk.apps.mathsquiz.engine.model.entities.TestResult
import uk.apps.mathsquiz.engine.repositories.MathsQuizRepository

abstract class MathsQuizResultViewModel: ViewModel() {
    //This gets all the data in the DB
    abstract val allResultsList: LiveData<List<TestResult>>

    //For saving data into the DB
    abstract fun saveTestResult(
        number: String,
        operation: String,
        name: String,
        score: String,
        date: String,
        id: Int)

    //Operator Filter list
    abstract fun getFilterOperatorList(filterOperator: String?)
    //Number Filter List
    abstract fun getFilterNumberList(filterNumber: String?)
}

class MathsQuizResultViewModelImpl(private val repository: MathsQuizRepository) : MathsQuizResultViewModel() {

    //Variables for the operator and number filter
    var storedOperator: String? = null
    var storedNumber: String? = null
    lateinit var resultsList: List<TestResult>
    private val _allResultsList: MutableLiveData<List<TestResult>> = MutableLiveData()
    //This allows us to view all the entries in the DB
    override val allResultsList: LiveData<List<TestResult>> get() = _allResultsList

    //This will run anytime this ViewModel is engaged
    init {
        viewModelScope.launch {
            repository.allResultsList.collect {
                resultsList = it
                emitResults()
            }
        }
    }

    //This allows us to add entries into our DB
    override fun saveTestResult( number: String,
                                 operation: String,
                                 name: String,
                                 score: String,
                                 date: String,
                                 id: Int) {
        viewModelScope.launch {
            repository.insertMathsQuizResult(
                TestResult(
                    number,
                    operation,
                    name,
                    score,
                    date,
                    id
                )
            )
        }
    }

    //This will store the Operator filter variable and run the emitResults fun
    override fun getFilterOperatorList(filterOperator: String?) {
        storedOperator = filterOperator
        emitResults()
    }
    //This will store the Number filter variable and run the emitResults fun
    override fun getFilterNumberList(filterNumber: String?) {
        storedNumber = filterNumber
        emitResults()
    }

    //This function will allow us to run the filter with either or both of the number and operator
    private fun emitResults(){
        val filteredResultsList = ArrayList(resultsList)
        val firstFiltered =
            if (storedOperator != null){
                filteredResultsList.filter { testResult -> testResult.operator == storedOperator}
            }else{
                filteredResultsList
            }
        val secondFiltered =
            if (storedNumber != null){
                firstFiltered.filter { testResult -> testResult.number == storedNumber}
            }else{
                firstFiltered
            }
        _allResultsList.postValue(secondFiltered)
    }
}
package uk.learning.mathquiz.data

import androidx.lifecycle.LiveData

//An abstraction for the DB DAO

class MathsQuizDBRepository (private val mathsQuizDatabaseDAO: MathsQuizDatabaseDAO) {

    //This will get all the the DB
    val allResultsList: LiveData<List<TestResult>> = mathsQuizDatabaseDAO.getAllResultsList()

    suspend fun addTestResult(testResult: TestResult){
        mathsQuizDatabaseDAO.insertTestResult(testResult)
    }

    suspend fun deleteAllResults(){
        mathsQuizDatabaseDAO.deleteAllTestResults()
    }
}
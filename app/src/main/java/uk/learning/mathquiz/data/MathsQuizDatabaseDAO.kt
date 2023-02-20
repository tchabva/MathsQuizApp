package uk.learning.mathquiz.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

//Where you implement the operations possible in the DB
//Suspend Keyword is for using Coroutines
@Dao
interface MathsQuizDatabaseDAO {
    //This will collect all the entries in the DB and order them by the ID
    @Query("SELECT * FROM maths_quiz_history ORDER BY id DESC")
    fun getAllResultsList(): LiveData<List<TestResult>>

    //This is the operation that will allow us to add test results to the DB
    @Insert
    suspend fun insertTestResult(testResult: TestResult)

    //Clear the DB
    @Query("DELETE FROM maths_quiz_history")
    suspend fun deleteAllTestResults()
}
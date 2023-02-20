package uk.apps.mathsquiz.engine.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import uk.apps.mathsquiz.engine.model.entities.TestResult

//Dao is where you implement the instructions
@Dao
interface MathsQuizDao {
    //Suspend is a keyword because we are working with coroutines
    @Insert //This will insert a test result into the DB`
    suspend fun insertTestResult(testResult: TestResult)

    //Will get all entries in the DB and order them by ID
    @Query("SELECT * FROM results_table ORDER BY ID DESC")
    fun getAllResultsList(): Flow<List<TestResult>>

}
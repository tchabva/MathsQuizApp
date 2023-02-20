package uk.apps.mathsquiz.engine.repositories

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import uk.apps.mathsquiz.engine.model.database.MathsQuizDao
import uk.apps.mathsquiz.engine.model.entities.TestResult

interface MathsQuizRepository {

    suspend fun insertMathsQuizResult(testResult: TestResult)

    val allResultsList: Flow<List<TestResult>>

}

class MathsQuizRepositoryImpl (private val mathsQuizDao: MathsQuizDao) : MathsQuizRepository {
    @WorkerThread //This annotation means it will be executed on the worker thread
    override suspend fun insertMathsQuizResult(testResult: TestResult){
        mathsQuizDao.insertTestResult(testResult)
    }

    //This allows us to view all of the dishes that are stored in the DB
    override val allResultsList: Flow<List<TestResult>> = mathsQuizDao.getAllResultsList()

}
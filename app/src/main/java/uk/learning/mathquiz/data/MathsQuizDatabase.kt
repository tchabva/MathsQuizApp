package uk.learning.mathquiz.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [TestResult::class],
    version = 1,
    exportSchema = false
)


abstract class MathsQuizDatabase : RoomDatabase() {

    abstract fun mathsQuizDAO(): MathsQuizDatabaseDAO

    companion object{
        private var INSTANCE: MathsQuizDatabase? = null

        fun getInstance(context: Context): MathsQuizDatabase{
            synchronized(this){
                var instance = INSTANCE

                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MathsQuizDatabase::class.java,
                        "maths_quiz_database"
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}
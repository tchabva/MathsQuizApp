package uk.apps.mathsquiz.engine.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uk.apps.mathsquiz.engine.model.entities.TestResult


@Database(entities = [TestResult::class], version = 1)
abstract class MathsQuizRoomDatabase : RoomDatabase() {

    abstract val mathsQuizDao: MathsQuizDao


    companion object{
        const val DATABASE_NAME = "maths_quiz_database"

        @Volatile
        private var INSTANCE: MathsQuizRoomDatabase? = null

        fun getDatabase(context: Context): MathsQuizRoomDatabase {
            //If the INSTANCE is not null, then return it
            //If it is null then create the DB
            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MathsQuizRoomDatabase::class.java,
                    DATABASE_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                //return instance
                instance
            }
        }
    }
}
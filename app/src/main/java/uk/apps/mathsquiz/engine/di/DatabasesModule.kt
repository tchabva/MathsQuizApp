package uk.apps.mathsquiz.engine.di

import android.content.Context
import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import uk.apps.mathsquiz.engine.model.database.MathsQuizDao
import uk.apps.mathsquiz.engine.model.database.MathsQuizRoomDatabase


val databasesModule = module {

    fun provideMathsQuizDao(database: MathsQuizRoomDatabase): MathsQuizDao{
        return database.mathsQuizDao
    }

    fun provideDatabase(context: Context): MathsQuizRoomDatabase{

        return Room.databaseBuilder(
            context,
            MathsQuizRoomDatabase::class.java,
            MathsQuizRoomDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    single{ provideDatabase(androidContext()) }

    single { provideMathsQuizDao(get()) }

}
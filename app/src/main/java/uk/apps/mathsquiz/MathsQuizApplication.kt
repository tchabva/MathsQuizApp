package uk.apps.mathsquiz

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import uk.apps.mathsquiz.engine.di.databasesModule
import uk.apps.mathsquiz.engine.di.repositoriesModule
import uk.apps.mathsquiz.engine.di.viewModelsModule

class MathsQuizApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@MathsQuizApplication)
            modules(databasesModule, repositoriesModule, viewModelsModule)
        }

    }

}
package uk.learning.mathquiz
/*
Hilt Application Class annotated with "@HiltAndroidApp". This will trigger Hilt's code generation,
including a base class for the application that will serve as the application-level dependency
container.

This generated Hilt component is attached to the Application object's lifecycle and provides
dependencies to it. It is also the parent component of the app (thus other apps can access the
dependencies that it provides)
 */

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class QuizApplication: Application() {
}
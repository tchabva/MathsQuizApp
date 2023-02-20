package uk.apps.mathsquiz.engine.di

import org.koin.dsl.module
import uk.apps.mathsquiz.engine.repositories.MathsQuizRepository
import uk.apps.mathsquiz.engine.repositories.MathsQuizRepositoryImpl


val repositoriesModule = module {
    single<MathsQuizRepository>{ MathsQuizRepositoryImpl(get()) }
}
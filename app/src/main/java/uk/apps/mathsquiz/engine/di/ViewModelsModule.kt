package uk.apps.mathsquiz.engine.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import uk.apps.mathsquiz.viewmodel.MathsQuizResultViewModel
import uk.apps.mathsquiz.viewmodel.MathsQuizResultViewModelImpl

val viewModelsModule = module {
    viewModel<MathsQuizResultViewModel>{ MathsQuizResultViewModelImpl(get()) }
}
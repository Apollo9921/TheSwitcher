package com.example.theswitcher_nunosilva.koin

import com.example.theswitcher_nunosilva.model.DivisionDatabase
import com.example.theswitcher_nunosilva.model.DivisionRepository
import com.example.theswitcher_nunosilva.ui.HomeScreenViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {
        DivisionDatabase.getDatabase(androidContext())
    }
    single {
        get<DivisionDatabase>().divisionDao()
    }
    single {
        DivisionRepository(get())
    }
    viewModel { HomeScreenViewModel(get()) }
}
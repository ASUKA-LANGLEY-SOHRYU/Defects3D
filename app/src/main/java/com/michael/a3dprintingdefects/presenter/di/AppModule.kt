package com.michael.a3dprintingdefects.presenter.di

import com.michael.a3dprintingdefects.presenter.mapper.DefectDetailsMapper
import com.michael.a3dprintingdefects.presenter.mapper.DefectDetailsToListItemMapper
import com.michael.a3dprintingdefects.presenter.mapper.DefectListItemMapper
import com.michael.a3dprintingdefects.presenter.vm.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module{
    viewModel{
        MainViewModel(
            getDefectsUseCase = get(),
            defectDetailsMapper = get(),
            defectListItemMapper = get(),
            defectDetailsToListItemMapper = get()
        )
    }
    factory { DefectListItemMapper(getPictureUrlUseCase = get()) }
    factory { DefectDetailsToListItemMapper() }
    factory { DefectDetailsMapper(getPictureUrlUseCase = get()) }
}
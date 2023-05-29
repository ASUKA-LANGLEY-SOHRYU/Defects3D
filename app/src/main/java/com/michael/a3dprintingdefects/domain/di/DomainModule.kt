package com.michael.a3dprintingdefects.domain.di

import com.michael.a3dprintingdefects.domain.usecase.GetPictureUrlUseCase
import com.michael.a3dprintingdefects.domain.usecase.GetDefectsUseCase
import org.koin.dsl.module

val domainModule = module{
    factory { GetDefectsUseCase(defectRepository = get()) }
    factory { GetPictureUrlUseCase(defectsRepository = get()) }
}
package com.michael.a3dprintingdefects.domain.usecase

import com.michael.a3dprintingdefects.domain.repository.IDefectsRepository

class GetPictureUrlUseCase(private val defectsRepository: IDefectsRepository) {
    fun execute(id: Int): String{
        return defectsRepository.getDefectPictureURL(id)
    }
}
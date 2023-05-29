package com.michael.a3dprintingdefects.presenter.mapper

import com.michael.a3dprintingdefects.app.core.mapper.IMapper
import com.michael.a3dprintingdefects.domain.model.Defect
import com.michael.a3dprintingdefects.domain.usecase.GetPictureUrlUseCase
import com.michael.a3dprintingdefects.presenter.model.DefectDetails

class DefectDetailsMapper(
    private val getPictureUrlUseCase: GetPictureUrlUseCase
): IMapper<Defect, DefectDetails> {
    override fun map(input: Defect): DefectDetails {
        return DefectDetails(
            input.id,
            input.name,
            input.description,
            "\n\n${input.reasons}\n\n${input.solutions}",
            getPictureUrlUseCase.execute(input.id)
        )
    }
}
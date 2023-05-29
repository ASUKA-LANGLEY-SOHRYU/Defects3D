package com.michael.a3dprintingdefects.presenter.mapper

import com.michael.a3dprintingdefects.app.core.mapper.IMapper
import com.michael.a3dprintingdefects.domain.model.Defect
import com.michael.a3dprintingdefects.domain.usecase.GetPictureUrlUseCase
import com.michael.a3dprintingdefects.presenter.model.DefectListItem

class DefectListItemMapper(
    private val getPictureUrlUseCase: GetPictureUrlUseCase
): IMapper<Defect, DefectListItem> {
    override fun map(input: Defect): DefectListItem {
        return DefectListItem(
            input.name,
            input.description,
            getPictureUrlUseCase.execute(input.id)
        )
    }
}
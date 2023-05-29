package com.michael.a3dprintingdefects.presenter.mapper

import com.michael.a3dprintingdefects.app.core.mapper.IMapper
import com.michael.a3dprintingdefects.presenter.model.DefectDetails
import com.michael.a3dprintingdefects.presenter.model.DefectListItem

class DefectDetailsToListItemMapper: IMapper<DefectDetails, DefectListItem> {
    override fun map(input: DefectDetails): DefectListItem {
        return DefectListItem(
            input.name,
            input.description,
            input.pictureURL
        )
    }
}
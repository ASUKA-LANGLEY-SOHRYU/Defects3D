package com.michael.a3dprintingdefects.data.mapper

import com.michael.a3dprintingdefects.app.core.mapper.IBidirectionalMapper
import com.michael.a3dprintingdefects.app.core.mapper.IMapper
import com.michael.a3dprintingdefects.data.model.DefectRepositoryItem
import com.michael.a3dprintingdefects.domain.model.Defect

class DefectMapper: IMapper<DefectRepositoryItem, Defect> {
    override fun map(input: DefectRepositoryItem): Defect {
        return Defect(
            input.id,
            input.name,
            input.description,
            input.reasons,
            input.solutions
        )
    }
}
package com.michael.a3dprintingdefects.domain.repository

import com.michael.a3dprintingdefects.domain.model.Defect
import io.reactivex.Observable

interface IDefectsRepository {

    fun getAllDefects(): Observable<Defect>

    fun getDefectPictureURL(id: Int): String

}
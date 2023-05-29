package com.michael.a3dprintingdefects.data.repository

import com.michael.a3dprintingdefects.data.datasource.api.retrofit.DefectsApiService
import com.michael.a3dprintingdefects.data.di.BASE_URL
import com.michael.a3dprintingdefects.domain.model.Defect
import com.michael.a3dprintingdefects.domain.repository.IDefectsRepository
import com.michael.a3dprintingdefects.data.mapper.DefectMapper
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable

class DefectsRepositoryImpl(
    private val apiService: DefectsApiService,
    private val mapper: DefectMapper
): IDefectsRepository {
    override fun getAllDefects(): Observable<Defect> {
        return Observable.create<Defect> { subscriber ->
            for (i in 1..29){
                apiService.getDefect(i).map {
                    mapper.map(it)
                }.subscribe({
                    subscriber.onNext(it)
                },{
                    subscriber.onComplete()
                })
            }
            subscriber.onComplete()
        }
    }

    override fun getDefectPictureURL(id: Int): String {
        return "${BASE_URL}/api/client/defects/picture/${id}"
    }
}
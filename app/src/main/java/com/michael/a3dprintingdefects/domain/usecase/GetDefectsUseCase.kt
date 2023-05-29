package com.michael.a3dprintingdefects.domain.usecase

import com.michael.a3dprintingdefects.domain.model.Defect
import com.michael.a3dprintingdefects.domain.repository.IDefectsRepository
import io.reactivex.Observable

class GetDefectsUseCase(private val defectRepository: IDefectsRepository) {
    fun execute(): Observable<Defect>{
        return defectRepository.getAllDefects().map {
            val newName = it.name.withoutPrefix(":", 10)
            val newDescription = it.description.withoutPrefix(":", 12)
            return@map it.withName(newName).withDescription(newDescription)
        }
    }

    fun String.withoutPrefix(prefixSymbol: String, maxLength: Int): String{
        val prefixPos = this.indexOf(prefixSymbol)
        if (prefixPos != -1 && prefixPos < maxLength){
            return this.drop(prefixPos + 1).strip()
        }
        return this
    }
}
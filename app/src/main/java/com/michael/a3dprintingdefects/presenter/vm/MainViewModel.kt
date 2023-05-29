package com.michael.a3dprintingdefects.presenter.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.michael.a3dprintingdefects.domain.usecase.GetDefectsUseCase
import com.michael.a3dprintingdefects.presenter.mapper.DefectDetailsMapper
import com.michael.a3dprintingdefects.presenter.mapper.DefectListItemMapper
import com.michael.a3dprintingdefects.presenter.model.DefectDetails
import com.michael.a3dprintingdefects.presenter.model.DefectListItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainViewModel(
    private val getDefectsUseCase: GetDefectsUseCase,
    private val defectDetailsMapper: DefectDetailsMapper,
    private val defectListItemMapper: DefectListItemMapper,
): ViewModel() {

    private val _defects = MutableLiveData<MutableList<DefectDetails>>(mutableListOf())
    val defects: LiveData<MutableList<DefectDetails>> = _defects

    private val _defectsListItems = MutableLiveData<MutableList<DefectListItem>>(mutableListOf())
    val defectsListItems: LiveData<MutableList<DefectListItem>> = _defectsListItems

    init {
        loadDefects()
    }

    private fun loadDefects(){
        getDefectsUseCase.execute()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe {
                _defects.value?.add(defectDetailsMapper.map(it))
                _defects.notifyObserver()

                _defectsListItems.value?.add(defectListItemMapper.map(it))
                _defectsListItems.notifyObserver()
            }
    }

    private fun <T> MutableLiveData<T>.notifyObserver() {
        this.value = this.value
    }
}
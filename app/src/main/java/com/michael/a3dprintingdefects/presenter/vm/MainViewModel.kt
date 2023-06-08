package com.michael.a3dprintingdefects.presenter.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.michael.a3dprintingdefects.domain.usecase.GetDefectsUseCase
import com.michael.a3dprintingdefects.presenter.mapper.DefectDetailsMapper
import com.michael.a3dprintingdefects.presenter.mapper.DefectDetailsToListItemMapper
import com.michael.a3dprintingdefects.presenter.mapper.DefectListItemMapper
import com.michael.a3dprintingdefects.presenter.model.DefectDetails
import com.michael.a3dprintingdefects.presenter.model.DefectListItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.Stack

class MainViewModel(
    private val getDefectsUseCase: GetDefectsUseCase,
    private val defectDetailsMapper: DefectDetailsMapper,
    private val defectListItemMapper: DefectListItemMapper,
    private val defectDetailsToListItemMapper: DefectDetailsToListItemMapper,
): ViewModel() {

    private val _defects = MutableLiveData<MutableList<DefectDetails>>(mutableListOf())
    val defects: LiveData<MutableList<DefectDetails>> = _defects

    private val _defectsListItems = MutableLiveData<MutableList<DefectListItem>>(mutableListOf())
    val defectsListItems: LiveData<MutableList<DefectListItem>> = _defectsListItems

    private val _filteredDefects = MutableLiveData<MutableList<DefectDetails>>(mutableListOf())
    val filteredDefects: LiveData<MutableList<DefectDetails>> = _filteredDefects

    private val _filteredDefectsListItems = MutableLiveData<MutableList<DefectListItem>>(mutableListOf())
    val filteredDefectsListItems: LiveData<MutableList<DefectListItem>> = _filteredDefectsListItems

    private val _searchText = MutableLiveData("")
    val searchText: LiveData<String> = _searchText

    init {
        loadDefects()
    }

    fun onSearchTextChanged(text: String){
        _searchText.value = text
        filter(text)
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

                onSearchTextChanged(_searchText.value ?: "")
            }
    }

    private fun filter(text: String){
        _filteredDefects.value = _defects.value?.filter {
            it.name.contains(text, true) ||
            it.description.contains(text, true) ||
            it.otherInformation.contains(text, true)
        }?.toMutableList()
        _filteredDefectsListItems.value = _filteredDefects.value?.map {
            defectDetailsToListItemMapper.map(it)
        }?.toMutableList()
    }

    private fun <T> MutableLiveData<T>.notifyObserver() {
        this.value = this.value
    }
}
package com.michael.a3dprintingdefects.presenter.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.michael.a3dprintingdefects.R
import com.michael.a3dprintingdefects.data.datasource.api.retrofit.DefectsApiService
import com.michael.a3dprintingdefects.data.repository.DefectsRepositoryImpl
import com.michael.a3dprintingdefects.databinding.FragmentMainBinding
import com.michael.a3dprintingdefects.domain.usecase.GetDefectsUseCase
import com.michael.a3dprintingdefects.domain.usecase.GetPictureUrlUseCase
import com.michael.a3dprintingdefects.presenter.adapter.DefectsListAdapter
import com.michael.a3dprintingdefects.presenter.adapter.SpaceItemDecorator
import com.michael.a3dprintingdefects.presenter.mapper.DefectDetailsToListItemMapper
import com.michael.a3dprintingdefects.presenter.mapper.DefectListItemMapper
import com.michael.a3dprintingdefects.presenter.model.DefectDetails
import com.michael.a3dprintingdefects.presenter.model.DefectListItem
import com.michael.a3dprintingdefects.presenter.vm.MainViewModel
import io.reactivex.Observable
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Stack

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var adapter: DefectsListAdapter
    private val vm by viewModel<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDefectsList()
        observeLiveData()
        initSearch()
    }

    private fun initSearch() {
        binding.search.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                vm.onSearchTextChanged(p0.toString())
            }

        })
    }

    private fun initDefectsList(){
        adapter = DefectsListAdapter()
        adapter.setOnButtonClickListener(object : DefectsListAdapter.OnButtonClickListener{
            override fun onButtonClick(position: Int) {
                onDefectsDetailsClick(position)
            }
        })
        binding.defectsList.adapter = adapter
        binding.defectsList.addItemDecoration(SpaceItemDecorator(40))
    }

    private fun observeLiveData(){
        vm.filteredDefectsListItems.observe(viewLifecycleOwner, Observer(::onDefectDownloaded))
    }


    private fun onDefectDownloaded(value: MutableList<DefectListItem>){
        adapter.setDefects(value)
        if (value.isNotEmpty()){
            binding.progressBar.visibility = View.GONE
            binding.defectsList.visibility = View.VISIBLE
        }
    }

    private fun onDefectsDetailsClick(id: Int){
        val name = vm.filteredDefects.value?.get(id)?.name ?: "Err"
        val pictureURL = vm.filteredDefects.value?.get(id)?.pictureURL ?: "Err"
        val description = (vm.filteredDefects.value?.get(id)?.description ?: "Err") + (vm.filteredDefects.value?.get(id)?.otherInformation ?: "")
        val bundle = bundleOf(
            DetailsFragment.NAME to name,
            DetailsFragment.PICTURE_URL to pictureURL,
            DetailsFragment.DESCRIPTION to description
        )
        findNavController().navigate(R.id.action_mainFragment_to_detailsFragment, bundle)
    }
}
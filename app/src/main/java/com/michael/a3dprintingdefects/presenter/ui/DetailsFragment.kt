package com.michael.a3dprintingdefects.presenter.ui

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.michael.a3dprintingdefects.R
import com.michael.a3dprintingdefects.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        binding.apply {
            defectName.text = requireArguments().getString(NAME, DEFAULT)
            defectDescription.text = requireArguments().getString(DESCRIPTION, DEFAULT)
            val pictureUrl = requireArguments().getString(PICTURE_URL, DEFAULT)

            Glide.with(requireContext())
                .load(pictureUrl).transform(RoundedCorners(10))
                .error(R.drawable.ic_launcher_foreground)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(defectPicture)

            back.setOnClickListener {
                findNavController().popBackStack()
            }
        }

    }

    companion object{
        const val NAME = "NAME"
        const val DESCRIPTION = "DESC"
        const val PICTURE_URL = "PICTURE"
        const val DEFAULT = "ERROR"
    }

}
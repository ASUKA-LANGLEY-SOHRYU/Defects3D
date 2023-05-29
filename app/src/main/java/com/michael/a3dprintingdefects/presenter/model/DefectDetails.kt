package com.michael.a3dprintingdefects.presenter.model

data class DefectDetails(
    val id: Int,
    val name: String,
    val description: String,
    val otherInformation: String,
    val pictureURL: String
)
package com.michael.a3dprintingdefects.data.model

data class DefectRepositoryItem(
    val id: Int,
    val name: String,
    val description: String,
    val reasons: String,
    val solutions: String,
    val picture: String
)

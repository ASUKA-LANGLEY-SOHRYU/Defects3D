package com.michael.a3dprintingdefects.domain.model

data class Defect(
    val id: Int,
    val name: String,
    val description: String,
    val reasons: String,
    val solutions: String,
){
    fun withName(name: String): Defect{
        return Defect(
            this.id,
            name,
            this.description,
            this.reasons,
            this.solutions
        )
    }

    fun withDescription(description: String): Defect{
        return Defect(
            this.id,
            this.name,
            description,
            this.reasons,
            this.solutions
        )
    }
}

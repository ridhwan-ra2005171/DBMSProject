package com.example.dbmsphase2.model

import kotlinx.serialization.Serializable

@Serializable
data class Project(
    val ProjectNo: Int,
    val ProjectName: String,
    val Description: String,
    val ProjectLoc: String,
    val ManagedBy: String
)

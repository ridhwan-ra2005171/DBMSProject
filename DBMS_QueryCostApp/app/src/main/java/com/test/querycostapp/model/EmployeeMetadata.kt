package com.test.querycostapp.model

import kotlinx.serialization.Serializable

@Serializable
data class EmployeeMetadata(
    val Attribute: String,
    val Type: String,
    val Unique: Boolean,
    val indexName: String?, //can be null
    val Selectivity: Double,
    val selectionCardinality: Double,
    val NDV: Int
)


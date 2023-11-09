package com.test.querycostapp.model

import kotlinx.serialization.Serializable

@Serializable
data class ProjectMetadata(
    val ProjAttribute: String,
    val Type: String,
    val Unique: Boolean,
    val indexName: String?, //could be null
    val Selectivity: Double,
    val selectionCardinality: Double,
    val NDV: Int
)


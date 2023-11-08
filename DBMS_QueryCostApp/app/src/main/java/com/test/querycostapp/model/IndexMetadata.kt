package com.test.querycostapp.model

import kotlinx.serialization.Serializable


@Serializable
data class IndexMetadata(
    val indexName: String,
    val Index: String,
    val indexType: String,
    val unique: Boolean,
    val level: Int,
    val selectivity: Double,
    val selectionCardinality: Double,
    val cardinality: Int,
    val firstLevelBlockCount: Int
)


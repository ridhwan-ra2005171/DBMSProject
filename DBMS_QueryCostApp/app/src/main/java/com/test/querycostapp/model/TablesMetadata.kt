package com.test.querycostapp.model

import kotlinx.serialization.Serializable

@Serializable
data class TablesMetadata(
    val tableName: String,
    val rowFormat: String,
    val columnCount: Int,
    val rowCount: Int,
    val avgRowLength: Int,
    val dataLength: Int,
    val blockCount: Int,
    val bfr: Int
)


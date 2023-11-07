package com.test.querycostapp.model

import kotlinx.serialization.Serializable

@Serializable
data class ResourceItem(
    val title: String,
    val url: String,
    val type: String
)

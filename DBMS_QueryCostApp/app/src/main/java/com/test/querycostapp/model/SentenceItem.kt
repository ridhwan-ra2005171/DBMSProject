package com.example.phase1app.model

import com.test.querycostapp.model.ResourceItem
import kotlinx.serialization.Serializable

@Serializable
data class Sentence(
    val text: String,
    val resources: List<ResourceItem>
)



package com.example.phase1app.model

import kotlinx.serialization.Serializable

@Serializable
data class Definition(
    val text: String,
    val source: String
)



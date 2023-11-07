package com.test.querycostapp.model

import com.example.phase1app.model.Definition
import com.example.phase1app.model.Sentence
import kotlinx.serialization.Serializable

@Serializable
data class WordItem(
    val text: String,
    val definitions: List<Definition>,
    val sentences: List<Sentence>
)



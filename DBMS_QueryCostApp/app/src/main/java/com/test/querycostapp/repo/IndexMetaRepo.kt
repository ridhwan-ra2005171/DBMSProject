package com.test.querycostapp.repo

import android.content.Context
import com.test.querycostapp.model.IndexMetadata
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

object IndexMetaRepo {


    var indexMetadatas = listOf<IndexMetadata>()


    fun initIndexMetadatas(context: Context): List<IndexMetadata> {
        if(indexMetadatas.isEmpty()){
            val jsonContent=
                context
                    .assets
                    .open("IndexMetadata.json")
                    .bufferedReader().use { it.readText() }
            indexMetadatas = Json{ignoreUnknownKeys = true}.decodeFromString(jsonContent)
        }
        return indexMetadatas
    }
}
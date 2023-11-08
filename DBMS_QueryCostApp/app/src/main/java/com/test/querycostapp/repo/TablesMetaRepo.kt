package com.test.querycostapp.repo

import android.content.Context
import com.test.querycostapp.model.TablesMetadata
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

object TablesMetaRepo {

    var tablesMetadatas = listOf<TablesMetadata>()

    fun initTablesMetadatas(context: Context): List<TablesMetadata> {
        if(tablesMetadatas.isEmpty()){
            val jsonContent=
                context
                    .assets
                    .open("TablesMetadata.json")
                    .bufferedReader().use { it.readText() }
            tablesMetadatas = Json{ignoreUnknownKeys = true}.decodeFromString(jsonContent)
        }
        return tablesMetadatas
    }

}
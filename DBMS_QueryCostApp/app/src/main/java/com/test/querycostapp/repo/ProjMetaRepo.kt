package com.test.querycostapp.repo

import android.content.Context
import com.test.querycostapp.model.ProjectMetadata
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

object ProjMetaRepo {

    var projectMetadatas = listOf<ProjectMetadata>()

    fun initProjectMetadatas(context: Context): List<ProjectMetadata> {
        if(projectMetadatas.isEmpty()){
            val jsonContent=
                context
                    .assets
                    .open("ProjectMetadata.json")
                    .bufferedReader().use { it.readText() }
            projectMetadatas = Json{ignoreUnknownKeys = true}.decodeFromString(jsonContent)
        }
        return projectMetadatas
    }


}
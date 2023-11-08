package com.test.querycostapp.repo

import android.content.Context
import com.test.querycostapp.model.EmployeeMetadata
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

object EmpMetaRepo {

    var employeeMetadatas = listOf<EmployeeMetadata>()


    fun initEmployeeMetadatas(context: Context): List<EmployeeMetadata> {
        if(employeeMetadatas.isEmpty()){
            val jsonContent=
                context
                    .assets
                    .open("EmployeeMetadata.json")
                    .bufferedReader().use { it.readText() }
            employeeMetadatas = (Json{ignoreUnknownKeys = true}.decodeFromString(jsonContent))
        }
        return employeeMetadatas
    }

}
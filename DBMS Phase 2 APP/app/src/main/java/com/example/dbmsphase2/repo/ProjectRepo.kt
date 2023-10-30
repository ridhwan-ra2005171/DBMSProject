package com.example.dbmsphase2.repo

import android.content.Context
import com.example.dbmsphase2.model.Employee
import com.example.dbmsphase2.model.Project
import com.example.dbmsphase2.repo.ProjectRepo.projects
import kotlinx.serialization.json.Json
import java.io.File

object ProjectRepo {

//    val employees: Any
    var projects = mutableListOf<Project>()

    //reading from json
    fun initProducts(context: Context):List<Project>{
        if(projects.isEmpty()){
            val jsonContent=
                context
                    .assets
                    .open("Projects.json")
                    .bufferedReader().use { it.readText() }
            projects = Json{ignoreUnknownKeys = true}.decodeFromString(jsonContent)
        }
        return projects
    }

    //do each of the algorithms here for select
}


fun main(args: Array<String>) {
    initKotlin()

    println(projects.toList())
}

private fun initKotlin() {
    val data = File("/app/src/androidTest/assets/Projects.json").readText()
    val jsonData = Json.decodeFromString<List<Project>>(data)
    ProjectRepo.projects.addAll(jsonData)
}
package com.test.querycostapp.repo.dataRepos

import android.content.Context
import com.test.querycostapp.repo.dataRepos.ProjectRepo.projects
import com.test.querycostapp.model.Project
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File

object ProjectRepo {

//    val employees: Any
    var projects = mutableListOf<Project>()

    //reading from json
    fun initProjects(context: Context):List<Project>{
        if(projects.isEmpty()){
            val jsonContent=
                context
                    .assets
                    .open("projects.json")
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
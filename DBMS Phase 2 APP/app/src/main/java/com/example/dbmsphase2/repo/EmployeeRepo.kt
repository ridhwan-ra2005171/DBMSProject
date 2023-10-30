package com.example.dbmsphase2.repo

import android.content.Context
import com.example.dbmsphase2.model.Employee
import kotlinx.serialization.json.Json
import java.io.File

object EmployeeRepo {

    var employees = mutableListOf<Employee>()

    //reading from json
    fun initProducts(context: Context):List<Employee>{
        if(employees.isEmpty()){
            val jsonContent=
                context
                    .assets
                    .open("Employees.json")
                    .bufferedReader().use { it.readText() }
            employees = Json{ignoreUnknownKeys = true}.decodeFromString(jsonContent)
        }
        return employees
    }

    //do each of the algorithms here

}


fun main(args: Array<String>) {
    initKotlin()

    println(EmployeeRepo.employees.toList())
}

private fun initKotlin() {
    val data = File("app/src/main/assets/Employees.json").readText()
    val jsonData = Json.decodeFromString<List<Employee>>(data)
//    ProjectRepo.employees.addAll(jsonData)
    EmployeeRepo.employees.addAll(jsonData)
}
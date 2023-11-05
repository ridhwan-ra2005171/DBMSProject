package com.example.dbmsphase2.repo

import android.content.Context
import com.example.dbmsphase2.model.Employee
import kotlinx.serialization.json.Json
import java.io.File

object EmployeeRepo {

    var employees = mutableListOf<Employee>()

    //reading from json
    fun initEmployees(context: Context):List<Employee>{
        if(employees.isEmpty()){
            val jsonContent=
                context
                    .assets
                    .open("employees.json")
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
    val data = File("employees.json").readText()
    val jsonData = Json.decodeFromString<List<Employee>>(data)
//    EmployeeRepo.employees.addAll(jsonData)
    EmployeeRepo.employees.addAll(jsonData)
}
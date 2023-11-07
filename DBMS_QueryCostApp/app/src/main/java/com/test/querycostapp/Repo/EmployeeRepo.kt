package com.test.querycostapp.Repo

import android.content.Context
import android.util.Log
import com.test.querycostapp.model.Employee
import com.test.querycostapp.model.PackageItem
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

object EmployeeRepo {
    var employees = listOf<Employee>() // Made language packages private to avoid modification


    fun initEmployees(context: Context): List<Employee> {
        if(employees.isEmpty()){
            val jsonContent=
                context
                    .assets
                    .open("employees.json")
                    .bufferedReader().use { it.readText() }
            employees = (Json{ignoreUnknownKeys = true}.decodeFromString(jsonContent))
        }

//        Log.d("EMP REPO", employees.map{it.toString()}.toString())
        return employees // return packages NOT learning package
    }

}

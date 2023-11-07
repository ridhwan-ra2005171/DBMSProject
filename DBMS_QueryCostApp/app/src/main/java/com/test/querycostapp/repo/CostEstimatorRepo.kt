package com.test.querycostapp.repo

import android.util.Log
import androidx.core.graphics.component1
import com.test.querycostapp.model.Employee
import com.test.querycostapp.model.Project

object CostEstimatorRepo{


    var writtenQuery = mutableListOf<String>() //will be passed when Done is entered
    var employees = mutableListOf<Employee>() //will be passed when Done is entered
    var projects = mutableListOf<Project>() //will be passed when Done is entered

    fun handleQuery() {
//        Log.d("Operator", "handleQuery: ${writtenQuery[0]}")
          Log.d("employees", "${employees} ")
//        Log.d("projects", "${projects} ")


        //just testing via hardcoding (press the button)
//        val (index) = binarySearchEmployee(employees, writtenQuery[7]) //SELECT * FROM Employee WHERE SSN = 1234
//        if (index != -1) {
//            val employee = employees[index]
//            Log.d("Founded", "${employee}")
//        } else {
//            Log.d("Founded", "Employee with SSN ${writtenQuery[7]} not found")
//        }

        //OPERATOR SELECT
        if (writtenQuery[0].equals("SELECT", ignoreCase = true)){
            Log.d("Operator", "handleQuery: Selectooooo")

            //TABLE EMPLOYEE
            if (writtenQuery[3].equals("EMPLOYEE", ignoreCase = true)){


                //TABLE PROJECT
            }else if (writtenQuery[3].equals("PROJECT", ignoreCase = true)){

            }


            //OPERATOR JOIN
        }else if (writtenQuery[0].equals("JOIN", ignoreCase = true)){
            Log.d("Operator", "handleQuery: Joinoooo")




        }
    }


    //primary key = SSN
//    primary key and equality operator,
//    example query SELECT * FROM Employee WHERE SSN = '1234';


//    primary key with range operator,


    //S2 binary Search //target = either ssn or projectNo (only specific single value)
        fun binarySearchEmployee(arr: MutableList<Employee>, targetSSN: String): Int {
        var left = 0
        var right = arr.size - 1

        while (left <= right) {
            val mid = left + (right - left) / 2

            if (arr[mid].SSN == targetSSN) {
                return mid // Employee found, return their index
            }

            if (arr[mid].SSN < targetSSN) {
                left = mid + 1 // Target is in the right half
            } else {
                right = mid - 1 // Target is in the left half
            }
        }

        return -1 // Employee not found
    }

}
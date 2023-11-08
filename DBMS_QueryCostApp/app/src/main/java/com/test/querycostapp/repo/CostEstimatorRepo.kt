package com.test.querycostapp.repo

import android.util.Log
import androidx.core.graphics.component1
import com.test.querycostapp.model.Employee
import com.test.querycostapp.model.Project

object CostEstimatorRepo {


    var writtenQuery = mutableListOf<String>() //will be passed when Done is entered
    var employees = mutableListOf<Employee>() //will be passed when Done is entered
    var projects = mutableListOf<Project>() //will be passed when Done is entered

    fun handleQuery() {
//        Log.d("Operator", "handleQuery: ${writtenQuery[0]}")
        Log.d("employees", "${employees} ")
//        Log.d("projects", "${projects} ")

        if (writtenQuery.size > 1) { //dont delete this if statement, or else we get error index


            Log.d("query", "${writtenQuery[7]} ")
            Log.d("query", "${employees[0].SSN} ")

            //just testing via hardcoding (press the button) [binary search working]
            Log.d("S2", "${writtenQuery[7]}")
            val (index) = binarySearchEmployee(
                employees,
                writtenQuery[7]
            ) //SELECT * FROM Employee WHERE SSN = 2345
            if (index != -1) { //if employee is found
                val employee = employees[index]
                Log.d("S2", "${employee}")
            } else {
                Log.d("S2", "Employee with SSN ${writtenQuery[7]} not found")
            }
        }


        //OPERATOR SELECT
        if (writtenQuery[0].equals("SELECT", ignoreCase = true)) {
            Log.d("Operator", "handleQuery: Selectooooo")

            //TABLE EMPLOYEE
            if (writtenQuery[3].equals("EMPLOYEE", ignoreCase = true)) {


                //TABLE PROJECT
            } else if (writtenQuery[3].equals("PROJECT", ignoreCase = true)) {

            }


            //OPERATOR JOIN
        } else if (writtenQuery[0].equals("JOIN", ignoreCase = true)) {
            Log.d("Operator", "handleQuery: Joinoooo")


        }
    }


    //primary key = SSN
//    primary key and equality operator,
//    example query SELECT * FROM Employee WHERE SSN = '1234';


//    primary key with range operator,


    //S2 binary Search //target = either ssn or projectNo (only specific single value)
    fun binarySearchEmployee(arr: MutableList<Employee>, targetSSN: String): Int {
        Log.d("S2", "binarySearchEmployee called ")
        var left = 0
        var right = arr.size - 1

        while (left <= right) {
            val mid = left + (right - left) / 2
            val midSSN = arr[mid].SSN // Add this line to log the mid SSN

            Log.d("S2", "Checking SSN at index $mid: $midSSN")

            if (midSSN == targetSSN) {
                return mid // Employee found, return their index
            }

            if (midSSN < targetSSN) {
                left = mid + 1 // Target is in the right half
            } else {
                right = mid - 1 // Target is in the left half
            }
        }

        return -1 // Employee not found
    }

    //if its on binary S2:
//    log2b + ceil[ (s/bfr)] -1 fileblocks
//            reduces to log2b if equality condition is on unique key / attribute. ( just make s=1 when its on unique)


    //for S6 there are 4 scenarios


//    J3 sortmerge:
//    Br + Bs +((joinselectivity * |R| * |S|)/bfr of RS

}
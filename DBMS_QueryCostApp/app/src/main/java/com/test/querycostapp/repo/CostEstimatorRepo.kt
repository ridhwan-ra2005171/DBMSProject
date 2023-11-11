package com.test.querycostapp.repo

import android.util.Log
import androidx.core.graphics.component1
import com.test.querycostapp.model.Employee
import com.test.querycostapp.model.EmployeeMetadata
import com.test.querycostapp.model.IndexMetadata
import com.test.querycostapp.model.Project
import com.test.querycostapp.model.ProjectMetadata
import com.test.querycostapp.model.TablesMetadata
import com.test.querycostapp.model.tableTypesClasses.TableClass

// This method searches if the value of the attribute entered exists in the table
// like if SSN = 123 is in the Employee table, it will return TRUE
fun valueExists(targetValue : String, targetAttribute: String, table: List<Any>) : Boolean {
    var exists = false
    val targetAttribute = targetAttribute.lowercase()
    var tempTable: List<String> = listOf()
    // map the table to a list of attributes depending on the attribute type
    if (table[0] is Employee) {
        table as MutableList<Employee> // Convert the list<Any> to list<Employee>
        // make temporary table to store the values
        tempTable = when (targetAttribute) {
            "ssn" -> table.map { it.SSN }
            "fname" -> table.map { it.Fname }
            "minit" -> table.map { it.Minit }
            "lname" -> table.map { it.Lname }
            "dob" -> table.map { it.DOB }
            "address" -> table.map { it.Address }
            "gender" -> table.map { it.Gender }
            "phoneno" -> table.map { it.PhoneNo }
            "hiredate" -> table.map { it.HireDate }
            "manager" -> table.map { it.Manager.toString() }
            "managerssn" -> table.map { it.ManagerSSN.toString()}
            else -> table.map { it.toString() }
        }
    } else {
        table as MutableList<Project> // Convert the list<Any> to list<Project>
        // make temporary table to store the values
        tempTable = when (targetAttribute) {
            "projectno" -> table.map { it.ProjectNo.toString() }
            "projectname" -> table.map { it.ProjectName }
            "description" -> table.map { it.Description }
            "projectloc" -> table.map { it.ProjectLoc }
            "managedby" -> table.map { it.ManagedBy }
            else -> table.map { it.toString() }
        }
    }
    // now that we have a list of values of the type of the targetAttribute
    // find the value in that list
    exists = tempTable.contains(targetValue)
    return exists
}

// This method searches if the attribute entered exists in the tables columns
// like if target Attribute is EmpNo from table Employee, it will return FALSE
fun attributeExists(targetAttribute: String, table: List<Any>) : Boolean {
    var exists = false
    val targetAttribute = targetAttribute.lowercase()
    if (table[0] is Employee) {
        table as MutableList<Employee> // Convert the list<Any> to list<Employee>
        // Convert the first employee to a string, then search through the string if the target attribute exists in that string
        exists = table[0].toString().lowercase().contains(targetAttribute)
    } else {
        table as MutableList<Project> // Convert the list<Any> to list<Project>
        // Convert the first project to a string, then search through the string if the target attribute exists in that string
        exists = table[0].toString().lowercase().contains(targetAttribute)
    }
    return exists
}

fun main(args: Array<String>) {
    val testListEmp = listOf(
        Employee(
            "123",
            "Mohammed",
            "A",
            "Ali",
            "1990-05-15",
            "123 Main St",
            "Male",
            "555-123-4567",
            "2015-03-20",
            false,
            null
        ),
        Employee(
            "234",
            "Lea",
            "R",
            "Garcia",
            "1988-09-10",
            "456 Elm St",
            "Female",
            "555-234-5678",
            "2016-02-12",
            false,
            null
        ),
        Employee(
            "345",
            "Lina",
            "S",
            "Martinez",
            "1995-11-05",
            "789 Birch St",
            "Female",
            "555-345-6789",
            "2019-06-22",
            false,
            null
        ),
        Employee(
            "456",
            "Lina",
            "S",
            "Martinez",
            "1995-11-05",
            "789 Birch St",
            "Female",
            "555-345-6789",
            "2019-06-22",
            false,
            null
        )
    )
    val testListProject = listOf<Project>(
        Project(
            101,
            "Project1",
            "Description",
            "123 Main St",
            "6789"
        ),
        Project(
            102,
            "Project2",
            "Description",
            "789 Birch St",
            "5678"
        ),
    )

    println(valueExists("123", "SsN", testListEmp ))
    println(attributeExists("PrOjEctno",testListProject))

}

enum class QUERY_TYPE {
    SELECT,
    JOIN
}

object CostEstimatorRepo {

    var queryType = QUERY_TYPE.SELECT // We will use this to determine which costs to call

    var writtenQuery = mutableListOf<String>() //will be passed when Done is entered

    var employees = mutableListOf<Employee>() //will be passed when Done is entered
    var projects = mutableListOf<Project>() //will be passed when Done is entered

    var empMetadatas = mutableListOf<EmployeeMetadata>() //will be passed when Done is entered
    var projectMetadatas = mutableListOf<ProjectMetadata>() //will be passed when Done is entered
    var tableMetadatas = mutableListOf<TablesMetadata>() //will be passed when Done is entered
    var indexMetadatas = mutableListOf<IndexMetadata>() //will be passed when Done is entered

    // From table metadata ------------------------------------------------------------------------------------
    //var empBfr = tableMetadatas.firstOrNull { it.tableName.equals("Employee", ignoreCase = true) }?.bfr
    //var projBfr = tableMetadatas.firstOrNull { it.tableName.equals("Project", ignoreCase = true) }?.bfr

    //var empBlk = tableMetadatas.firstOrNull { it.tableName.equals("Employee", ignoreCase = true) }?.blockCount
    //var projBlk = tableMetadatas.firstOrNull { it.tableName.equals("Project", ignoreCase = true) }?.blockCount

    //var empRowCount = tableMetadatas.firstOrNull { it.tableName.equals("Employee", ignoreCase = true) }?.rowCount
    //var projRowCount = tableMetadatas.firstOrNull { it.tableName.equals("Project", ignoreCase = true) }?.rowCount


    fun handleQuery() {


//        Log.d("Operator", "handleQuery: ${writtenQuery[0]}")
//        Log.d("employees", "${employees} ")
//        Log.d("projects", "${projects} ")

        // Find the index of "FROM" keyword
        var fromIndex = writtenQuery.indexOf("FROM")
        //SELECT * FROM Employee WHERE SSN = 2345
        if (writtenQuery.size > 1) { //dont delete this if statement, or else we get error index
            Log.d("query[7]", "${writtenQuery[7]} ")

            var tableName = writtenQuery[fromIndex + 1] //gets table name


            if(tableName.equals("Employee", ignoreCase = true)){ //handles employee table
                Log.d("tablename", "Table Name: $tableName")

            }else if(tableName.equals("Project", ignoreCase = true)){ //handles project table
                Log.d("tablename", "Table Name: $tableName")

            }

        }

        Log.d("metadata", "tableMetaDatas: ${tableMetadatas[0]} + ${tableMetadatas[1]}")




//        if (writtenQuery[0].equals("SELECT", ignoreCase = true)) {
//            Log.d("Operator", "handleQuery: Selectooooo")
//
//            //TABLE EMPLOYEE
//            if (writtenQuery[3].equals("EMPLOYEE", ignoreCase = true)) {
//
//
//                //TABLE PROJECT
//            } else if (writtenQuery[3].equals("PROJECT", ignoreCase = true)) {
//
//            }
//
//
//            //OPERATOR JOIN
//        } else if (writtenQuery[0].equals("JOIN", ignoreCase = true)) {
//            // join format: JOIN "table1 table2 #buffers"
//            Log.d("Operator", "handleQuery: Joinoooo")
//
//
//        }
    }


    fun handleJoin(selectedOuterTable : String, selectedInnerTable : String, selectedNoOfBuffers : Int, innerTableHasHash : Boolean): Map<String, Double> {
        // From table metadata ------------------------------------------------------------------------------------
        val empBfr = tableMetadatas.firstOrNull { it.tableName.equals("Employee", ignoreCase = true) }?.bfr
        val projBfr = tableMetadatas.firstOrNull { it.tableName.equals("Project", ignoreCase = true) }?.bfr

        val empBlk = tableMetadatas.firstOrNull { it.tableName.equals("Employee", ignoreCase = true) }?.blockCount
        val projBlk = tableMetadatas.firstOrNull { it.tableName.equals("Project", ignoreCase = true) }?.blockCount

        val empRowCount = tableMetadatas.firstOrNull { it.tableName.equals("Employee", ignoreCase = true) }?.rowCount
        val projRowCount = tableMetadatas.firstOrNull { it.tableName.equals("Project", ignoreCase = true) }?.rowCount

        Log.d("empMetadata", "empBfr, empBlk, empRow: ${empBfr} + ${empBlk} + ${empRowCount}")

        val outerTable = TableClass()
        val innerTable = TableClass()

        //initializing the outer table
        if (selectedOuterTable.equals("Employee", ignoreCase = true)){
            //outerTable = TableClass(writtenQuery[1], empBfr, empBlk, empRowCount)
            outerTable.tableName = selectedOuterTable
            outerTable.bfr = empBfr
            outerTable.blockCount = empBlk
            outerTable.rowCount = empRowCount
            Log.d("outerT", "outertable: ${outerTable.tableName} + ${outerTable.bfr} + ${outerTable.blockCount} + ${outerTable.rowCount}")
            println(outerTable)
        } else if (selectedOuterTable.equals("Project", ignoreCase = true)){
            //outerTable = TableClass(writtenQuery[1], projBfr, projBlk, projRowCount)
            outerTable.tableName = selectedOuterTable
            outerTable.bfr = projBfr
            outerTable.blockCount = projBlk
            outerTable.rowCount = projRowCount
            Log.d("outerT", "outertable: ${outerTable.tableName} + ${outerTable.bfr} + ${outerTable.blockCount} + ${outerTable.rowCount}")
            println(outerTable)
        }

        //initializing the inner table
        if (selectedInnerTable.equals("Employee", ignoreCase = true)){
            //innerTable = TableClass(writtenQuery[2], empBfr, empBlk, empRowCount)
            innerTable.tableName = selectedInnerTable
            innerTable.bfr = empBfr
            innerTable.blockCount = empBlk
            innerTable.rowCount = empRowCount
            Log.d("innerT", "innerT: ${innerTable.tableName} + ${innerTable.bfr} + ${innerTable.blockCount} + ${innerTable.rowCount}")
        } else if (selectedInnerTable.equals("Project", ignoreCase = true)){
            //innerTable = TableClass(writtenQuery[2], projBfr, projBlk, projRowCount)
            innerTable.tableName = selectedInnerTable
            innerTable.bfr = projBfr
            innerTable.blockCount = projBlk
            innerTable.rowCount = projRowCount
            Log.d("innerT", "innerT: ${innerTable.tableName} + ${innerTable.bfr} + ${innerTable.blockCount} + ${innerTable.rowCount}")
        }

        //initializing number of Buffers (for J1 algorithm)
        var noOfBuffers = selectedNoOfBuffers

        var Costs = JoinCostEstimator.getJoinCost(outerTable,innerTable, empMetadatas, projectMetadatas,
            indexMetadatas,noOfBuffers, innerTableHasHash)

        Log.d("Join Costs", "Costs: ${Costs.entries}")

        queryType = QUERY_TYPE.JOIN

        return Costs
    }

//    fun getJoinCost() : Map<String, Double> {
//        JoinCostEstimator.getJoinCost()
//    }

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




    //for S6 there are 4 scenarios


//    J3 sortmerge:
//    Br + Bs +((joinselectivity * |R| * |S|)/bfr of RS

}


//
//
//            Log.d("query", "${writtenQuery[7]} ")
//            Log.d("query", "${employees[0].SSN} ")
//
//            //just testing via hardcoding (press the button) [binary search working]
//            Log.d("S2", "${writtenQuery[7]}")
//            val (index) = binarySearchEmployee(
//                employees,
//                writtenQuery[7]
//            )
//            if (index != -1) { //if employee is found
//                val employee = employees[index]
//                Log.d("S2", "${employee}")
//            } else {
//                Log.d("S2", "Employee with SSN ${writtenQuery[7]} not found")
//            }
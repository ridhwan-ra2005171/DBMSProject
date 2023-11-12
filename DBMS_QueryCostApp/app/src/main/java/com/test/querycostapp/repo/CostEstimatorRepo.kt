package com.test.querycostapp.repo

import android.util.Log
import com.test.querycostapp.algorithms.searchAlgorithms.S1LinearSearch
import com.test.querycostapp.algorithms.searchAlgorithms.S2BinarySearchCost
import com.test.querycostapp.algorithms.searchAlgorithms.S3aPrimaryKeySelectCost
import com.test.querycostapp.algorithms.searchAlgorithms.S3bHashKeySelectCost
import com.test.querycostapp.algorithms.searchAlgorithms.S4IndexForMultipleRecords
import com.test.querycostapp.algorithms.searchAlgorithms.S6SecondaryIndexCost
import com.test.querycostapp.model.Employee
import com.test.querycostapp.model.EmployeeMetadata
import com.test.querycostapp.model.IndexMetadata
import com.test.querycostapp.model.Project
import com.test.querycostapp.model.ProjectMetadata
import com.test.querycostapp.model.TablesMetadata
import com.test.querycostapp.model.tableTypesClasses.TableClass
import kotlin.math.ceil

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


    fun handleSelection(): MutableList<Pair<String, Int>> {


//        Log.d("Operator", "handleQuery: ${writtenQuery[0]}")
//        Log.d("employees", "${employees} ")
//        Log.d("projects", "${projects} ")
        var selectcostList: MutableList<Pair<String, Int>> = mutableListOf() //to store it for displaying
        // Clear the contents of the costList
        selectcostList.clear()


        // Find the index of "FROM" keyword
        var fromIndex = writtenQuery.indexOf("FROM")
        //SELECT * FROM Employee WHERE SSN = 2345
        if (writtenQuery.size > 1) { //dont delete this if statement, or else we get error index
            Log.d("query[7]", "${writtenQuery[7]} ")

            var tableName = writtenQuery[fromIndex + 1] //gets table name


            if(tableName.equals("Employee", ignoreCase = true)){ //handles employee table
                Log.d("tablename", "Table Name: $tableName")

                var blockCount = tableMetadatas.firstOrNull { it.tableName.equals("Employee", ignoreCase = true) }?.blockCount
                var rowCount = tableMetadatas.firstOrNull { it.tableName.equals("Employee", ignoreCase = true) }?.rowCount
                var empBfr = tableMetadatas.firstOrNull { it.tableName.equals("Employee", ignoreCase = true) }?.bfr
                var x = indexMetadatas.firstOrNull { it.indexName.equals("Employee_SSN", ignoreCase = true) }?.level //returns level of index

                // Check query type for Employee table
                if (writtenQuery.contains("SSN") && writtenQuery.contains("=")) {
                    // Primary Key and Equality Operator
                    Log.d("queryType", "Primary Key and Equality Operator")
                    var primaryKey = writtenQuery[writtenQuery.indexOf("SSN")] //position of primary key
//                    var primaryKeyValue = writtenQuery[writtenQuery.indexOf("=") + 1] //value of primary key
                    var targetvalue = writtenQuery[writtenQuery.indexOf("=") + 1] //value of primary key

                    Log.d("primarykey", "primaryKey ${primaryKey} ")
                    Log.d("primarykey", "blockCount ${blockCount} ")
                    Log.d("primarykey", "primaryKeyValue ${targetvalue} ")
//                    S1a-----
                    var isFound = valueExists(targetvalue, "SSN", employees)
                    var costS1a = S1LinearSearch(notFound = !isFound, unique = true, equality = true, blockCount = blockCount!!)
//                    S2a------
                    var costS2a = S2BinarySearchCost(blockCount!!,1.0,empBfr!!) //S=1 since its unique [WORKING]

                    //S3a------
                    var cost3a = S3aPrimaryKeySelectCost(x!!) //passes index level
//                    S3b------
                    var cost3b = S3bHashKeySelectCost() //[Working]
//                    S6a------
                    var costS6a = S6SecondaryIndexCost(x!!,true,false)

                    Log.d("PKequality", "costS1a:  ${costS1a} ")
                    Log.d("PKequality", "costS2a:  ${costS2a} ")
                    Log.d("PKequality", "cost3a:  ${cost3a} ")
                    Log.d("PKequality", "cost3b:  ${cost3b} ")
                    Log.d("PKequality", "costS6a:  ${costS6a} ")

                    selectcostList.add("S1 - Linear Search on unique Select" to costS1a.toInt())
                    selectcostList.add("S2a - Binary Search on Unique Select" to costS2a.toInt())
                    selectcostList.add("S3a - PrimaryKey index Select" to cost3a)
//                    selectcostList.add("cost3b" to cost3b.toInt())
                    selectcostList.add("S6a - Secondary Index on Unique Select" to costS6a.toInt())



                    return selectcostList

                } else if (writtenQuery.contains("SSN") && (writtenQuery.contains(">=") || writtenQuery.contains("<=")|| writtenQuery.contains("<")|| writtenQuery.contains(">"))) {
                    // Range Operator using primary
                    // S1 and S4
                    Log.d("queryType", "Range Operator")

                    var targetvalue = writtenQuery[writtenQuery.indexOf("SSN") + 2] //value of primary key
                    var isFound = valueExists(targetvalue, "SSN", employees)

                    var costS1c = ceil(S1LinearSearch(notFound = !isFound, unique = true, equality = false, blockCount = blockCount!!)).toInt()

                    var costS4 = ceil(S4IndexForMultipleRecords(indexLevel = x!!, blockCount = blockCount)).toInt()

                    Log.d("PKrange", "costS1c:  ${costS1c} ")
                    Log.d("PKrange", "costS4:  ${costS4} ")

                    selectcostList.add("CS1c - Linear Search" to costS1c)
                    selectcostList.add("CS4 - Ordering-Index for multiple Records" to costS4)

                    return selectcostList

                } else if (!writtenQuery.contains("SSN") && writtenQuery.contains("=")) { //if doesnt contain primary (meaning uses non-primary key)
                    // Non-Primary Key using Equality Operator
                    Log.d("queryType", "Non-Primary Key using Equality Operator")
                    var selectedAttribute = writtenQuery[writtenQuery.indexOf("WHERE") + 1] //gets selected attribute
                    var s = empMetadatas.firstOrNull { it.EmpAttribute.equals(selectedAttribute, ignoreCase = true) }?.selectionCardinality //Selection Cardinality of attribute selected
                    var bFirst = indexMetadatas.firstOrNull { it.indexName.equals("Employee_managerSSN", ignoreCase = true) }?.firstLevelBlockCount //first level block count of index
                    Log.d("NPK", "selectedAttribute:  ${selectedAttribute}")
                    Log.d("NPK", "s: ${s}")
                    var targetvalue = writtenQuery[writtenQuery.indexOf("=") + 1] //value of primary key


                    //S1b
                    var isfound = valueExists(targetvalue, selectedAttribute, employees)
                    var costS1b = S1LinearSearch(notFound = !isfound, unique = false, equality = true, blockCount = blockCount!!)

                    //S2b
                    var costS2b = S2BinarySearchCost(blockCount!!,s!!,empBfr!!) // [WORKING]


                    //S6ab secondary index on a non-key attribute with an equality condition
                    var costS6ab = S6SecondaryIndexCost(x!!,false,false,s!!,bFirst!!)

                    Log.d("NPKequality", "costS1b:  ${costS1b} ")
                    Log.d("NPKequality", "costS2b:  ${costS2b} ")
                    Log.d("NPKequality", "costS6a Nonkey:  ${costS6ab} ")

                    selectcostList.add("S1b - Linear Search on non-primary Select" to costS1b.toInt())
                    selectcostList.add("S2b - Binary Search on non-primary Select" to costS2b.toInt())
                    selectcostList.add("S6ab - Secondary Index on non-primary Select" to costS6ab.toInt())

                    return selectcostList


                } else if ((writtenQuery.contains("HireDate") || writtenQuery.contains("DOB")) && (writtenQuery.contains(">=") || writtenQuery.contains("<=") || writtenQuery.contains("<")|| writtenQuery.contains(">"))) {
                    // Non-Primary Key using Range Operator
                    Log.d("queryType", "Non-Primary Key using Range Operator")
                    // S1 and S6b
                    var isHireDate = false // the attribute is DOB
                    if (writtenQuery.contains("HireDate")){
                        isHireDate = true // the attribute is Hire Date
                    }

                    if (isHireDate){
                        var targetvalue = writtenQuery[writtenQuery.indexOf("HireDate") + 2] //value of to be compared to
                        var isFound = valueExists(targetvalue, "HireDate", employees)

                        var costS1c = ceil(S1LinearSearch(notFound = !isFound, unique = false, equality = false, blockCount = blockCount!!)).toInt()
                        // var costS6b = S6SecondaryIndexCost(x!!,false,true,bI1 = first level block count,r = no of records)

                        selectcostList.add("CS1c - Linear Search" to costS1c)
                    }else{
                        var targetvalue = writtenQuery[writtenQuery.indexOf("DOB") + 2] //value of to be compared to
                        var isFound = valueExists(targetvalue, "DOB", employees)

                        var costS1c = ceil(S1LinearSearch(notFound = !isFound, unique = false, equality = false, blockCount = blockCount!!)).toInt()

                        selectcostList.add("CS1c - Linear Search" to costS1c)
                    }

                    return selectcostList

                }


            }else if(tableName.equals("PROJECT", ignoreCase = true)){ //handles project table
                Log.d("tablename", "Table Name: $tableName")

                var blockCount = tableMetadatas.firstOrNull { it.tableName.equals("Project", ignoreCase = true) }?.blockCount
                var rowCount = tableMetadatas.firstOrNull { it.tableName.equals("Project", ignoreCase = true) }?.rowCount
                var projBfr = tableMetadatas.firstOrNull { it.tableName.equals("PROJECT", ignoreCase = true) }?.bfr
                var x = indexMetadatas.firstOrNull { it.indexName.equals("Project_ProjectNo", ignoreCase = true) }?.level //returns level of index


                // Check query type for Project table
                if (writtenQuery.contains("ProjectNo") && writtenQuery.contains("=")) {


                    // Primary Key and Equality Operator
                    Log.d("queryType2", "Primary Key and Equality Operator")
                    var primaryKey = writtenQuery[writtenQuery.indexOf("ProjectNo")] //position of primary key
//                    var primaryKeyValue = writtenQuery[writtenQuery.indexOf("=") + 1] //value of primary key
                    var targetvalue = writtenQuery[writtenQuery.indexOf("=") + 1] //value of primary key

                    Log.d("primarykey2", "primaryKey ${primaryKey} ")
                    Log.d("primarykey2", "blockCount ${blockCount} ")

//                    Log.d("primarykey", "primaryKeyValue ${primaryKeyValue} ")
//                    S1a-----
                    var isFound = valueExists(targetvalue, "ProjectNo", projects)
                    Log.d("primarykey2", "isFound ${isFound} ")

                    var costS1a = S1LinearSearch(notFound = !isFound, unique = true, equality = true, blockCount = blockCount!!)
                    Log.d("PKequality2", "costS1a:  ${costS1a} ")

//                    S2a------
                    var costS2a = S2BinarySearchCost(blockCount!!,1.0,projBfr!!) //S=1 since its unique [WORKING]
                    Log.d("PKequality2", "costS2a:  ${costS2a} ")

                    //S3a------
                    var cost3a = S3aPrimaryKeySelectCost(x!!) //passes index level
                    Log.d("PKequality2", "cost3a:  ${cost3a} ")

//                    S3b------
                    var cost3b = S3bHashKeySelectCost() //[Working]
                    Log.d("PKequality2", "cost3b:  ${cost3b} ")

//                    S6a------
//                    var costS6a = S6SecondaryIndexCost(rowCount!!,1.0,empBfr!!)
                    var costS6a = S6SecondaryIndexCost(x!!,true,false)
                    Log.d("PKequality2", "costS6a:  ${costS6a} ")


                    selectcostList.add("S1b - Linear Search on unique Select" to costS1a.toInt())
                    selectcostList.add("S2b - Binary Search on unique Select" to costS2a.toInt())
                    selectcostList.add("S3a - PrimaryKey index Select" to cost3a.toInt())
//                    selectcostList.add("costS6ab" to cost3b.toInt())
                    selectcostList.add("S6ab - Secondary Index on unique Select" to costS6a.toInt())

                    return selectcostList




                } else if (writtenQuery.contains("ProjectNo") && (writtenQuery.contains(">=") || writtenQuery.contains("<=") || writtenQuery.contains("<")|| writtenQuery.contains(">"))) {
                    // Range Operator with primary index
                    Log.d("queryType", "Range Operator")
                    Log.d("queryType", "Range Operator")

                    var targetvalue = writtenQuery[writtenQuery.indexOf("ProjectNo") + 2] //value of primary key
                    var isFound = valueExists(targetvalue, "ProjectNo", projects)

                    var costS1c = ceil(S1LinearSearch(notFound = !isFound, unique = true, equality = false, blockCount = blockCount!!)).toInt()

                    var costS4 = ceil(S4IndexForMultipleRecords(indexLevel = x!!, blockCount = blockCount)).toInt()

                    Log.d("PKrange", "costS1c:  ${costS1c} ")
                    Log.d("PKrange", "costS4:  ${costS4} ")

                    selectcostList.add("CS1c - Linear Search" to costS1c)
                    selectcostList.add("CS4 - Ordering-Index for multiple Records" to costS4)

                    return selectcostList

                } else if (!writtenQuery.contains("ProjectNo") && writtenQuery.contains("=")) {
                    // Non-Primary Key using Equality Operator
                    Log.d("queryType", "Non-Primary Key using Equality Operator")

                    var selectedAttribute = writtenQuery[writtenQuery.indexOf("WHERE") + 1] //gets selected attribute
                    var s = projectMetadatas.firstOrNull { it.ProjAttribute.equals(selectedAttribute, ignoreCase = true) }?.selectionCardinality //Selection Cardinality of attribute selected
                    var bFirst = indexMetadatas.firstOrNull { it.indexName.equals("Project_managedBy", ignoreCase = true) }?.firstLevelBlockCount //first level block count of index
                    Log.d("NPK", "selectedAttribute:  ${selectedAttribute}")
                    Log.d("NPK", "s: ${s}")
                    var targetvalue = writtenQuery[writtenQuery.indexOf("=") + 1] //value of primary key


                    //S1b
                    var isfound = valueExists(targetvalue, selectedAttribute, projects)
                    var costS1b = S1LinearSearch(notFound = !isfound, unique = false, equality = true, blockCount = blockCount!!)
                    Log.d("NPKequality2", "costS1b:  ${costS1b} ")
                    //S2b
                    var costS2b = S2BinarySearchCost(blockCount!!,s!!,projBfr!!) // [WORKING]
                    Log.d("NPKequality2", "costS2b:  ${costS2b} ")

                    //S6ab secondary index on a non-key attribute with an equality condition
                    var costS6ab = S6SecondaryIndexCost(x!!,false,false,s!!,bFirst!!)
                    Log.d("NPKequality2", "costS6a nonkey:  ${costS6ab} ")


                    selectcostList.add("S1b - Linear Search on non-primary Select" to costS1b.toInt())
                    selectcostList.add("S2b - Binary Search on non-primary Select" to costS2b.toInt())
                    selectcostList.add("S6ab - Secondary Index on non-primary Select" to costS6ab.toInt())

                    return selectcostList

                } else if (writtenQuery.contains("ManagedBy") && (writtenQuery.contains(">=") || writtenQuery.contains("<=") || writtenQuery.contains("<")|| writtenQuery.contains(">"))) {
                    // Non-Primary Key using Range Operator
                    Log.d("queryType", "Non-Primary Key using Range Operator")

                    // S1 and S6b

                        var targetvalue = writtenQuery[writtenQuery.indexOf("ManagedBy") + 2] //value of to be compared to
                        var isFound = valueExists(targetvalue, "ManagedBy", employees)

                        var costS1c = ceil(S1LinearSearch(notFound = !isFound, unique = false, equality = false, blockCount = blockCount!!)).toInt()
                        // var costS6b = S6SecondaryIndexCost(x!!,false,true,bI1 = first level block count,r = no of records)

                        selectcostList.add("CS1c - Linear Search" to costS1c)

                    return selectcostList


                }
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
        return selectcostList
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
            innerTable.xB = find_xB(innerTable.tableName, indexMetadatas)
            Log.d("innerT", "innerT: ${innerTable.tableName} + ${innerTable.bfr} + ${innerTable.blockCount} + ${innerTable.rowCount}")
        } else if (selectedInnerTable.equals("Project", ignoreCase = true)){
            //innerTable = TableClass(writtenQuery[2], projBfr, projBlk, projRowCount)
            innerTable.tableName = selectedInnerTable
            innerTable.bfr = projBfr
            innerTable.blockCount = projBlk
            innerTable.rowCount = projRowCount
            innerTable.xB = find_xB(innerTable.tableName, indexMetadatas)
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

}

fun find_xB(tableName : String, indexMetadata: MutableList<IndexMetadata>) : Int? {
    return if (tableName.equals("Employee", ignoreCase = true)){
        indexMetadata.find { it.indexName.equals("Employee_SSN", ignoreCase = true) }?.level
    } else {
        indexMetadata.find { it.indexName.equals("Project_managedBy", ignoreCase = true) }?.level
    }
}
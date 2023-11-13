package com.test.querycostapp.algorithms

import android.util.Log
import com.test.querycostapp.algorithms.searchAlgorithms.S2BinarySearchCost
import com.test.querycostapp.algorithms.searchAlgorithms.S6SecondaryIndexCost
import com.test.querycostapp.model.ConditionClass
import com.test.querycostapp.model.EmployeeMetadata
import com.test.querycostapp.model.IndexMetadata
import com.test.querycostapp.model.ProjectMetadata
import com.test.querycostapp.model.tableTypesClasses.TableClass
import com.test.querycostapp.repo.CostEstimatorRepo
import com.test.querycostapp.repo.indexExists
import com.test.querycostapp.repo.valueExists
import kotlin.math.ceil
import kotlin.math.log2

object searchAlgorithms{


    // S1 - Linear Search (brute force) approach
    // if select is EQUALITY and KEY, cost = b/2
    // if select is EQUALITY and NON-KEY, cost = b
    // if select is RANGE, cost = b
    fun S1LinearSearch(notFound : Boolean, unique : Boolean, equality : Boolean, blockCount : Int) : Int {
        // assign blockCount to cost
        var cost = blockCount.toDouble()
        // if the select is NOT an equality, the attribute is not unique i.e. non key, or the record doesn't exist; return the number of blocks i.e. the cost
        return kotlin.math.ceil( if (!equality or !unique or notFound) {
             cost
        } else {
            // if the select is an equality, the attribute is unique and the record exists; return HALF of the number of blocks i.e. cost/2
             cost / 2
        }).toInt()
    }




    //S2 - Binary Search
    //if its on binary S2:
//    log2b + ceil[ (s/bfr)] -1 fileblocks
//            reduces to log2b if equality condition is on unique key / attribute. ( just make s=1 when its on unique)

    fun S2BinarySearchCost(b: Int, s: Double, bfr: Int): Int {
        return kotlin.math.ceil(if (s == 1.0) {
            // When s is 1 (equality on a unique key attribute)
            log2(b.toDouble())
        } else {
            // For other scenarios if not on unique key
            log2(b.toDouble()) + ceil(s / bfr) - 1
        }).toInt()
    }

    //S3a CS3a = x + 1.0, x is levels of index
    fun S3aPrimaryKeySelectCost(x: Int): Int{
        return x + 1
    }

    fun S3bHashKeySelectCost(): Int {
        return kotlin.math.ceil(1.0).toInt()
    }

    // S4—Using an ordering index to retrieve multiple records.
    // This algorithm is only applicable to a KEY attribute with INDEX and for RANGE queries
    // ------------------------ From the Book ------------------------------------------------------
    // If the comparison condition is >, >=, <, or <= on a key field with an ordering index, roughly
    // half the file records will satisfy the condition. This gives a cost function of
    // CS4 = x + (b/2). This is a very rough estimate, and although it may be correct
    // on the average, it may be inaccurate in individual cases. A more accurate
    // estimate is possible if the distribution of records is stored in a histogram
    //----------------------------------------------------------------------------------------------
    fun S4IndexForMultipleRecords(indexLevel: Int, blockCount: Int): Int {
        return kotlin.math.ceil(indexLevel.toDouble() + ((blockCount.toDouble()) / 2)).toInt()
    }


    //S6- Using a secondary (B+-tree) index
    //S6a -> For a secondary index on a key (unique) attribute, with an equality condition, cost is x + 1
    //for secondary on nonkey attribute, cost is CS6a = x + 1 + s [s is selection cardinality]
    //S6b -> for range queries The cost estimate for this case, approximately, is CS6b = x + (bI1/2) + (r/2) [bI1 is the number of blocks in the index, r is the number of records in the index]

    fun S6SecondaryIndexCost(x: Int, isUniqueKeyAttribute: Boolean, isRangeQuery: Boolean, s: Double = 0.0, bI1: Int = 0, r: Int = 0): Int {
        return kotlin.math.ceil(if (!isRangeQuery) {
            if (isUniqueKeyAttribute) {
                // S6a -> For a secondary index on a unique key attribute with an equality condition
                x + 1.0
            } else {

                // S6a -> For a secondary index on a non-key attribute with an equality condition
                x + 1.0 + s
            }
        } else { //if its on range queries

            // S6b -> For a secondary index on a unique key attribute with a range query
            x + (bI1 / 2.0) + (r / 2.0)
        }).toInt()
    }


    //S7 Conjunctive (AND)
    fun S7ConjunctiveSelectCost(conditionList:MutableList<ConditionClass>): MutableList<Pair<String, Int>> {
//        val operations = selectedAttrList.zip(operatorList)
        var selectcostList: MutableList<Pair<String, Int>> = mutableListOf() //to store it for displaying

        var ExistIndex = false
        conditionList.forEach{
            println(it.attributeName) //selected attr
            println(it.operator) //operator




            var selectedAttribute = it.attributeName

            ExistIndex = indexExists(it.attributeName)
            Log.d("S7Index", "S7Index: ${ExistIndex}")

            if (ExistIndex) {

                if (listOf("projectno", "projectname", "description", "projectloc", "managedby").indexOf(element = it.attributeName.lowercase()) >= 0) {
                    //get needed arguments for the cost in project
                    var blockCount = CostEstimatorRepo.tableMetadatas.firstOrNull {
                        it.tableName.equals(
                            "Project",
                            ignoreCase = true
                        )
                    }?.blockCount
                    var rowCount = CostEstimatorRepo.tableMetadatas.firstOrNull {
                        it.tableName.equals(
                            "Project",
                            ignoreCase = true
                        )
                    }?.rowCount
                    var projBfr = CostEstimatorRepo.tableMetadatas.firstOrNull {
                        it.tableName.equals(
                            "PROJECT",
                            ignoreCase = true
                        )
                    }?.bfr
                    var x = CostEstimatorRepo.indexMetadatas.firstOrNull {
                        it.indexName.equals(
                            "Project_ProjectNo",
                            ignoreCase = true
                        )
                    }?.level //returns level of index





                    if (it.attributeName.equals("projectno", ignoreCase = true)) { //if primary

                        if (it.operator.equals("=")){ //handles equality primary

                            var primaryKey =
                                CostEstimatorRepo.writtenQuery[CostEstimatorRepo.writtenQuery.indexOf("ProjectNo")] //position of primary key



//                    S2a------
                            var costS2a = S2BinarySearchCost(
                                blockCount!!,
                                1.0,
                                projBfr!!
                            ) //S=1 since its unique [WORKING]
                            Log.d("PKequality2", "costS2a:  ${costS2a} ")

                            //S3a------
                            var cost3a = S3aPrimaryKeySelectCost(x!!) //passes index level
                            Log.d("PKequality2", "cost3a:  ${cost3a} ")

//                    S3b------
                            var cost3b = S3bHashKeySelectCost() //[Working]
                            Log.d("PKequality2", "cost3b:  ${cost3b} ")

//                    S6a------
//                    var costS6a = S6SecondaryIndexCost(rowCount!!,1.0,empBfr!!)
                            var costS6a = S6SecondaryIndexCost(x!!, true, false)
                            Log.d("PKequality2", "costS6a:  ${costS6a} ")


                            selectcostList.add("S2b - Binary Search on unique Select" to costS2a)
                            selectcostList.add("S3a - PrimaryKey index Select" to cost3a)
//                    selectcostList.add("costS6ab" to cost3b )
                            selectcostList.add("S6ab - Secondary Index on unique Select" to costS6a)

                            return selectcostList


                        }else if (it.operator.equals(">") || it.operator.equals("<") || it.operator.equals(">=") || it.operator.equals("<=")){ //handles range


                            var costS4 =
                                S4IndexForMultipleRecords(indexLevel = x!!, blockCount = blockCount!!)

                            Log.d("PKrange", "costS4:  ${costS4} ")

                            selectcostList.add("CS4 - Ordering-Index for multiple Records" to costS4)

                            return selectcostList

                        }

                    }else{ //if non primary

                        if (it.operator.equals("=")){ //handles equality

                            var s = CostEstimatorRepo.projectMetadatas.firstOrNull {
                                it.ProjAttribute.equals(
                                    selectedAttribute
                                )
                            }?.selectionCardinality //Selection Cardinality of attribute selected
                            var bFirst = CostEstimatorRepo.indexMetadatas.firstOrNull {
                                it.indexName.equals(
                                    "Project_managedBy",
                                    ignoreCase = true
                                )
                            }?.firstLevelBlockCount //first level block count of index
                            Log.d("NPK", "s: ${s}")
                            var targetvalue =
                                CostEstimatorRepo.writtenQuery[CostEstimatorRepo.writtenQuery.indexOf("=") + 1] //value of primary key




                            //S2b
                            var costS2b = S2BinarySearchCost(blockCount!!, s!!, projBfr!!) // [WORKING]
                            Log.d("NPKequality2", "costS2b:  ${costS2b} ")

                            //S6ab secondary index on a non-key attribute with an equality condition
                            var costS6ab = S6SecondaryIndexCost(x!!, false, false, s!!, bFirst!!)
                            Log.d("NPKequality2", "costS6a nonkey:  ${costS6ab} ")


                            selectcostList.add("S2b - Binary Search on non-primary Select" to costS2b)
                            selectcostList.add("S6ab - Secondary Index on non-primary Select" to costS6ab)
                            Log.d("S7ConjunctiveSelectCost", "S7ConjunctiveSelectCost: ${selectcostList}")
                            return selectcostList

                        }else if (!it.operator.equals("=")){ //handles range

                            var bFirst = CostEstimatorRepo.indexMetadatas.firstOrNull {
                                it.indexName.equals(
                                    "Project_managedBy",
                                    ignoreCase = true
                                )
                            }?.firstLevelBlockCount //first level block count of index

                            // S1 and S6b

                            var targetvalue =
                                CostEstimatorRepo.writtenQuery[CostEstimatorRepo.writtenQuery.indexOf("ManagedBy") + 2] //value of to be compared to
                            var isFound = valueExists(targetvalue, "ManagedBy",
                                CostEstimatorRepo.employees
                            )

                            var costS6b =
                                S6SecondaryIndexCost(x!!, false, true, bI1 = bFirst!!, r = rowCount!!)

                            selectcostList.add("CS6b - Secondary Index on Non-Primary Range" to costS6b)

                            return selectcostList

                        }

                    }

                } else { //handle employee table
                    //get needed arguments for the cost in employee
                    var blockCount = CostEstimatorRepo.tableMetadatas.firstOrNull {
                        it.tableName.equals(
                            "Employee",
                            ignoreCase = true
                        )
                    }?.blockCount
                    var rowCount = CostEstimatorRepo.tableMetadatas.firstOrNull {
                        it.tableName.equals(
                            "Employee",
                            ignoreCase = true
                        )
                    }?.rowCount
                    var empBfr = CostEstimatorRepo.tableMetadatas.firstOrNull {
                        it.tableName.equals(
                            "Employee",
                            ignoreCase = true
                        )
                    }?.bfr
                    var x = CostEstimatorRepo.indexMetadatas.firstOrNull {
                        it.indexName.equals(
                            "Employee_SSN",
                            ignoreCase = true
                        )
                    }?.level //returns level of index


                    if (it.attributeName.equals("ssn", ignoreCase = true)) { //if primary



                        if (it.operator.equals("=")) { //handles equality primary

                            var primaryKey =
                                CostEstimatorRepo.writtenQuery[CostEstimatorRepo.writtenQuery.indexOf("SSN")] //position of primary key
//                    var primaryKeyValue = writtenQuery[writtenQuery.indexOf("=") + 1] //value of primary key
                            var targetvalue =
                                CostEstimatorRepo.writtenQuery[CostEstimatorRepo.writtenQuery.indexOf("=") + 1] //value of primary key

                            Log.d("primarykey", "primaryKey ${primaryKey} ")
                            Log.d("primarykey", "blockCount ${blockCount} ")
                            Log.d("primarykey", "primaryKeyValue ${targetvalue} ")


//                    S2a------
                            var costS2a = S2BinarySearchCost(
                                blockCount!!,
                                1.0,
                                empBfr!!
                            ) //S=1 since its unique [WORKING]

                            //S3a------
                            var cost3a = S3aPrimaryKeySelectCost(x!!) //passes index level
//                    S3b------
                            var cost3b = S3bHashKeySelectCost() //[Working]
//                    S6a------
                            var costS6a = S6SecondaryIndexCost(x!!, true, false)

                            Log.d("PKequality", "costS2a:  ${costS2a} ")
                            Log.d("PKequality", "cost3a:  ${cost3a} ")
                            Log.d("PKequality", "cost3b:  ${cost3b} ")
                            Log.d("PKequality", "costS6a:  ${costS6a} ")

                            selectcostList.add("S2a - Binary Search on Unique Select" to costS2a)
                            selectcostList.add("S3a - PrimaryKey index Select" to cost3a)
//                    selectcostList.add("cost3b" to cost3b )
                            selectcostList.add("S6a - Secondary Index on Unique Select" to costS6a)



                            return selectcostList


                        }else{
                            //handles range primary
// Range Operator using primary
                            // and S4
                            Log.d("queryType", "Range Operator")

                            var targetvalue =
                                CostEstimatorRepo.writtenQuery[CostEstimatorRepo.writtenQuery.indexOf("SSN") + 2] //value of primary key



                            var costS4 =
                                S4IndexForMultipleRecords(indexLevel = x!!, blockCount = blockCount!!)

                            Log.d("PKrange", "costS4:  ${costS4} ")

                            selectcostList.add("CS4 - Ordering-Index for multiple Records" to costS4)

                            return selectcostList


                        }
                    }else { //if non primary

                        if (it.operator.equals("=")){
                            //handles equality non PK

                            var s = CostEstimatorRepo.empMetadatas.firstOrNull {
                                it.EmpAttribute.equals(
                                    selectedAttribute

                                )
                            }?.selectionCardinality //Selection Cardinality of attribute selected
                            var bFirst = CostEstimatorRepo.indexMetadatas.firstOrNull {
                                it.indexName.equals(
                                    "Employee_managerSSN",
                                    ignoreCase = true
                                )
                            }?.firstLevelBlockCount //first level block count of index
                            Log.d("NPK", "selectedAttribute:  ${selectedAttribute}")
                            Log.d("NPK", "s: ${s}")

                            //S2b
                            var costS2b = S2BinarySearchCost(blockCount!!, s!!, empBfr!!) // [WORKING]


                            //S6ab secondary index on a non-key attribute with an equality condition
                            var costS6ab = S6SecondaryIndexCost(x!!, false, false, s!!, bFirst!!)

                            Log.d("NPKequality", "costS2b:  ${costS2b} ")
                            Log.d("NPKequality", "costS6a Nonkey:  ${costS6ab} ")

                            selectcostList.add("S2b - Binary Search on non-primary Select" to costS2b)
                            selectcostList.add("S6ab - Secondary Index on non-primary Select" to costS6ab)

                            return selectcostList


                        }else{
                            //handles range non PK
                            var costNA = 0
                            selectcostList.add("Not Applicable" to costNA)

                            return selectcostList



                        }

                    }



                }


                return selectcostList
            }else{
                //brute force S1 used
//                S1LinearSearch()
               //check if from employee or project
                if (listOf("projectno", "projectname", "description", "projectloc", "managedby").indexOf(element = it.attributeName.lowercase()) >= 0) {
                    //get needed arguments for the cost in project
                    var blockCount = CostEstimatorRepo.tableMetadatas.firstOrNull {
                        it.tableName.equals(
                            "Project",
                            ignoreCase = true
                        )
                    }?.blockCount
                    var rowCount = CostEstimatorRepo.tableMetadatas.firstOrNull {
                        it.tableName.equals(
                            "Project",
                            ignoreCase = true
                        )
                    }?.rowCount
                    var projBfr = CostEstimatorRepo.tableMetadatas.firstOrNull {
                        it.tableName.equals(
                            "PROJECT",
                            ignoreCase = true
                        )
                    }?.bfr
                    var x = CostEstimatorRepo.indexMetadatas.firstOrNull {
                        it.indexName.equals(
                            "Project_ProjectNo",
                            ignoreCase = true
                        )
                    }?.level //returns level of index

                    if (it.attributeName.equals("projectno", ignoreCase = true)) { //if primary
                        if (it.operator.equals("=")) { //handles equality primary
                            var primaryKey =
                                CostEstimatorRepo.writtenQuery[CostEstimatorRepo.writtenQuery.indexOf("ProjectNo")] //position of primary key
//                    var primaryKeyValue = writtenQuery[writtenQuery.indexOf("=") + 1] //value of primary key
                            var targetvalue =
                                CostEstimatorRepo.writtenQuery[CostEstimatorRepo.writtenQuery.indexOf("=") + 1] //value of primary key

                            Log.d("primarykey2", "primaryKey ${primaryKey} ")
                            Log.d("primarykey2", "blockCount ${blockCount} ")

//                    Log.d("primarykey", "primaryKeyValue ${primaryKeyValue} ")
//                    S1a-----
                            var isFound = valueExists(targetvalue, "ProjectNo",
                                CostEstimatorRepo.projects
                            )
                            Log.d("primarykey2", "isFound ${isFound} ")

                            var costS1a = S1LinearSearch(
                                notFound = !isFound,
                                unique = true,
                                equality = true,
                                blockCount = blockCount!!
                            )
                            selectcostList.add("S1a - Linear Search on unique Select" to costS1a)
                            return selectcostList

                        }else{ //handles range primary

                            var costS1c = S1LinearSearch(
                                notFound = false,
                                unique = true,
                                equality = false,
                                blockCount = blockCount!!
                            )
                            selectcostList.add("CS1c - Linear Search" to costS1c)
                            return selectcostList

                        }

                    }else{ //if non primary

                        if (it.operator.equals("=")) { //handles equality primary
                            var costS1b = S1LinearSearch(
                                notFound = false,
                                unique = false,
                                equality = true,
                                blockCount = blockCount!!
                            )
                            selectcostList.add("S1b - Linear Search on non-primary Select" to costS1b)
                            return selectcostList

                        }else{ //handles range primary

                            var costS1c = S1LinearSearch(
                                notFound = false,
                                unique = false,
                                equality = false,
                                blockCount = blockCount!!
                            )
                            selectcostList.add("CS1c - Linear Search" to costS1c)
                            return selectcostList

                        }

                    }





                } else { //handle employee table
                    //attribute for employees
                    var blockCount = CostEstimatorRepo.tableMetadatas.firstOrNull {
                        it.tableName.equals(
                            "Employee",
                            ignoreCase = true
                        )
                    }?.blockCount
                    var rowCount = CostEstimatorRepo.tableMetadatas.firstOrNull {
                        it.tableName.equals(
                            "Employee",
                            ignoreCase = true
                        )
                    }?.rowCount
                    var empBfr = CostEstimatorRepo.tableMetadatas.firstOrNull {
                        it.tableName.equals(
                            "Employee",
                            ignoreCase = true
                        )
                    }?.bfr
                    var x = CostEstimatorRepo.indexMetadatas.firstOrNull {
                        it.indexName.equals(
                            "Employee_SSN",
                            ignoreCase = true
                        )
                    }?.level //returns level of index

                    if (it.attributeName.equals("SSN", ignoreCase = true)) { //if primary
                        if (it.operator.equals("=")) { //handles equality primary

                            var costS1a = S1LinearSearch(
                                notFound = false,
                                unique = true,
                                equality = true,
                                blockCount = blockCount!!
                            )
                            selectcostList.add("S1a - Linear Search on unique Select" to costS1a)
                            return selectcostList


                        }else{ //handles range primary

                            var costS1c = S1LinearSearch(
                                notFound = false,
                                unique = true,
                                equality = false,
                                blockCount = blockCount!!
                            )
                            selectcostList.add("CS1c - Linear Search" to costS1c)
                            return selectcostList

                        }

                    }else{ //if non primary

                        if (it.operator.equals("=")) { //handles equality primary

                            var costS1b = S1LinearSearch(
                                notFound = false,
                                unique = false,
                                equality = true,
                                blockCount = blockCount!!
                            )
                            selectcostList.add("S1b - Linear Search on non-primary Select" to costS1b)
                            return selectcostList


                        }else{ //handles range primary

                            var costS1c = S1LinearSearch(
                                notFound = false,
                                unique = false,
                                equality = false,
                                blockCount = blockCount!!
                            )
                            selectcostList.add("CS1c - Linear Search" to costS1c)
                            return selectcostList

                        }

                    }

                }

            }


        }

        return selectcostList
    }

    // COST OF OR QUERY
    // Disjunction SELECT
    //return int and double from the function
    fun S7DisjunctionSelectCost( conditionList : List<ConditionClass>, targetTable: TableClass, indexMetadata: List<IndexMetadata>, empMetadatas: List<EmployeeMetadata>, projectMetadatas: List<ProjectMetadata>) : Map<String, Int> {

        var b : Int;
        var bfr : Int;
        var r : Int;
        if (targetTable.tableName.equals("Employee", ignoreCase = true)) {
            empMetadatas
            b = targetTable.blockCount!!
            bfr = targetTable.bfr!!
            r = targetTable.rowCount!!
        } else {
            projectMetadatas
            b = targetTable.blockCount!!
            bfr = targetTable.bfr!!
            r = targetTable.rowCount!!
        }

        val allHaveIndex = conditionList.all { indexExists(it.attributeName) }

        var costPerAttr : MutableMap<String, Int> = emptyMap<String, Int>().toMutableMap<String, Int>()

        // If any attribute in the query has NO index, return the brute force cost
        if (!allHaveIndex) {
            return mapOf<String, Int>("S1 - Linear Search" to S1LinearSearch(true, false, false, if (targetTable.tableName.equals("Employee", ignoreCase = true)) 30 else 5))
        }
        // Equality:
        // S1(key, nonkey, S2(key, nonkey), S3a(key), S6a (key, nonkey)
        // Range
        // If all the attributes involved in the query have an index
        // Sum up the cost of those index searches
        // IF ALL ATTRIBUTES HAVE INDEX
        for (condition in conditionList) {
            // Get the required values from the attribute metadata
            var s : Double?

            if (targetTable.tableName.equals("Employee", ignoreCase = true)) {
                s = empMetadatas.firstOrNull { it.EmpAttribute.equals(condition.attributeName, ignoreCase = true) }?.selectionCardinality
            } else {
                s = projectMetadatas.firstOrNull { it.ProjAttribute.equals(condition.attributeName, ignoreCase = true) }?.selectionCardinality
            }

            val  x = indexMetadata.firstOrNull {it.indexName.equals("${targetTable.tableName}_${condition.attributeName}", ignoreCase = true)}?.level!!
            val bl1 = indexMetadata.firstOrNull {it.indexName.equals("${targetTable.tableName}_${condition.attributeName}", ignoreCase = true)}?.firstLevelBlockCount!!


            // Check if condition is equality first
            if (condition.operator == "=") {
                // Check if the attribute is unique
                // Unique
                if (listOf("ssn", "projectno").indexOf(condition.attributeName) >= 0) {
                    // find the costs of each equality search
                    val keyCosts: MutableMap<String, Int> = mutableMapOf()
//                    keyCosts.put("${condition.attributeName}: S1 - Linear Search", searchAlgorithms.S1LinearSearch())
                    keyCosts.put(
                        "${condition.attributeName}: S2 - Binary Search",
                        searchAlgorithms.S2BinarySearchCost(b = b, s = s!!, bfr = bfr)
                    )
                    keyCosts.put(
                        "${condition.attributeName}: S3a - Primary Key Select",
                        searchAlgorithms.S3aPrimaryKeySelectCost(x)
                    )
                    keyCosts.put(
                        "${condition.attributeName}: S6a - Secondary Index",
                        searchAlgorithms.S6SecondaryIndexCost(
                            x = x,
                            isUniqueKeyAttribute = true,
                            isRangeQuery = false,
                            s = s,
                            bI1 = bl1,
                            r = r
                        )
                    )
                    // add the least cost for this attribute in the costPerAttr map
                    costPerAttr.put(keyCosts.minBy { it.value }.key, keyCosts.minBy { it.value }.value)

                } else { // Non-Unique
                    val nonKeyCosts: MutableMap<String, Int> = mutableMapOf()
//                nonKeyCosts.put("${condition.attributeName}: S1 - Linear Search", searchAlgorithms.S1LinearSearch())
                    nonKeyCosts.put(
                        "${condition.attributeName}: S2 - Binary Search",
                        searchAlgorithms.S2BinarySearchCost(b= b , s = s!!, bfr = bfr)
                    )
                    nonKeyCosts.put(
                        "${condition.attributeName}: S6a - Secondary Index",
                        searchAlgorithms.S6SecondaryIndexCost(x = x, isUniqueKeyAttribute = false, isRangeQuery = false, s = s, bI1 = bl1, r = r)
                    )
                    // add the least cost for this attribute in the costPerAttr map
                    costPerAttr.put(nonKeyCosts.minBy { it.value }.key, nonKeyCosts.minBy { it.value }.value)
                }
                // Range
                // S1(key, nonkey), S4 (key), S6b (key, nonkey)
            } else { // If condition is RANGE
                // Check if the attribute is unique
                if (listOf("ssn", "projectno").indexOf(condition.attributeName) >= 0) {
                    val keyCosts: MutableMap<String, Int> = mutableMapOf()
//                keyCosts.put("${condition.attributeName}: S1 - Linear Search", searchAlgorithms.S1LinearSearch())
                    keyCosts.put(
                        "${condition.attributeName}: S4 - Range Search",
                        searchAlgorithms.S4IndexForMultipleRecords(indexLevel = x, blockCount = b)
                    )
                    keyCosts.put(
                        "${condition.attributeName}: S6b - Secondary Index",
                        searchAlgorithms.S6SecondaryIndexCost(x =x, isUniqueKeyAttribute = true, isRangeQuery = true, s = s!!, bI1 = bl1, r = r)
                    )
                    // add the least cost for this attribute in the costPerAttr map
                    costPerAttr.put("${keyCosts.minBy { it.value }!!.key}", keyCosts.minBy { it.value }!!.value)

                } else { // Non-Unique S1, S6b
                    val nonKeyCosts: MutableMap<String, Int> = mutableMapOf()
//                nonKeyCosts.put("${condition.attributeName}: S1 - Linear Search", searchAlgorithms.S1LinearSearch())
                    nonKeyCosts.put(
                        "${condition.attributeName}: S6b - Secondary Index",
                        searchAlgorithms.S6SecondaryIndexCost(x =x, isUniqueKeyAttribute = false, isRangeQuery = true, s = s!!, bI1 = bl1, r = r)
                    )
                    // add the least cost for this attribute in the costPerAttr map
                    costPerAttr.put("${nonKeyCosts.minBy { it.value }!!.key}", nonKeyCosts.minBy { it.value }!!.value)

                }

            }
        }

        return costPerAttr
    }



}

// Testing the Search Algorithms
fun main(args: Array<String>) {
    // S1 - Linear Search ---------------------------
    // If key (unique) AND equality
    val Cs1a = searchAlgorithms.S1LinearSearch( notFound = false, unique = true, equality = true, blockCount = 2000)
    println("S1 Linear Search (key, equality): $Cs1a")
    //  If key (unique) AND range
    val Cs1b = searchAlgorithms.S1LinearSearch( notFound = false, unique = true, equality = false, blockCount = 2000)
    println("S1 Linear Search (key, range): $Cs1b")
    //  If NON-key (not unique) AND equality
    val Cs1c = searchAlgorithms.S1LinearSearch( notFound = false, unique = false, equality = true, blockCount = 2000)
    println("S1 Linear Search (nonkey, equality): $Cs1c")
    //  If key (not unique) AND range
    val Cs1d = searchAlgorithms.S1LinearSearch( notFound = false, unique = false, equality = false, blockCount = 2000)
    println("S1 Linear Search (nonkey, range): $Cs1d")
    println()


    // S2 - Binary Search ------------------------
    // Number of blocks, selection cardinality, blocking factor
    val cost1 = S2BinarySearchCost(64, 1.0, 8)  //equality on a unique key attribute
    println("S2 Cost 1: $cost1")
    val cost2 = S2BinarySearchCost(64,100.0,8) //equality on a non-unique key attribute
    println("S2 Cost 2: $cost2")
    println()


    // S3 -  hash key to retrieve a single record ---------------------------

    // S4 - Index for Multiple Records ------------------------
    val Cs4 = searchAlgorithms.S4IndexForMultipleRecords(indexLevel = 3, blockCount = 2000)
    println("S4 Index for Multiple Records: $Cs4")
    println()


    //S6 - Secondary Index Cost--------------------
    // Example usage for a secondary index on a unique key attribute with an equality condition
    val S6cost1 = S6SecondaryIndexCost(5, isUniqueKeyAttribute = true, isRangeQuery = false)
    println("Cost S6a: $cost1")

    // Example usage for a secondary index on a unique key attribute with a range query
    val S6cost2 = S6SecondaryIndexCost(5, isUniqueKeyAttribute = true, isRangeQuery = true, bI1 = 100, r = 500)
    println("Cost S6b: $S6cost2")

    // Example usage for a secondary index on a non-key attribute with an equality condition
    val S6cost3 = S6SecondaryIndexCost(5, isUniqueKeyAttribute = false, isRangeQuery = false, s = 50.0)
    println("Cost S6a nonkey: $S6cost3")

}
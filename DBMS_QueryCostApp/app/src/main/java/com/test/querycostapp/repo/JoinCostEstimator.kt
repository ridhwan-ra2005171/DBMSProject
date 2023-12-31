package com.test.querycostapp.repo

import com.test.querycostapp.algorithms.joinAlgorithms
import com.test.querycostapp.model.EmployeeMetadata
import com.test.querycostapp.model.IndexMetadata
import com.test.querycostapp.model.ProjectMetadata
import com.test.querycostapp.model.tableTypesClasses.TableClass

object JoinCostEstimator {
    // Create a Table instance for each table

    fun getJoinCost(outerTable : TableClass,
                    innerTable : TableClass,
                    empMetadata : MutableList<EmployeeMetadata>,
                    projectMetadata: MutableList<ProjectMetadata>,
                    indexMetadata : MutableList<IndexMetadata>,
                    noOfBuffers : Int,
                    innerTableHasHash : Boolean
    ) : Map<String, Int> {

        val js = 1.0 / Math.max(
            empMetadata.find { it.EmpAttribute.equals("SSN", ignoreCase = true) }?.NDV!!,
            projectMetadata.find { it.ProjAttribute.equals("ManagedBy", ignoreCase = true) }?.NDV!!
        )

        val bR = outerTable.blockCount
        val bS = innerTable.blockCount
        val R = innerTable.rowCount
        val S = outerTable.rowCount
        val bfrRS = outerTable.bfr
        val xB = innerTable.xB
        val nB = noOfBuffers

        val sB = empMetadata.find { it.EmpAttribute.equals("SSN", ignoreCase = true) }?.selectionCardinality!!

        // The hasSecondaryIndex is used to check if the inner table has a secondary index for the Index-based join (J2)
        // we will assume the inner table is Project, IF NOT, the hasSecondaryIndex will go to the next if statement
        var hasSecondaryIndex = checkIfIndexExists("Project_managedBy", "Secondary", indexMetadata)
        // if inner table is Employee, search if a secondary index on attribute SSN exists
        if (innerTable.tableName.equals("Employee", ignoreCase = true)) {
            hasSecondaryIndex = checkIfIndexExists("Employee_SSN", "Secondary", indexMetadata)
        }

        var hasClusterIndex = checkIfIndexExists("Project_managedBy", "Cluster", indexMetadata)
        if (innerTable.tableName.equals("Employee", ignoreCase = true)) {
            hasClusterIndex = checkIfIndexExists("Employee_SSN", "Cluster", indexMetadata)
        }

        var hasPrimaryIndex = checkIfIndexExists("Project_managedBy", "Primary", indexMetadata)
        if (innerTable.tableName.equals("Employee", ignoreCase = true)) {
            hasPrimaryIndex = checkIfIndexExists("Employee_SSN", "Primary", indexMetadata)
        }

        // checking if sorting is needed for J3
        var outerSorted = false // default, also if its project it should be false
        var innerSorted = false // default, also if its project it should be false
        if(outerTable.tableName.equals("Employee", ignoreCase = true) && checkIfIndexExists("Employee_SSN", "Primary", indexMetadata)){
            outerSorted = true
        }
        if(innerTable.tableName.equals("Employee", ignoreCase = true) && checkIfIndexExists("Employee_SSN", "Primary", indexMetadata)){
            innerSorted = true
        }

//        var hasHashIndex = checkIfIndexExists("Project_managedBy", "Hash", indexMetadata)
//        if (innerTable.tableName.equals("Employee", ignoreCase = true)) {
//            hasHashIndex = checkIfIndexExists("Employee_SSN", "Hash", indexMetadata)
//        }

        // J1 - Nested-loop join --------------
        val CJ1 = joinAlgorithms.J1NestedLoopJoinCost(
            bR = bR,
            bS = bS,
            js = js,
            R = R,
            S = S,
            bfrRS = bfrRS,
            nB = nB
        )

        // J2 - Index Based join
        val CJ2 = joinAlgorithms.J2IndexBasedJoinCost(
            js = js,
            bR = bR,
            R = R,
            S = S,
            xB = xB,
            sB = sB,
            bfrRS = bfrRS,
            hasSecondaryIndex = hasSecondaryIndex,
            hasClusterIndex = hasClusterIndex,
            hasPrimaryIndex = hasPrimaryIndex,
            innerTableHasHash = innerTableHasHash )
        // Storing the cost of each join in an object each (deconstructing the CJ2 list)
        val (CJ2a, CJ2b, CJ2c, CJ2d) = CJ2.values.toList()

        // J3 - Sort-merge join
        val CJ3 = joinAlgorithms.J3SortMergeJoinCost(
            bR = bR,
            bS = bS,
            js = js,
            R = R,
            S = S,
            bfrRS = bfrRS,
            outerSorted = outerSorted,
            innerSorted = innerSorted
        ) //figure out sorting needed

        // J4 - Partition-hash join
        val CJ4 = joinAlgorithms.J4PartitionHashJoinCost(
            bR = bR,
            bS = bS,
            js = js,
            R = R,
            S = S,
            bfrRS = bfrRS
        )

        // The costs of each join, stored in a list
        val cost_list = mutableMapOf(
            "CJ1 - Nested-Loop join" to CJ1,
            "CJ2a - Index-based join (Secondary Index)" to CJ2a,
            "CJ2b - Index-based join (Cluster Index)" to CJ2b,
            "CJ2c - Index-based join (Primary Index)" to CJ2c,
            "CJ2d - Index-based join (Hash Index)" to CJ2d,
            "CJ3 - Sort-merge join" to CJ3,
            "CJ4 - Partition-hash join" to CJ4)
        return cost_list.filter {it.value > 0}.toList().sortedBy { (_, value) -> value}.toMap()
    }



}

fun checkIfIndexExists(indexName : String, indexType : String, indexMetadata : MutableList<IndexMetadata>) : Boolean {
    return indexMetadata.find { it.indexName.equals(indexName, ignoreCase = true) && it.Index.equals(indexType, ignoreCase = true) } != null
}
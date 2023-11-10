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
                    noOfBuffers : Int) : Map<String, Double> {
        val js = 1.0 / Math.max(
            empMetadata.find { it.EmpAttribute.equals("SSN", ignoreCase = true) }?.NDV!!,
            projectMetadata.find { it.ProjAttribute.equals("ManagedBy", ignoreCase = true) }?.NDV!!
        )

        val bR = outerTable.blockCount
        val bS = innerTable.blockCount
        val R = innerTable.rowCount
        val S = outerTable.rowCount
        val bfrRS = outerTable.bfr
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

        var hasHashIndex = checkIfIndexExists("Project_managedBy", "Hash", indexMetadata)
        if (innerTable.tableName.equals("Employee", ignoreCase = true)) {
            hasHashIndex = checkIfIndexExists("Employee_SSN", "Hash", indexMetadata)
        }

        // J1 - Nested-loop join --------------
        val CJ1 = joinAlgorithms.J1NestedLoopJoinCost( bR, bS, js, R, S, bfrRS, nB)

        // J2 - Index Based join
        val CJ2 = joinAlgorithms.J2IndexBasedJoinCost(
            js, bR, R, S, nB, sB, bfrRS,
            hasSecondaryIndex, hasClusterIndex, hasPrimaryIndex,
            innerHashIndex = true, outerHasHashIndex = false )
        // Storing the cost of each join in an object each (deconstructing the CJ2 list)
        val (CJ2a, CJ2b, CJ2c, CJ2d) = CJ2.values.toList()

        // J3 - Sort-merge join
        val CJ3 = joinAlgorithms.J3SortMergeJoinCost(bR, bS, js, R, S, bfrRS, false) //figure out sorting needed

        // J4 - Partition-hash join
        val CJ4 = joinAlgorithms.J4PartitionHashJoinCost(bR, bS, js, R, S, bfrRS)

        // The costs of each join, stored in a list
        val cost_list = mutableMapOf<String, Double>("CJ1" to CJ1, "CJ2a" to CJ2a, "CJ2b" to CJ2b, "CJ2c" to CJ2c, "CJ2d" to CJ2d, "CJ3" to CJ3, "CJ4" to CJ4)
        return cost_list.filter {it.value > 0}.toList().sortedBy { (_, value) -> value}.toMap()
    }



}

fun checkIfIndexExists(indexName : String, indexType : String, indexMetadata : MutableList<IndexMetadata>) : Boolean {
    return indexMetadata.find { it.indexName.equals(indexName, ignoreCase = true) && it.Index.equals(indexType, ignoreCase = true) } != null
}
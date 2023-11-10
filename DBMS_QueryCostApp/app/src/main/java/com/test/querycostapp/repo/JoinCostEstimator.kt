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
                    indexMetadata : MutableList<IndexMetadata>) : Map<String, Double> {
        val js = 1.0 / Math.max(
            empMetadata.find { it.EmpAttribute.equals("SSN", ignoreCase = true) }?.NDV!!,
            projectMetadata.find { it.ProjAttribute.equals("ManagedBy", ignoreCase = true) }?.NDV!!
        )

        val bR = outerTable.blockCount
        val bS = innerTable.blockCount
        val R = innerTable.rowCount
        val S = outerTable.rowCount
        val bfrRS = outerTable.bfr
        val nB = outerTable.blockCount

        val sB = empMetadata.find { it.EmpAttribute.equals("SSN", ignoreCase = true) }?.selectionCardinality!!

        // The hasSecondaryIndex is used to check if the inner table has a secondary index for the Index-based join (J2)
        // we will assume the inner table is Project, IF NOT, the hasSecondaryIndex will go to the next if statement
        var hasSecondaryIndex = checkIfIndexExists("Project_projectNo", "Secondary", indexMetadata)
        // if inner table is Employee, search if a secondary index on attribute SSN exists
        if (innerTable.tableName.equals("Employee", ignoreCase = true)) {
            hasSecondaryIndex = checkIfIndexExists("Employee_SSN", "Secondary", indexMetadata)
        }

        var hasClusterIndex = checkIfIndexExists("Project_projectNo", "Cluster", indexMetadata)
        if (innerTable.tableName.equals("Employee", ignoreCase = true)) {
            hasClusterIndex = checkIfIndexExists("Employee_SSN", "Cluster", indexMetadata)
        }

        var hasPrimaryIndex = checkIfIndexExists("Project_projectNo", "Primary", indexMetadata)
        if (innerTable.tableName.equals("Employee", ignoreCase = true)) {
            hasPrimaryIndex = checkIfIndexExists("Employee_SSN", "Primary", indexMetadata)
        }

        var hasHashIndex = checkIfIndexExists("Project_projectNo", "Hash", indexMetadata)
        if (innerTable.tableName.equals("Employee", ignoreCase = true)) {
            hasHashIndex = checkIfIndexExists("Employee_SSN", "Hash", indexMetadata)
        }

        var costList = mapOf<String, Double>()

        // J1 - Nested-loop join --------------
        joinAlgorithms.J1NestedLoopJoinCost( bR, bS, js, R, S, bfrRS, nB)

        // J2 - Index Based join
        joinAlgorithms.J2IndexBasedJoinCost(js, bR, R, S, nB, sB, bfrRS, hasSecondaryIndex, hasClusterIndex, hasPrimaryIndex, outerHasHashIndex = false, innerHashIndex = true )

        // J3 - Sort-merge join
        joinAlgorithms.J3SortMergeJoinCost(bR, bS, js, R, S, bfrRS, false)

        // J4 - Partition-hash join
        joinAlgorithms.J4PartitionHashJoinCost(bR, bS, js, R, S, bfrRS)

        return costList
    }



}

fun checkIfIndexExists(indexName : String, indexType : String, indexMetadata : MutableList<IndexMetadata>) : Boolean {
    return indexMetadata.find { it.indexName.equals(indexName, ignoreCase = true) && it.Index.equals(indexType, ignoreCase = true) } != null
}
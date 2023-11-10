package com.test.querycostapp.algorithms

import kotlin.math.ceil
import kotlin.math.log
import kotlin.math.roundToInt

object joinAlgorithms {

    // J1—Nested-loop join
    fun J1NestedLoopJoinCost(bR: Int, bS: Int, js: Double, R: Int, S: Int, bfrRS: Int, nB: Int) : Double{
        val CJ1 = if(nB == 3){
            bR + (bR * bS) + (js * R * S) / bfrRS.toDouble()
        }else{
            bR + (ceil((bR/(nB-2)).toDouble()) * bS) + (js * R * S) / bfrRS.toDouble()
        }
        return CJ1
    }


    //J2—Index-based nested-loop join (using an access structure to retrieve the matching record(s))
    // ONLY APPLICABLE if the INNER TABLE HAS INDEX
    // R join (A = B) S

    // If an index exists for the join attribute B of S with index levels xB,
    // we can retrieve each record s in R and then use the index to retrieve all the matching
    // records t from S that satisfy t[B] = s[A]. The cost depends on the type of index.

    // outerBlockCount: if the outer table (R) has index on attribute A, the outerBlockCount is the number of blocks of the index
    // otherwise, its the number of blocks for the entire outer table
    fun J2IndexBasedJoinCost(js : Double, bR : Int, R : Int, S : Int, xB : Int,
                             sB: Double, // selection cardinality for inner table attribute B
                             bfrRS : Int,
                             hasSecondaryIndex : Boolean, hasClusterIndex : Boolean, hasPrimaryIndex : Boolean,
                             innerHashIndex : Boolean, outerHasHashIndex : Boolean,
                             ) : Map<String, Double> {

        var c1 = 0.0; var c2 =0.0; var c3 =0.0; var c4 = 0.0

        // Third term of the cost functions, 3rd term signifies the cost of performing the join itself...
        // ...it is static between the 4 functions, so we store it in a variable to re-use it
        val term3 = ((js * R * S) / bfrRS.toDouble())

        // J2a - Secondary index
        // CJ2a = bR + (|R| * (xB + 1 + sB)) + (( js * |R| * |S|)/bfrRS)
        if (hasSecondaryIndex) {
            c1 = bR + (R * (xB + 1 + sB)) + term3
        }
        // J2b - Clustering index
        // CJ2b = bR + (|R| * (xB + (sB/bfrB))) + (( js * |R| * |S|)/bfrRS)
        if (hasClusterIndex) {
            c2 = bR + ( R * ( xB + (sB / bfrRS))) + term3
        }

        // J2c - Primary index
        // CJ2c = bR + (|R| * (xB + 1)) + ((js * |R| * |S|)/bfrRS)
        if (hasPrimaryIndex) {
            c3 = bR + ( R * ( xB + 1)) + term3
        }

        // J2d - Hash index
        // CJ2d = bR + (|R| * h) + ((js * |R| * |S|)/bfrRS)
        // h is the average number of block accesses to retrieve a record, given its hash key value
        val h = 1.2
        // if the innner table has a hash index (innerHashIndex = true) use the above formula
        if (innerHashIndex && outerHasHashIndex == false) {
            c4 = bR + ( R * h ) + term3
        } else { // if both tables (inner and outer) have hash index modify the formula to be
                 // bR + (|R| * h) + (h * |S|) + ((js * |R| * |S|)/bfrRS)
            c4 = bR + ( R * h ) + (h * S) + term3
        }

        // return a map of the cost functions in descending order
        return mapOf<String, Double>("CJ2a(secondary)" to c1, "CJ2b(cluster)" to c2, "CJ2c(primary)" to c3, "CJ2d(hash)" to c4)
//            .toSortedMap(compareByDescending { it })
    }


    // J3—Sort-merge join

//    CJ3a = bR + bS + ((js * |R| * |S|)/bfrRS)
        //if sorting is needed on join attribute => add (2 * b) + (2 * b * (logdM nR))
fun J3SortMergeJoinCost(
    bR: Int, // Number of blocks in the outer table
    bS: Int, // Number of blocks in the inner table
    js: Double, // Join cardinality
    R: Int, // Number of records in the outer table
    S: Int, // Number of records in the inner table
    bfrRS: Int, // Blocking factor of R and S
    sortingNeeded: Boolean // Whether sorting is needed on join attribute
): Double {
    // CJ3a = bR + bS + ((js * R * S) / bfrRS)
    val CJ3a = bR + bS + (js * R * S) / bfrRS.toDouble()

    return if (sortingNeeded) {
        // If sorting is needed, add (2 * b) + (2 * b * (logdM nR))
        val dM = 10 // Assuming dM (M-way merge) is a constant value
        val nR = R + S // Combined number of records in R and S

        CJ3a + (2 * bR) + (2 * bR * log(dM.toDouble(), nR.toDouble())).roundToInt().toDouble()
    } else {
        // If sorting is not needed, return CJ3a as the cost
        CJ3a
    }
}


    // J4—Partition–hash join (or just hash join)
    //
    fun J4PartitionHashJoinCost(bR: Int, bS: Int, js: Double, R: Int, S: Int, bfrRS: Int): Double{
        val CJ4 = 3 * (bR + bS) + (js * R * S) / bfrRS.toDouble()
        return CJ4
    }

}

// Testing join algorithms
fun main(args: Array<String>) {
    // J1 - Nested-loop join --------------



    // J2 - Index-based nested-loop join ONLY APPLICABLE if the INNER TABLE HAS INDEX
    // Employee as outer table & Department as inner with Primary Index on deptNo
    val j2 = joinAlgorithms.J2IndexBasedJoinCost(
        hasPrimaryIndex = true, bfrRS = 4, js = (1.0/125.0), bR = 2000,
        R = 10000, S = 125, xB = 1,
        sB = 1.0, hasClusterIndex = false, hasSecondaryIndex = false,
        innerHashIndex = true, outerHasHashIndex = false
    )
    println("J2 Index-based nested-loop join: \n$j2")


    //J3 - Sort-merge join
    val j3Cost1 = joinAlgorithms.J3SortMergeJoinCost(
        bR = 2000, bS = 125, js = (1.0/125.0), R = 10000, S = 125, bfrRS = 4, sortingNeeded = true
    )
    println("J3 Sort-merge join not sorted: \n$j3Cost1")
    val j3Cost2 = joinAlgorithms.J3SortMergeJoinCost(
        bR = 2000, bS = 125, js = (1.0/125.0), R = 10000, S = 125, bfrRS = 4, sortingNeeded = false
    )
    println("J3 Sort-merge join sorted: \n$j3Cost2")


}
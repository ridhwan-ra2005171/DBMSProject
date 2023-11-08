package com.test.querycostapp.algorithms

import com.test.querycostapp.model.Employee
import com.test.querycostapp.repo.EmpMetaRepo
import kotlin.math.ceil
import kotlin.math.log2

object searchAlgorithms{


    // S1 - Linear Search (brute force) approach 
    // if select is EQUALITY and KEY, cost = b/2
    // if select is EQUALITY and NON-KEY, cost = b
    // if select is RANGE, cost = b
    fun S1LinearSearch(notFound : Boolean, unique : Boolean, equality : Boolean, blockCount : Int) : Double {
        // assign blockCount to cost
        var cost = blockCount.toDouble()
        // if the select is NOT an equality, the attribute is not unique i.e. non key, or the record doesn't exist; return the number of blocks i.e. the cost
        if (!equality or !unique or notFound) {
            return cost
        } else {
            // if the select is an equality, the attribute is unique and the record exists; return HALF of the number of blocks i.e. cost/2
            return cost / 2
        }
    }




    //S2 - Binary Search
    //if its on binary S2:
//    log2b + ceil[ (s/bfr)] -1 fileblocks
//            reduces to log2b if equality condition is on unique key / attribute. ( just make s=1 when its on unique)

    fun S2BinarySearchCost(b: Int, s: Double, bfr: Int): Double {
        return if (s == 1.0) {
            // When s is 1 (equality on a unique key attribute)
            log2(b.toDouble())
        } else {
            // For other scenarios
            log2(b.toDouble()) + ceil(s / bfr) - 1
        }
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
    fun S4IndexForMultipleRecords(indexLevel: Int, blockCount: Int): Double {
        return indexLevel.toDouble() + ((blockCount.toDouble()) / 2)
    }
}
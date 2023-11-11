package com.test.querycostapp.algorithms

import com.test.querycostapp.algorithms.searchAlgorithms.S2BinarySearchCost
import com.test.querycostapp.algorithms.searchAlgorithms.S6SecondaryIndexCost
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
            // For other scenarios if not on unique key
            log2(b.toDouble()) + ceil(s / bfr) - 1
        }
    }

    //S3a CS3a = x + 1.0, x is levels of index
    fun S3aPrimaryKeySelectCost(x: Int): Int{
        return x + 1
    }

    fun S3bHashKeySelectCost(): Double{
        return 1.0
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


    //S6- Using a secondary (B+-tree) index
    //S6a -> For a secondary index on a key (unique) attribute, with an equality condition, cost is x + 1
    //for secondary on nonkey attribute, cost is CS6a = x + 1 + s [s is selection cardinality]
    //S6b -> for range queries The cost estimate for this case, approximately, is CS6b = x + (bI1/2) + (r/2) [bI1 is the number of blocks in the index, r is the number of records in the index]

    fun S6SecondaryIndexCost(x: Int, isUniqueKeyAttribute: Boolean, isRangeQuery: Boolean, s: Double = 0.0, bI1: Int = 0, r: Int = 0): Double {
        return if (!isRangeQuery) {
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
        }
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
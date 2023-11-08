package com.test.querycostapp.algorithms

import com.test.querycostapp.model.Employee
import kotlin.math.ceil
import kotlin.math.log2

object searchAlgorithms{



// S1 - Linear Search (brute force) approach
fun linearSearch(attribute : String, value: String, table : List<Any>, equality : Boolean) : Int {
    // get the number of blocks of the table
    var blockCount: Int = if (table[0] is Employee) {
        // get the block count for Employee from the metadata
        return 0
    } else {
        // get the block count for Project from the metadata
        return 0
    }

    // assign blockCount to cost
    var cost = blockCount

    // if the select is NOT an equality, return all the number of blocks i.e. the cost
    if (!equality) {
        return cost
    }
    // If the select is equality, check if the attribute is a key or non-key
    // if the attribute is a key and the value exists in the table, return half of the number of blocks as per the formula in the book
//    if (attributevalueExists) {
//
//    }
    // if record doesn't exist in table, return b
    // call the valueExists function to check if the record exists in the table

    // find if the records exists in table
    // Retrieve all records: Cost = b
//    val exists = table.contains()

    // If the record is not in the table, then search the whole table
    // meaning search all the blocks
    // otherwise the cost is searching half of the blocks



    return 0

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

}
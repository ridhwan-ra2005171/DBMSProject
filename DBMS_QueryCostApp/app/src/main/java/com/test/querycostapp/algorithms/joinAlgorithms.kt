package com.test.querycostapp.algorithms

object joinAlgorithm {

    // J1—Nested-loop join


    //J2—Index-based nested-loop join (using an access structure to retrieve the matching record(s))
    // ONLY APPLICABLE if the INNER TABLE HAS INDEX
    // R join (A = B) S

    // If an index exists for the join attribute B of S with index levels xB,
    // we can retrieve each record s in R and then use the index to retrieve all the matching
    // records t from S that satisfy t[B] = s[A]. The cost depends on the type of index.

    // outerBlockCount: if the outer table (R) has index on attribute A, the outerBlockCount is the number of blocks of the index
    // otherwise, its the number of blocks for the entire outer table
    fun S2IndexBaseJoinCost(js : Double, outerBlockCount : Int, outerRowCount : Int, innerRowCount : Int, innerIndexLevel : Int, innnerSelectioCard: Double, bfr : Int,
                            hasSecondaryIndex : Boolean, hasClusterIndex : Boolean, hasPrimaryIndex : Boolean, hasHashIndex : Boolean,
                            outerHashValue : Double = 0.0, innerHashValue : Double = 0.0) : Map<String, Double> {

        var c1 = 0.0; var c2 =0.0; var c3 =0.0; var c4 = 0.0

        // Third term of the cost functions, 3rd term signifies the cost of performing the join itself...
        // ...it is static between the 4 functions, so we store it in a variable to re-use it
        val term3 = ((js * outerRowCount * innerRowCount).toDouble() / bfr.toDouble())

        // J2a - Secondary index
        // CJ2a = bR + (|R| * (xB + 1 + sB)) + (( js * |R| * |S|)/bfrRS)
        if (hasSecondaryIndex) {
            c1 = outerBlockCount + (outerRowCount * (innerIndexLevel + 1 + innnerSelectioCard)) + term3
        }
        // J2b - Clustering index
        // CJ2b = bR + (|R| * (xB + (sB/bfrB))) + (( js * |R| * |S|)/bfrRS)
        if (hasClusterIndex) {
            c2 = outerBlockCount * ( outerRowCount * ( innerIndexLevel + (innnerSelectioCard / bfr))) + term3
        }

        // J2c - Primary index
        // CJ2c = bR + (|R| * (xB + 1)) + ((js * |R| * |S|)/bfrRS)
        if (hasPrimaryIndex) {
            c3 = outerBlockCount * ( outerRowCount * ( innerIndexLevel + 1)) + term3
        }

        // J2d - Hash index
        // CJ2d = bR + (|R| * (xB + 1)) + ((js * |R| * |S|)/bfrRS)
        // h is the average number of block accesses to retrieve a record, given its hash key value
        val h = 1.0
        // if there is a hash index AND if the outer table has hash index OR the inner table has hash index
        if (hasHashIndex || !(outerHashValue == 0.0 && innerHashValue == 0.0)) {
            c4 = outerBlockCount * ( outerRowCount * ( innerIndexLevel + 1)) + term3
        }

        // return a map of the cost functions in descending order
        return mapOf<String, Double>("C1" to c1, "C2" to c2, "C3" to c3, "C4" to c4).toSortedMap(compareByDescending { it })
    }


    // J3—Sort-merge join



    // J4—Partition–hash join (or just hash join)
}
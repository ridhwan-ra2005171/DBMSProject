package com.test.querycostapp.repo

object CostEstimatorRepo{


    var writtenQuery = mutableListOf<String>() //will be passed when Done is entered



    //primary key = SSN
//    primary key and equality operator,
//    example query SELECT * FROM Employee WHERE SSN = '1234';


//    primary key with range operator,


    //S2 binary Search //target = either ssn or projectNo
    fun binarySearch(arr: IntArray, target: Int): Int {
        var left = 0
        var right = arr.size - 1

        while (left <= right) {
            val mid = left + (right - left) / 2

            if (arr[mid] == target) {
                return mid // Element found, return its index
            }

            if (arr[mid] < target) {
                left = mid + 1 // Target is in the right half
            } else {
                right = mid - 1 // Target is in the left half
            }
        }

        return -1 // Element not found
    }
}
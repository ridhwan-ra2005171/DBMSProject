//package com.test.querycostapp.algorithms
//
//import com.test.querycostapp.model.ConditionClass
//import com.test.querycostapp.repo.indexExists
//// Disjunction SELECT
////return int and double from the function
//fun S7DisjunctionSelectCost(conditionList : List<ConditionClass>) : Map<String, Int> {
//
//    val allHaveIndex = conditionList.all { indexExists(it.attributeName) }
//
//    var costPerAttr : MutableMap<String, Int> = emptyMap<String, Int>().toMutableMap<String, Int>()
//
//    // If any attribute in the query has NO index, return the brute force cost
//    if (!allHaveIndex) {
//        return mapOf<String, Int>("S1 - Linear Search", searchAlgorithms.S1LinearSearch())
//    }
//    // Equality:
//    // S1(key, nonkey, S2(key, nonkey), S3a(key), S6a (key, nonkey)
//    // Range
//    // S1(key, nonkey), S4 (
//    // If all the attributes involved in the query have an index
//    // Sum up the cost of those index searches
//    // IF ALL ATTRIBUTES HAVE INDEX
//    for (condition in conditionList) {
//        // Check if condition is equality first
//        if (condition.operator == "=") {
//            // Check if the attribute is unique
//            // Unique
//            if (listOf("ssn", "projectno").indexOf(condition.attributeName) >= 0) {
//                // find the costs of each equality search
//                val keyCosts: MutableMap<String, Int> = mutableMapOf()
////                    keyCosts.put("${condition.attributeName}: S1 - Linear Search", searchAlgorithms.S1LinearSearch())
//                keyCosts.put(
//                    "${condition.attributeName}: S2 - Binary Search",
//                    searchAlgorithms.S2BinarySearchCost()
//                )
//                keyCosts.put(
//                    "${condition.attributeName}: S3a - Primary Key Select",
//                    searchAlgorithms.S3aPrimaryKeySelectCost()
//                )
//                keyCosts.put(
//                    "${condition.attributeName}: S6a - Secondary Index",
//                    searchAlgorithms.S6SecondaryIndexCost()
//                )
//                // add the least cost for this attribute in the costPerAttr map
//                costPerAttr.put("${keyCosts.minBy { it.value }!!.key}", keyCosts.minBy { it.value }!!.value)
//
//            } else { // Non-Unique
//                val nonKeyCosts: MutableMap<String, Int> = mutableMapOf()
////                nonKeyCosts.put("${condition.attributeName}: S1 - Linear Search", searchAlgorithms.S1LinearSearch())
//                nonKeyCosts.put(
//                    "${condition.attributeName}: S2 - Binary Search",
//                    searchAlgorithms.S2BinarySearchCost()
//                )
//                nonKeyCosts.put(
//                    "${condition.attributeName}: S6a - Secondary Index",
//                    searchAlgorithms.S6SecondaryIndexCost()
//                )
//                // add the least cost for this attribute in the costPerAttr map
//                costPerAttr.put("${nonKeyCosts.minBy { it.value }!!.key}", nonKeyCosts.minBy { it.value }!!.value)
//            }
//            // Range
//            // S1(key, nonkey), S4 (key), S6b (key, nonkey)
//        } else { // If condition is RANGE
//            // Check if the attribute is unique
//            if (listOf("ssn", "projectno").indexOf(condition.attributeName) >= 0) {
//                val keyCosts: MutableMap<String, Int> = mutableMapOf()
////                keyCosts.put("${condition.attributeName}: S1 - Linear Search", searchAlgorithms.S1LinearSearch())
//                keyCosts.put(
//                    "${condition.attributeName}: S4 - Range Search",
//                    searchAlgorithms.S4IndexForMultipleRecords()
//                )
//                keyCosts.put(
//                    "${condition.attributeName}: S6b - Secondary Index",
//                    searchAlgorithms.S6SecondaryIndexCost()
//                )
//                // add the least cost for this attribute in the costPerAttr map
//                costPerAttr.put("${keyCosts.minBy { it.value }!!.key}", keyCosts.minBy { it.value }!!.value)
//
//            } else { // Non-Unique S1, S6b
//                val nonKeyCosts: MutableMap<String, Int> = mutableMapOf()
////                nonKeyCosts.put("${condition.attributeName}: S1 - Linear Search", searchAlgorithms.S1LinearSearch())
//                nonKeyCosts.put(
//                    "${condition.attributeName}: S6b - Secondary Index",
//                    searchAlgorithms.S6SecondaryIndexCost()
//                )
//                // add the least cost for this attribute in the costPerAttr map
//                costPerAttr.put("${nonKeyCosts.minBy { it.value }!!.key}", nonKeyCosts.minBy { it.value }!!.value)
//
//            }
//
//        }
//    }
//
//    return costPerAttr
//}

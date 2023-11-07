package com.test.querycostapp.model

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import kotlinx.serialization.Serializable

@Serializable
data class PackageItem(
    val packageId: Int,
    val author: String,
    val category: String,
    val description: String,
    val iconUrl: String,
    val language: String,
    val lastUpdatedDate: String,
    val level: String, //this is difficulty
    val title: String,
    val version: Int,
    val words: List<WordItem>,
    var downloaded: Boolean = false
) {
//    var ratings : MutableList<RatingItem>
//        get() = mutableListOf<RatingItem>()
//        set(newList) {
//            this.ratings = newList
//        }

//    fun addRating(ratingItem: RatingItem) {
//        Log.d("Add _rating method", "Before addRating: ${ratings}")
//        val tempList = this.ratings.toMutableList() + ratingItem
//        ratings = tempList.toMutableList()
//        // // ratings.add(ratingItem)
//        Log.d("Add _rating method", "After addRating: ${ratings}")
//    }

}
//@Serializable
//data class LanguagePackage(
//    val packageId: Int,
//    val author: String,
//    val category: String,
//    val description: String,
//    val iconUrl: String,
//    val language: String,
//    val lastUpdatedDate: String,
//    val level: String,
//    val title: String,
//    val version: Int,
//    val words: List<WordItem>
//)
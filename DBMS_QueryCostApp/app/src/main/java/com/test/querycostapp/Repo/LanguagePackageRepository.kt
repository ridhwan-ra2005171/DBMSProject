package com.test.querycostapp.Repo

import android.content.Context
import android.util.Log
import com.test.querycostapp.model.PackageItem
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

object LanguagePackageRepository {
    var languagePackages: List<PackageItem> = listOf<PackageItem>() // Made language packages private to avoid modification


    fun initPackages(context: Context): List<PackageItem> {
        if(languagePackages.isEmpty()){
            val jsonContent=
                context
                    .assets
                    .open("packages.json")
                    .bufferedReader().use { it.readText() }
            languagePackages = (Json{ignoreUnknownKeys = true}.decodeFromString(jsonContent))
        }

        Log.d("PACKAGE REPO", languagePackages.map{it.toString()}.toString())
        return languagePackages // return packages NOT learning package
    }

}

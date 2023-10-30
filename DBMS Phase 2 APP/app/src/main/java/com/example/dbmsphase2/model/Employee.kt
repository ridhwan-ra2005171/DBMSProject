package com.example.dbmsphase2.model

import kotlinx.serialization.Serializable

@Serializable
data class Employee(
    val SSN: String,
    val Fname: String,
    val Minit: String,
    val Lname: String,
    val DOB: String,
    val Address: String,
    val Gender: String,
    val PhoneNo: String,
    val HireDate: String,
    val Manager: Boolean,
    val ManagerSSN: String?
)

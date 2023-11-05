package com.example.dbmsphase2.view

import DDMenu
import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun QueryScreen() {
    var selectedOperation by remember { mutableStateOf("") }
    var selectedTable by remember { mutableStateOf("") }
    var selectedAttribute by remember { mutableStateOf("") }
    var selectedOperator by remember { mutableStateOf("") }
//
//    var test by remember {mutableStateOf("")}
//    test = selectedOperation

    var operatorText by remember {mutableStateOf("")} //to store range or equality

    var queryResult by remember { mutableStateOf("") }

    val operationList = listOf("SELECT", "JOIN")
    //add if statement for attribute list
    val tableList = listOf("Employee", "Project")
    var attributeList = mutableListOf("SSN", "Fname", "Minit", "Lname", "DOB", "Address", "Gender", "PhoneNo", "HireDate", "Manager", "ManagerSSN") //subject to change
    attributeList = mutableListOf("test")

    val operatorList = listOf("=", "<", ">", "<=", ">=")

    when (selectedTable) {
        "Employee" -> {
            attributeList = mutableListOf("SSN", "Fname", "Minit", "Lname", "DOB", "Address", "Gender", "PhoneNo", "HireDate", "Manager", "ManagerSSN")
        }
        "Project" -> {
            attributeList = mutableListOf("ProjectNo", "ProjectName", "Description", "ProjectLoc", "ManagedBy")
        }
    }


    Column (modifier = Modifier
        .fillMaxSize()
        .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
    //dropdown select or join
        DDMenu(
            items = operationList,
            selectedItem = remember { mutableStateOf(selectedOperation) },
            label = "Select an Operation"
        ){ selectedValue ->
            selectedOperation = selectedValue
        }

        if (selectedOperation == "SELECT") {
        //dropdown table selection
        DDMenu(
            items = tableList,
            selectedItem = remember { mutableStateOf(selectedTable) },
            label = "Select a table"
        ){ selectedValue ->
            selectedTable = selectedValue
        }


        // choose on what (if select is chosen), otherwise on join choice it wont be chosen
        DDMenu(
            items = attributeList,
            selectedItem = remember { mutableStateOf(selectedAttribute) },
            label = "Select an attribute"
        ){ selectedValue ->
            selectedAttribute = selectedValue
        }



        //operator chosen
        DDMenu(
            items = operatorList,
            selectedItem = remember { mutableStateOf(selectedOperator) },
            label = "Select an operator"
        ){ selectedValue ->
            selectedOperator = selectedValue
        }


            Log.d("Selected", "QueryScreen: ${selectedOperation}")
            Log.d("Selected", "QueryScreen: ${selectedTable}")
            Log.d("Selected", "QueryScreen: ${selectedAttribute}")
            Log.d("Selected", "QueryScreen: ${selectedOperator}")
        //textfield for that operation
        OutlinedTextField(
            value = operatorText,
            onValueChange = {
                operatorText = it
            },
            label = { Text("Label") },
            placeholder = { Text("Enter text") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { /* Handle done action if needed */ }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.Gray, MaterialTheme.shapes.small) // Add the border
                .padding(16.dp)
        )
            queryResult = selectedOperation + " " + selectedTable + " " + selectedAttribute + " " + selectedOperator + " " + operatorText
        }else if(selectedOperation == "JOIN"){
            //if join is chosen, display attribute for equijoin
            attributeList = mutableListOf("ManagerSSN")
            DDMenu(
                items = attributeList,
                selectedItem = remember { mutableStateOf(selectedAttribute) },
                label = "Select an attribute"
            ){ selectedValue ->
                selectedAttribute = selectedValue
            }
            queryResult = selectedOperation + " " + selectedAttribute

        }

        //result to display the query
        Text(text = queryResult)

        //text for the result of query:
        Text(text = "Result is here")

        //lazy Row to list the results
        LazyRow() {

        }
        //need to display of the algorithms and sort them in descending order in terms of time
    }
}

@Preview
@Composable
fun QueryScreenPreview() {
    QueryScreen()
}
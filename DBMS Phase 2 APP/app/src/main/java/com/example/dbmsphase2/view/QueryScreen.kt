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
    var selectedAttribute by remember { mutableStateOf("") }
    var selectedOperator by remember { mutableStateOf("") }
//
//    var test by remember {mutableStateOf("")}
//    test = selectedOperation

    var operatorText by remember {mutableStateOf("")} //to store range or equality

    var queryResult by remember { mutableStateOf("") }

    val operationList = listOf("SELECT", "JOIN")
    val attributeList = listOf("SSN", "Fname", "Minit", "Lname", "DOB", "Address", "Gender", "PhoneNo", "HireDate", "Manager", "ManagerSSN") //subject to change
    val operatorList = listOf("=", "<", ">", "<=", ">=")


    Column (modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {
    //dropdown select or join
        DDMenu(
            items = operationList,
            selectedItem = remember { mutableStateOf(selectedOperation) },
            label = "Select an Operation"
        ){ selectedValue ->
            selectedOperation = selectedValue
        }
        // choose on what (if select is chosen), otherwise on join choice it wont be chosen
        DDMenu(
            items = attributeList,
            selectedItem = remember { mutableStateOf(selectedAttribute) },
            label = "Select an attribute"
        ){ selectedValue ->
            selectedAttribute = selectedValue
        }
//        selectedOperation = "SELECT"

        Log.d("Selected", "QueryScreen: ${selectedOperation}")
        if (selectedOperation == "SELECT") {
        //operator chosen
        DDMenu(
            items = operatorList,
            selectedItem = remember { mutableStateOf(selectedOperator) },
            label = "Select an operator"
        ){ selectedValue ->
            selectedOperator = selectedValue
        }
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
        }

        //result to display the query
        Text("this is to display the query")

        //text for the result of query:
        Text(text = "Result is here")

        //lazy column to list the results of the algorithms and sort them in descending order in terms of time
        LazyColumn {

        }
    }
}

@Preview
@Composable
fun QueryScreenPreview() {
    QueryScreen()
}
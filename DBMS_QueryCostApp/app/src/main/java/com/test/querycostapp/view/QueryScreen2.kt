package com.example.dbmsphase2.view

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.test.querycostapp.repo.CostEstimatorRepo
import com.test.querycostapp.repo.EmployeeRepo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QueryScreen2() {
    var queryText by remember { mutableStateOf("") }
    var queryResult by remember { mutableStateOf("") }
    var queryTokens = queryResult.split(Regex("\\s+")) // Tokenize by whitespace

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Textfield for query
        OutlinedTextField(
            value = queryText,
            onValueChange = {
                queryText = it
            },
            label = { Text("Query") },
            placeholder = { Text("Enter Query") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    // When the "Done" button is pressed, update queryResult
                    queryResult = queryText
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.Gray, MaterialTheme.shapes.small)
                .padding(16.dp)
        )

        // Button to trigger the action
        Button(
            onClick = {
                // When the button is pressed, update queryResult
                queryResult = queryText
            }
        ) {
            Text(text = "Done")
        }

        // Display queryResult when it's not empty
        if (queryResult.isNotEmpty()) {
            Text(text = queryResult)
            Log.d("tokens", "QueryScreen2: ${queryTokens}")
            CostEstimatorRepo.writtenQuery= queryTokens.toMutableList()
            Log.d("writtenQuery", "QueryScreen2: ${CostEstimatorRepo.writtenQuery}")
        }
    }
}



//        val sampleData = listOf(
//            Person("Alice", 30, "alice@example.com"),
//            Person("Bob", 25, "bob@example.com"),
//            Person("Charlie", 35, "charlie@example.com"),
//            Person("David", 28, "david@example.com")
//        )
//lazy column to list the results of the algorithms and sort them in descending order in terms of time
//        LazyColumn {
//
//        }
//        TableAppEmployee(EmployeeRepo.initEmployees(LocalContext.current))
//        Log.d("QueryScreen2", "QueryScreen2: ${EmployeeRepo.initEmployees(LocalContext.current)}")

@Preview
@Composable
fun QueryScreen2Preview() {
    QueryScreen2()
}
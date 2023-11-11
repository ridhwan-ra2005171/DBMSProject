package com.example.dbmsphase2.view

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.test.querycostapp.repo.CostEstimatorRepo
import com.test.querycostapp.repo.CostEstimatorRepo.handleSelection
import com.test.querycostapp.repo.dataRepos.EmpMetaRepo
import com.test.querycostapp.repo.dataRepos.EmployeeRepo
import com.test.querycostapp.repo.dataRepos.IndexMetaRepo
import com.test.querycostapp.repo.dataRepos.ProjMetaRepo
import com.test.querycostapp.repo.dataRepos.ProjectRepo
import com.test.querycostapp.repo.dataRepos.TablesMetaRepo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QueryScreen2() {
    var showCosts by rememberSaveable { mutableStateOf(false) }

    var queryText by rememberSaveable { mutableStateOf("") }
    var queryResult by rememberSaveable { mutableStateOf("") }
    var queryTokens = queryResult.split(Regex("\\s+")) // Tokenize by whitespace

    var selectionCostList: MutableList<Pair<String, Int>> by rememberSaveable {
        mutableStateOf(
            mutableListOf()
        )
    }



    CostEstimatorRepo.employees = EmployeeRepo.initEmployees(LocalContext.current).toMutableList()
    CostEstimatorRepo.projects = ProjectRepo.initProjects(LocalContext.current).toMutableList()
    //----
    //these will call the metadatas json and store them in the list under CostEstimator repo
    CostEstimatorRepo.tableMetadatas = TablesMetaRepo.initTablesMetadatas(LocalContext.current).toMutableList()
    CostEstimatorRepo.empMetadatas = EmpMetaRepo.initEmployeeMetadatas(LocalContext.current).toMutableList()
    CostEstimatorRepo.projectMetadatas = ProjMetaRepo.initProjectMetadatas(LocalContext.current).toMutableList()
    CostEstimatorRepo.indexMetadatas = IndexMetaRepo.initIndexMetadatas(LocalContext.current).toMutableList()

    Log.d("METADATA", CostEstimatorRepo.tableMetadatas.toString())

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
                queryResult = queryText;
                selectionCostList=handleSelection();
                showCosts = !showCosts //toggle the showCosts
            }
        ) {
            Text(text = "Show Cost")
        }
        CostEstimatorRepo.writtenQuery= queryTokens.toMutableList();

        // Display queryResult when it's not empty
        if (queryResult.isNotEmpty()) {
            Text(text = queryResult, style= TextStyle(fontWeight = FontWeight.Bold))
            Log.d("tokens", "QueryScreen2: ${queryTokens}")
            Log.d("writtenQuery", "QueryScreen2: ${CostEstimatorRepo.writtenQuery}")
        }

        if (showCosts) {
            // List of costs here
            LazyColumn(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(5.dp)) {
                item {
                    Text(
//                        textAlign = TextAlign.Center,
                        text = "Cost of Different Selections",
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

//                items(costList.entries.toList()) { (joinAlgo, joinCost) ->
//
//                    CostItem(key = joinAlgo, value = joinCost.toInt().toString())
//
//                    Divider(
//                        color = MaterialTheme.colorScheme.tertiary,
//                        thickness = 0.5.dp,
//                        modifier = Modifier.padding(vertical = 5.dp)
//                    )
//
//                }
                item {
                    for ((label, cost) in selectionCostList) {
                        println("$label: $cost")
                        Row (modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "$label:",style = TextStyle(fontSize = 16.sp,fontWeight = FontWeight.Bold))
                            Text(text = "$cost", modifier = Modifier.padding(start = 10.dp))
                        }
                    }
                }


            }
        }
    }
}



// Usage in your composable function




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
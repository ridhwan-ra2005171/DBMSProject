@file:OptIn(ExperimentalMaterial3Api::class)

package com.test.querycostapp.view

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import com.test.querycostapp.repo.CostEstimatorRepo
import com.test.querycostapp.repo.JoinCostEstimator
import com.test.querycostapp.view.unusedViews.DDMenu

@Preview
@Composable
fun JoinScreen() {

    var showCosts by rememberSaveable { mutableStateOf(false) }

    val context = LocalContext.current

    val tableList = listOf("Employee", "Project")

    var outerTable by rememberSaveable { mutableStateOf("")}
    var innerTable by rememberSaveable { mutableStateOf("")}
    var bufferNo by rememberSaveable { mutableStateOf(3)}
    var costList by rememberSaveable {
        mutableStateOf(mapOf( "Example 1" to 0.0, "Example 2" to 0.0, "Example 3" to 0.0))
    }

    // we must have a table chooser, then a buffer size chooser
    // size - index - 1

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {

        // Prompt to select outer table
        Text (text = "Select an Outer Table", modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth(),
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            style = TextStyle(fontSize = MaterialTheme.typography.titleLarge.fontSize, fontWeight = FontWeight.Bold))

        // Table chooser
        TableChooser(
            tablesList = tableList,
            selectedItem = mutableStateOf(outerTable),
            onItemSelected = { item1, item2 -> outerTable = item1; innerTable = item2 },
            onBufferNoChanged = {bufferNo = it})

        Divider(color = MaterialTheme.colorScheme.primaryContainer, thickness = 0.5.dp, modifier = Modifier.padding(horizontal = 15.dp))

        // Button to calculate the cost
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
            // Calculate cost button
            Button(
                onClick = {
                    if (outerTable.isEmpty()) {
                        // Display you need to choose a table
                        Toast.makeText(context, "You need to choose a table", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        // Here we call the Join Cost Estimator
//                    JoinCostEstimator.getJoinCost(noOfBuffers = bufferSize.toInt())
                        showCosts = true
                    }


                }) {
                Text(text = "Calculate Cost")
            }
        }

        if (!outerTable.isEmpty()) {
            Text(text = "$outerTable ⨝ $innerTable", fontWeight = FontWeight.ExtraBold, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center )
        }

        if (showCosts) {
            // List of costs here
            LazyColumn(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(5.dp)) {
                item {
                    Text(
//                        textAlign = TextAlign.Center,
                        text = "Cost of Different Joins",
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                items(costList.entries.toList()) { (key, value) ->

                    CostItem(key = key, value = value.toString())

                    Divider(
                        color = MaterialTheme.colorScheme.tertiary,
                        thickness = 0.5.dp,
                        modifier = Modifier.padding(vertical = 5.dp)
                    )

                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TableChooser(tablesList : List<String>, selectedItem : MutableState<String>, onItemSelected : (String, String) -> Unit, onBufferNoChanged : (Int) -> Unit) {

    val buffersList = listOf("3","4","5","6","7","8","9","10")
    val weight = 1f
    val height = 48.dp

    var outerTable by rememberSaveable { mutableStateOf("")}
    var innerTable by rememberSaveable { mutableStateOf("")}

    var bufferSize by rememberSaveable { mutableStateOf("3")}

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp), horizontalArrangement = Arrangement.spacedBy(5.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        // Drop down to choose the outer table (R)
        CustomDropDown(modifier = Modifier
            .weight(weight)
            .fillMaxWidth(1f)
            .wrapContentWidth(), items = tablesList, selectedItem = selectedItem, label = "Outer Table",
            onItemSelected = {
                outerTable = it
                // get the opposite item in the table list to be the inner table
                innerTable = tablesList.get(tablesList.size - tablesList.indexOf(selectedItem.value) -1)
                onItemSelected(outerTable, innerTable)
        })

        Text(text = "⨝", modifier = Modifier.padding(top = 13.dp), style = androidx.compose.ui.text.TextStyle( fontSize = MaterialTheme.typography.titleLarge.fontSize,fontWeight = FontWeight.ExtraBold))

        // Inner Table (S)
        Column(modifier= Modifier
            .weight(weight)
            .fillMaxWidth()) {
            Text("Inner Table", style = androidx.compose.ui.text.TextStyle(fontWeight = FontWeight.SemiBold))
            Text(
                text = innerTable,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.primaryContainer,
                        MaterialTheme.shapes.small
                    )
                    .padding(10.dp)
                    .fillMaxWidth(),
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
        // Drop down to choose buffer size
        CustomDropDown(modifier = Modifier.weight(1F), items = buffersList, selectedItem = mutableStateOf<String>("3"), label = "Buffer Size",
            onItemSelected = {
                bufferSize = it
                onBufferNoChanged(it.toInt())
            })

    }


}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun TableChooserPrev() {
//    TableChooser(tablesList = listOf("Employee", "Project"), selectedItem = rememberSaveable { mutableStateOf("") }, onItemSelected = {}, onBufferSizeChanged = {})
}

@Composable
fun CustomDropDown(
    items: List<String>,
    selectedItem: MutableState<String>,
    label: String,
    modifier: Modifier = Modifier,
    onItemSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier= modifier) {

        if (label != "") {
            Text(
                label, style = androidx.compose.ui.text.TextStyle(
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    1.dp,
                    MaterialTheme.colorScheme.primaryContainer,
                    MaterialTheme.shapes.small
                )
                .clickable { expanded = true }
                .padding(5.dp)

        ) {
            Text(
                modifier = Modifier.padding(5.dp),
                text =selectedItem.value,
                style = androidx.compose.ui.text.TextStyle( fontSize = MaterialTheme.typography.bodyLarge.fontSize,fontWeight = FontWeight.Medium)
            )

            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .clickable { expanded = true }

            )
        }

        androidx.compose.material3.DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }) {
            items.forEach {
                DropdownMenuItem(
                    onClick = {
                        selectedItem.value = it
                        expanded = false
                        onItemSelected(it)
                    },
                    text = { Text(text = it) }
                )
            }
        }
    }
}

@Composable
fun CostItem(key : String, value : String) {
    Row (modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
    ) {

        Text(modifier = Modifier.weight(0.6f),
            text = "$key:" ,style = TextStyle(fontSize = MaterialTheme.typography.bodyLarge.fontSize,fontWeight = FontWeight.Bold))
        Text(modifier = Modifier.weight(0.4f),
            text = "$value" ,style = MaterialTheme.typography.bodyLarge)

    }
}
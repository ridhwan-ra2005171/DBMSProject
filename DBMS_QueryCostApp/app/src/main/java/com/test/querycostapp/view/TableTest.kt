package com.example.dbmsphase2.view

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TableExample() {
    var data by remember { mutableStateOf(listOf(List(3) { "" })) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Header Row
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            DataCell("Header 1")
            DataCell("Header 2")
            DataCell("Header 3")
        }

        // Data Rows
        for (row in data) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                for (cell in row) {
                    DataCell(cell)
                }
            }
        }

        // Add a new row
        Button(
            onClick = {
                data = data.toMutableList().apply { add(List(3) { "" }) }
            }
        ) {
            Text("Add Row")
        }
    }
}

@Composable
fun DataCell(data: String) {
    BasicTextField(
        value = data,
        onValueChange = { /* Handle value change if needed */ },
        modifier = Modifier.border(1.dp, Color.Gray).padding(4.dp)
    )
}

@Preview
@Composable
fun TableExamplePreview() {
    TableExample()
}
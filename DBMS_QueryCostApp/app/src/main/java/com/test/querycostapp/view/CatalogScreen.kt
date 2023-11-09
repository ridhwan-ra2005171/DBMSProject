package com.example.dbmsphase2.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CatalogScreen() {
    Column (modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,) {
    Text(text = "Tables Metadata",
        style= TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
    )

        Text(text = "Employee Table Metadata",
            style= TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        )
        Text(text = "Project Table Metadata",
            style= TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        )
        Text(text = "Index Metadata",
            style= TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        )

    }
}

@Preview
@Composable
fun CatalogScreenPreview() {
    CatalogScreen()
}
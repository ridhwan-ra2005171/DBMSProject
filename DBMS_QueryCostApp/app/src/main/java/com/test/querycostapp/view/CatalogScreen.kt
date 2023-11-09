package com.example.dbmsphase2.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.test.querycostapp.R

@Composable
fun CatalogScreen() {
    LazyColumn(Modifier.fillMaxSize()) {


        item {
            Text(
                text = "Tables Metadata",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            )
            LazyRow(Modifier.fillMaxWidth()) {
                items(1) { // Use items(1) for a single item, or change the count as needed
                    Image(
                        painter = painterResource(id = R.drawable.tables_metadata),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(100.dp)

                    )
                }
            }

            Text(
                text = "Employee Table Metadata",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            )
            LazyRow(Modifier.fillMaxWidth()) {
                items(1) { // Use items(1) for a single item, or change the count as needed
                    Image(
                        painter = painterResource(id = R.drawable.employees_metadata),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(250.dp)

                    )
                }
            }

            Text(
                text = "Project Table Metadata",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            )

            LazyRow(Modifier.fillMaxWidth()) {
                items(1) { // Use items(1) for a single item, or change the count as needed
                    Image(
                        painter = painterResource(id = R.drawable.projects_metadata),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(150.dp)

                    )
                }
            }
            Text(
                text = "Index Metadata",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            )
            LazyRow(Modifier.fillMaxWidth()) {
                items(1) { // Use items(1) for a single item, or change the count as needed
                    Image(
                        painter = painterResource(id = R.drawable.index_metadata),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(170.dp)

                    )
                }
            }

        }
    }
}

@Preview
@Composable
fun CatalogScreenPreview() {
    CatalogScreen()
}
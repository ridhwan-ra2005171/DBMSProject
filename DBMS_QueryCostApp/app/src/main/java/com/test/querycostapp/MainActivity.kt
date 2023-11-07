package com.test.querycostapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.test.querycostapp.repo.EmployeeRepo
import com.test.querycostapp.repo.ProjectRepo
import com.test.querycostapp.ui.theme.QueryCostAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val emps = EmployeeRepo.initEmployees(LocalContext.current)
            Log.d("EMP2", emps.map { it.Fname }.toString())

            val projects = ProjectRepo.initProjects(LocalContext.current)
            Log.d("EMP2", projects.map { it.ProjectName }.toString())

            QueryCostAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting(emps[0].Fname)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
    val emps = EmployeeRepo.initEmployees(LocalContext.current)
    Log.d("EMP2", emps.map { it.Fname }.toString())

    val projects = ProjectRepo.initProjects(LocalContext.current)
    Log.d("EMP2", projects.map { it.ProjectName }.toString())
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
   QueryCostAppTheme {
        Greeting("Android")


    }
}
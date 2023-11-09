@file:OptIn(ExperimentalMaterial3Api::class)

package com.test.querycostapp

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.test.querycostapp.repo.EmployeeRepo
import com.test.querycostapp.repo.ProjectRepo
import com.test.querycostapp.ui.theme.QueryCostAppTheme
import com.test.querycostapp.view.navigation.AppNavigator
import com.test.querycostapp.view.navigation.BottomBar

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
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
                    MainApp(emps[0].Fname)
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainApp(name: String, modifier: Modifier = Modifier) {
    // Nav controller
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomBar(navController)
        }
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(it)) {
//            Text(text = "Hello $name!")
            AppNavigator(navController = navController)

        }
    }

    val emps = EmployeeRepo.initEmployees(LocalContext.current)
    Log.d("EMP2", emps.map { it.Fname }.toString())

    val projects = ProjectRepo.initProjects(LocalContext.current)
    Log.d("EMP2", projects.map { it.ProjectName }.toString())
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
   QueryCostAppTheme {
        MainApp("Android")
    }
}
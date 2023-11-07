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
import com.test.querycostapp.Repo.EmployeeRepo
import com.test.querycostapp.Repo.LanguagePackageRepository
import com.test.querycostapp.ui.theme.QueryCostAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val emps = EmployeeRepo.initEmployees(LocalContext.current)
            Log.d("EMP", emps.map { it.toString() }.toString())

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
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val packages = LanguagePackageRepository.initPackages(LocalContext.current)
    Log.d("PAC", "GreetingPreview: ${packages.map { it.title }}")
    QueryCostAppTheme {
        Greeting("Android: ${packages[0].title}")


    }
}
@file:OptIn(ExperimentalMaterial3Api::class)

package com.test.querycostapp.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.dbmsphase2.view.CatalogScreen
import com.example.dbmsphase2.view.QueryScreen2
import com.test.querycostapp.view.navigation.NavDestinations

@Composable
fun TopBar(navController: NavHostController) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val bottomBars = listOf(NavDestinations.SelectCostEstimator, NavDestinations.JoinCostEstimator)

    BottomAppBar(containerColor = MaterialTheme.colorScheme.background) {
        bottomBars.forEach {
            NavigationBarItem(
                modifier = Modifier.height(56.dp).then(
                    if (currentRoute == it.route) { Modifier.background(MaterialTheme.colorScheme.primary, MaterialTheme.shapes.large) } else { Modifier }
                ),
                selected = currentRoute == it.route,
                onClick = { navController.navigate(it.route){
                    // this pop up to with saveState is used for saving the state of the screen when we navigate to another screen
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                } },
                icon = { },
                label = { Text(text = it.title, style =  MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold) }
            )
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CostScreen(navController: NavHostController) {

    Scaffold(
        topBar = {
            TopBar(navController = navController)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.Start
        ) {
            AppNavigator2(navController = navController)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigator2(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavDestinations.SelectCostEstimator.route) {

        // Cost Estimator Screen
        composable(NavDestinations.SelectCostEstimator.route) {
            QueryScreen2()
        }

        // Metadata Screen
        composable(NavDestinations.JoinCostEstimator.route) {
            JoinScreen()
        }

    }
}
//     Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.spacedBy(10.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Button(
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = Color.Transparent,
//                        contentColor = MaterialTheme.colorScheme.primary
//                    ),
//                    onClick = { toggleScreen = "select" },
//                    modifier = Modifier
//                        .weight(1f)
//                        .background(
//                            MaterialTheme.colorScheme.primaryContainer,
//                            MaterialTheme.shapes.large
//                        )
//                        .then(
//                            if (toggleScreen == "select")
//                                Modifier.border(
//                                    width = 2.dp,
//                                    shape = MaterialTheme.shapes.large,
//                                    color = MaterialTheme.colorScheme.primary
//                                )
//                            else
//                                Modifier
//                        )
//                ) {
//                    Text(text = "SELECT COST")
//                }
//
//                Button(
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = Color.Transparent,
//                        contentColor = MaterialTheme.colorScheme.primary
//                    ),
////                colors = ButtonDefaults.buttonColors(
////                    containerColor =
////                    if (toggleScreen == "join") MaterialTheme.colorScheme.primary
////                    else MaterialTheme.colorScheme.onSurface
////                ),
//                    onClick = { toggleScreen = "join" },
//                    modifier = Modifier
//                        .weight(1f)
//                        .background(
//                            MaterialTheme.colorScheme.primaryContainer,
//                            MaterialTheme.shapes.large
//                        )
//                        .then(
//                            if (toggleScreen == "join")
//                                Modifier.border(
//                                    width = 2.dp,
//                                    shape = MaterialTheme.shapes.large,
//                                    color = MaterialTheme.colorScheme.primary
//                                )
//                            else
//                                Modifier
//                        )
//                ) {
//                    Text(text = "JOIN COST")
//                }
//            }
//            // Display the screens here
//            if (toggleScreen == "select") {
//                QueryScreen2()
//            } else {
//                JoinScreen()
//            }
//

@Preview
@Composable
fun CostScreenPreview() {
    val navController = rememberNavController()
    CostScreen(navController = navController)
}
package com.test.querycostapp.view.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomBar(navController: NavHostController) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val bottomBars = listOf(NavDestinations.CostEstimator, NavDestinations.Catalog)

    BottomAppBar {
        bottomBars.forEach {
            NavigationBarItem(
                selected = currentRoute == it.route,
                onClick = { navController.navigate(it.route){
                    launchSingleTop = true
                } },
                icon = {
                    Icon(
                        imageVector = it.icon ?: Icons.Default.BrokenImage,
                        contentDescription = it.title
                    )
                },
                label = { Text(text = it.title) }
            )
        }
    }
}

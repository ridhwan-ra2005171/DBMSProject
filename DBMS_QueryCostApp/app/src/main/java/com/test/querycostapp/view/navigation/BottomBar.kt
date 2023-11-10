package com.test.querycostapp.view.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomBar(navController: NavHostController) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val bottomBars = listOf(NavDestinations.CostScreen, NavDestinations.Catalog)

    BottomAppBar {
        bottomBars.forEach {
            NavigationBarItem(

                selected = currentRoute == it.route,
                onClick = { navController.navigate(it.route){
                    // this pop up to wuth saveState is used for saving the state of the screen when we navigate to another screen
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
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

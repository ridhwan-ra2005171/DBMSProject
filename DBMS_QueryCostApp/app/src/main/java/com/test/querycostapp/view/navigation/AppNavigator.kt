package com.test.querycostapp.view.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dbmsphase2.view.CatalogScreen
import com.example.dbmsphase2.view.QueryScreen2
import com.test.querycostapp.view.CostScreen


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigator(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavDestinations.CostScreen.route) {

        // Cost Estimator Screen
        composable(NavDestinations.CostScreen.route) {
            CostScreen(navController = rememberNavController())
        }

        // Metadata Screen
        composable(NavDestinations.Catalog.route) {
            CatalogScreen()
        }

    }
}
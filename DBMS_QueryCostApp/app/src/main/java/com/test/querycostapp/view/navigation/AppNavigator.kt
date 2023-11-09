package com.test.querycostapp.view.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.dbmsphase2.view.CatalogScreen
import com.example.dbmsphase2.view.QueryScreen2


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigator(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavDestinations.CostEstimator.route) {

        // Query Screen
        composable(NavDestinations.CostEstimator.route) {
            QueryScreen2()
        }

        // Metadata Screen
        composable(NavDestinations.Catalog.route) {
            CatalogScreen()
        }

    }
}
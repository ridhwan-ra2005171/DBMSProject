package com.test.querycostapp.view.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.TableChart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavDestinations(val route: String, val title: String, val icon: ImageVector? = null) {
    object CostScreen : NavDestinations("costEstimator", "Cost Estimator", icon = Icons.Default.MonetizationOn)
    object SelectCostEstimator : NavDestinations("selectCostEstimator", "SELECTION Cost")
    object JoinCostEstimator : NavDestinations("joinCostEstimator", "JOIN Cost")

    object Catalog : NavDestinations("catalog", "Catalog", icon = Icons.Default.TableChart)
}
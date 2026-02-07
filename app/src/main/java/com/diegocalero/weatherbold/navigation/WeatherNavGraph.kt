package com.diegocalero.weatherbold.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.diegocalero.weatherbold.presentation.detail.DetailScreen
import com.diegocalero.weatherbold.presentation.search.SearchScreen
import com.diegocalero.weatherbold.presentation.splash.SplashScreen

@Composable
fun WeatherNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Route.Splash.route
    ) {
        composable(route = Route.Splash.route) {
            SplashScreen(
                onSplashFinished = {
                    navController.navigate(Route.Search.route) {
                        popUpTo(Route.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        composable(route = Route.Search.route) {
            SearchScreen(
                onLocationClick = { location ->
                    navController.navigate(
                        Route.Detail.createRouteByLatLng(location)
                    )
                }
            )
        }

        composable(
            route = Route.Detail.route,
            arguments = listOf(
                navArgument("query") { type = NavType.StringType },
                navArgument("locationName") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val query = backStackEntry.arguments?.getString("query").orEmpty()
            val locationName = backStackEntry.arguments?.getString("locationName").orEmpty()
            DetailScreen(
                locationName = locationName,
                query = query,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
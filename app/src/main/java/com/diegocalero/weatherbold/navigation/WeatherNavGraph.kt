package com.diegocalero.weatherbold.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
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
            // TODO: SearchScreen
        }

        composable(
            route = Route.Detail.route,
            arguments = listOf(
                navArgument("query") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val query = backStackEntry.arguments?.getString("query").orEmpty()
            // TODO: DetailScreen(query)
        }
    }
}
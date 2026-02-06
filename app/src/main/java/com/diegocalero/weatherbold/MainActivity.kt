package com.diegocalero.weatherbold

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.diegocalero.weatherbold.core.ui.theme.WeatherBoldTheme
import com.diegocalero.weatherbold.navigation.WeatherNavGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherBoldTheme {
                val navController = rememberNavController()
                WeatherNavGraph(navController = navController)
            }
        }
    }
}
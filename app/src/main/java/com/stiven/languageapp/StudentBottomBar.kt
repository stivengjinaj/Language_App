package com.stiven.languageapp

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

/**
 * Custom bottom bar for StudentPanel.
 *
 * @param navController a navigation controller inside StudentPanel navigation graph.
 * */
@Composable
fun StudentBottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreens.Lessons,
        BottomBarScreens.Exercises,
        BottomBarScreens.Dictionary,
        BottomBarScreens.Logout,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentSize = LocalConfiguration.current.screenWidthDp
    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp, 20.dp, 0.dp, 0.dp))
            .height((currentSize / 6 + 8).dp)
    ){
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}
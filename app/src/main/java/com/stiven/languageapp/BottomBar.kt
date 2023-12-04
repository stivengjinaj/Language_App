package com.stiven.languageapp

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomBar(navController: NavHostController){
    val screens = listOf(
        BottomBarScreen.MyCourses,
        BottomBarScreen.NewCourse,
        BottomBarScreen.Settings,
        BottomBarScreen.Emergency,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentSize = LocalConfiguration.current.screenWidthDp
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .clip(RoundedCornerShape(25.dp, 25.dp, 0.dp, 0.dp))
            .height((currentSize / 6 + 10).dp),
        content = {
            screens.forEach { screen ->
                AddItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        }
    )
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    val context = LocalContext.current
    val currentSize = LocalConfiguration.current.screenWidthDp
    BottomNavigationItem(
        label = {
            Text(
                text = context.getString(screen.title),
                style = TextStyle(
                    color = Color.White,
                    fontSize = (currentSize/12-22).sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    lineBreak = LineBreak.Simple
                )
            )
        },
        selectedContentColor = if(screen == BottomBarScreen.Emergency) Color.White else Color.LightGray,
        unselectedContentColor = if(screen == BottomBarScreen.Emergency) MaterialTheme.colorScheme.error else Color.LightGray,
        icon = {
            Icon(
                modifier = Modifier.size((currentSize/10).dp).padding(0.dp,0.dp,0.dp,5.dp),
                painter = painterResource(id = screen.icon),
                contentDescription = context.getString(screen.title),
                tint = if(screen == BottomBarScreen.Emergency) MaterialTheme.colorScheme.error else Color.White
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}

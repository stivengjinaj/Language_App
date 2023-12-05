package com.stiven.languageapp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Settings
import br.com.frazo.compose_resources.IconResource

/**
 * Sealed class that creates the routes of the bottom bar
 *
 * @param route Route path
 * @param title Route name
 * @param icon Route icon
 * */
sealed class BottomBarScreen(
    val route: String,
    val title: Int,
    val icon: Int
) {
    data object MyCourses : BottomBarScreen(
        "my_courses",
        R.string.my_courses,
        R.drawable.my_courses
    )

    data object NewCourse : BottomBarScreen(
        "new_course",
        R.string.new_course,
        R.drawable.new_course
    )

    data object Settings : BottomBarScreen(
        "settings",
        R.string.settings,
        R.drawable.settings
    )

    data object Emergency : BottomBarScreen(
        "emergency",
        R.string.emergency,
        R.drawable.emergency_dictionary
    )
}
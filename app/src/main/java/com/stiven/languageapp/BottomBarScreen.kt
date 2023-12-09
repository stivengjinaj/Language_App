package com.stiven.languageapp

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
    data object Classroom : BottomBarScreen(
        "classroom",
        R.string.classroom,
        R.drawable.classroom
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
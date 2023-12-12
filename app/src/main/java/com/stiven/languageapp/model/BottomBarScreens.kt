package com.stiven.languageapp.model

import com.stiven.languageapp.R

/**
 * Sealed class that creates the routes of the bottom bar
 *
 * @param route Route path
 * @param title Route name
 * @param icon Route icon
 * */
sealed class BottomBarScreens(
    val route: String,
    val title: Int,
    val icon: Int
) {
    data object Classroom : BottomBarScreens(
        "classroom",
        R.string.classroom,
        R.drawable.classroom
    )

    data object NewCourse : BottomBarScreens(
        "new_course",
        R.string.new_course,
        R.drawable.new_course
    )

    data object Settings : BottomBarScreens(
        "settings",
        R.string.settings,
        R.drawable.settings
    )

    data object Emergency : BottomBarScreens(
        "emergency",
        R.string.emergency,
        R.drawable.emergency_dictionary
    )

    data object Lessons: BottomBarScreens(
        "lessons",
        R.string.lessons,
        R.drawable.lessons
    )

    data object Exercises: BottomBarScreens(
        "exercises",
        R.string.exercises,
        R.drawable.exercises
    )

    data object Dictionary: BottomBarScreens(
        "dictionary",
        R.string.dictionary,
        R.drawable.dictionary
    )

    data object Logout: BottomBarScreens(
        "logout",
        R.string.logout,
        R.drawable.logout
    )
}
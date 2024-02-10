package com.stiven.languageapp.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val lightColorScheme = lightColorScheme(
    primary = mainColorLight,
    background = backgroundColorLight,
    //FONT COLOR
    secondary = fontColor,
    //ICONS
    onSecondary = mainColorLight,
    //SELECT OPTION COLOR
    tertiary = selectColor,
    //RED COLOR
    error = emergency,
    //Blue font in light/White font in dark
    inversePrimary = mainColorLight
)

private val darkColorScheme = darkColorScheme(
    primary = mainColorDark,
    background = backgroundColorDark,
    //FONT COLOR
    secondary = fontColor,
    //ICONS
    onSecondary = fontColor,
    //SELECT OPTION COLOR
    tertiary = selectColorDark,
    //RED COLOR
    error = emergency,
    //Blue font in light/White font in dark
    inversePrimary = backgroundColorLight
)

@Composable
fun LanguageAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> darkColorScheme
        else -> lightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
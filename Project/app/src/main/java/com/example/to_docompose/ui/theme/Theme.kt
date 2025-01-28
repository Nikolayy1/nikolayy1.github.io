package com.example.to_docompose.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView

private val LightColors = lightColorScheme(
    primary = Custom_dark_blue,
    onPrimary = Custom_white,
    background = Custom_white_background,
    surface = Custom_white_background
)

@Composable
fun ToDoComposeTheme(
    content: @Composable () -> Unit
) {
    val colors = LightColors

    val view = LocalView.current
    val colorPrimary = colors.primary
    val colorSurface = colors.surface

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorPrimary.toArgb()
            window.navigationBarColor = colorSurface.toArgb()
        }
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
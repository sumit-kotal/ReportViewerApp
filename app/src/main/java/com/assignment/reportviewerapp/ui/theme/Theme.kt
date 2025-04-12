package com.assignment.reportviewerapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview

// Light theme
private val LightColorPalette = lightColorScheme(
    primary = PrimaryColor,
    primaryContainer = PrimaryVariantColor,
    background = BackgroundColor,
    onPrimary = OnPrimaryColor
)

// Dark theme
private val DarkColorPalette = darkColorScheme(
    primary = DarkPrimaryColor,
    primaryContainer = DarkPrimaryVariantColor,
    background = DarkBackgroundColor,
    onPrimary = OnDarkPrimaryColor
)


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ReportViewerAppTheme {

    }
}


@Composable
fun ReportViewerAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorPalette
        else -> LightColorPalette
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
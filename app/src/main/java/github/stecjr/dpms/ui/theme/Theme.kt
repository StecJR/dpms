package github.stecjr.dpms.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    background = Color(0xff090023),
    onBackground = Color(0xffefefef),
    tertiaryContainer = Color(0xff0c0c73),
    onTertiaryContainer = Color(0xffffc377),
    error = Color(0xffb91333),
    onError = Color(0xFFFDA6A6),
    errorContainer = Color(0xff730e13),
)

private val LightColorScheme = lightColorScheme(
    background = Color(0xffececf7),
    onBackground = Color(0xff000000),
    tertiaryContainer = Color(0xffbce3f7),
    onTertiaryContainer = Color(0xffca3f0c),
    error = Color(0xffb91333),
    onError = Color(0xFFFDA6A6),
    errorContainer = Color(0xff730e13),
)

@Composable
fun DPMSTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme, typography = Typography, content = content
    )
}
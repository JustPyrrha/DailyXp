package gay.pyrrha.dailyxp.core.design.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import gay.pyrrha.dailyxp.core.model.data.Theme
import gay.pyrrha.dailyxp.core.util.supportsDynamicTheming


@Composable
fun XpTheme(
    theme: Theme = Theme.DYNAMIC,
    darkTheme: Boolean = isSystemInDarkTheme(),
    amoledDark: Boolean = false,
    content: @Composable () -> Unit,
) {
    var colourScheme = when (theme) {
        Theme.DEFAULT -> {
            if (darkTheme) {
                DarkDefaultColourScheme
            } else {
                LightDefaultColourScheme
            }
        }
        Theme.DYNAMIC -> {
            if(supportsDynamicTheming()) {
                val context = LocalContext.current
                if (darkTheme) {
                    dynamicDarkColorScheme(context)
                } else {
                    dynamicLightColorScheme(context)
                }
            } else {
                if (darkTheme) {
                    DarkDefaultColourScheme
                } else {
                    LightDefaultColourScheme
                }
            }
        }
        // todo: add more catppuccin variants accented with different palette colours
        Theme.LATTE -> Latte.asColorScheme(Latte.rosewater)
        Theme.FRAPPE -> Frappe.asColorScheme(Frappe.rosewater)
        Theme.MACCHIATO -> Macchiato.asColorScheme(Macchiato.rosewater)
        Theme.MOCHA -> Mocha.asColorScheme(Mocha.rosewater)

        Theme.TRANS_PRIDE -> if (darkTheme) {
            TransPrideDarkColours
        } else {
            TransPrideLightColours
        }
    }

    if (darkTheme && amoledDark) {
        colourScheme = colourScheme.copy(
            background = Color.Black,
            surface = Color.Black
        )
    }

    MaterialTheme(
        colorScheme = colourScheme,
        content = content
    )
}

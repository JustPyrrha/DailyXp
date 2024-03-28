package gay.pyrrha.dailyxp.core.ui.widget.preference


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import gay.pyrrha.dailyxp.core.design.theme.XpTheme
import gay.pyrrha.dailyxp.core.model.data.Theme
import gay.pyrrha.dailyxp.core.model.data.ThemeMode
import gay.pyrrha.dailyxp.core.ui.DevicePreviews
import gay.pyrrha.dailyxp.core.design.stringResource
import gay.pyrrha.dailyxp.i18n.MR

private val options = mapOf(
    ThemeMode.SYSTEM to MR.strings.theme_system,
    ThemeMode.LIGHT to MR.strings.theme_light,
    ThemeMode.DARK to MR.strings.theme_dark
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeModePreferenceWidget(
    value: ThemeMode,
    enabled: Boolean,
    onItemClick: (ThemeMode) -> Unit
) {
    PreferenceWidget(
        subcomponent = {
            MultiChoiceSegmentedButtonRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = PrefsHorizontalPadding),
            ) {
                options.onEachIndexed { index, (mode, labelRes) ->
                    SegmentedButton(
                        checked = mode == value,
                        onCheckedChange = { onItemClick(mode) },
                        shape = SegmentedButtonDefaults.itemShape(
                            index = index,
                            count = options.size
                        ),
                        enabled = enabled
                    ) {
                        Text(text = stringResource(labelRes))
                    }
                }
            }
        }
    )
}

@Composable
@DevicePreviews
private fun ThemeModePreferenceWidgetPreview() {
    XpTheme(theme = Theme.MOCHA) {
        ThemeModePreferenceWidget(value = ThemeMode.SYSTEM, true) { }
    }
}

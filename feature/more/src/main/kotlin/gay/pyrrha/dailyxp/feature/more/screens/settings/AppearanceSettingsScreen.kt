package gay.pyrrha.dailyxp.feature.more.screens.settings

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import gay.pyrrha.dailyxp.core.design.component.XpTopAppBar
import gay.pyrrha.dailyxp.core.design.icon.XpIcons
import gay.pyrrha.dailyxp.core.design.stringResource
import gay.pyrrha.dailyxp.core.model.data.Theme
import gay.pyrrha.dailyxp.core.model.data.ThemeMode
import gay.pyrrha.dailyxp.core.model.data.allowsModeChange
import gay.pyrrha.dailyxp.core.ui.widget.preference.PreferenceGroupHeaderWidget
import gay.pyrrha.dailyxp.core.ui.widget.preference.SwitchPreferenceWidget
import gay.pyrrha.dailyxp.core.ui.widget.preference.ThemeModePreferenceWidget
import gay.pyrrha.dailyxp.core.ui.widget.preference.XpThemesListWidget
import gay.pyrrha.dailyxp.i18n.MR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppearanceSettingsScreen(
    onBackClick: () -> Unit,
   viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.settingsUiState.collectAsStateWithLifecycle()

    when (uiState) {
        SettingsUiState.Loading -> {}
        is SettingsUiState.Success -> {
            Scaffold(
                topBar = {
                    XpTopAppBar(
                        title = MR.strings.pref_category_appearance,
                        navigationIcon = XpIcons.BackArrow,
                        onNavigationIconClick = onBackClick,
                    )
                }
            ) { padding ->
                Box(
                    modifier = Modifier
                        .padding(padding)
                ) {
                    ThemeGroup(
                        settings = (uiState as SettingsUiState.Success).settings,
                        onThemeModeClick = viewModel::updateThemeMode,
                        onThemeClick = viewModel::updateTheme,
                        onAmoledDarkChanged = viewModel::updateAmoledDark
                    )
                }
            }
        }
    }
}

@Composable
private fun ThemeGroup(
    settings: UserEditableSettings,
    onThemeModeClick: (ThemeMode) -> Unit,
    onThemeClick: (Theme) -> Unit,
    onAmoledDarkChanged: (Boolean) -> Unit,
) {
    Column {
        PreferenceGroupHeaderWidget(title = MR.strings.pref_category_theme)
        ThemeModePreferenceWidget(
            value = settings.themeMode,
            enabled = settings.theme.allowsModeChange(),
            onItemClick = onThemeModeClick
        )
        XpThemesListWidget(
            value = settings.theme,
            themeMode = settings.themeMode,
            onItemClick = onThemeClick
        )
        SwitchPreferenceWidget(
            title = stringResource(resource = MR.strings.pref_amoled_dark_mode),
            checked = settings.amoledDark,
            onCheckedChange = onAmoledDarkChanged,
            enabled = if (settings.themeMode == ThemeMode.SYSTEM) {
                isSystemInDarkTheme()
            } else {
                settings.themeMode == ThemeMode.DARK
            }
        )
    }
}

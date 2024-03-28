package gay.pyrrha.dailyxp.feature.onboarding

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import gay.pyrrha.dailyxp.core.model.data.Theme
import gay.pyrrha.dailyxp.core.model.data.ThemeMode
import gay.pyrrha.dailyxp.core.model.data.allowsModeChange
import gay.pyrrha.dailyxp.core.ui.widget.preference.ThemeModePreferenceWidget
import gay.pyrrha.dailyxp.core.ui.widget.preference.XpThemesListWidget

class ThemeStep : OnboardingStep {
    override val isComplete: Boolean = true

    @Composable
    override fun Content(
        onboardingUiState: OnboardingUiState,
        onChangeThemeMode: (ThemeMode) -> Unit,
        onChangeTheme: (Theme) -> Unit
    ) {
        Column {
            when(onboardingUiState) {
                OnboardingUiState.Loading -> {

                }
                is OnboardingUiState.Success -> {
                    ThemeModePreferenceWidget(
                        value = onboardingUiState.settings.themeMode,
                        onItemClick = { onChangeThemeMode(it) },
                        enabled = onboardingUiState.settings.theme.allowsModeChange()
                    )
                    XpThemesListWidget(
                        value = onboardingUiState.settings.theme,
                        themeMode = onboardingUiState.settings.themeMode,
                        onItemClick = { onChangeTheme(it) }
                    )
                }
            }
        }
    }
}

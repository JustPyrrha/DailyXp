package gay.pyrrha.dailyxp.feature.onboarding

import androidx.compose.runtime.Composable
import gay.pyrrha.dailyxp.core.model.data.Theme
import gay.pyrrha.dailyxp.core.model.data.ThemeMode

interface OnboardingStep {
    val isComplete: Boolean

    @Composable
    fun Content(
        onboardingUiState: OnboardingUiState,
        onChangeThemeMode: (ThemeMode) -> Unit,
        onChangeTheme: (Theme) -> Unit
    )
}

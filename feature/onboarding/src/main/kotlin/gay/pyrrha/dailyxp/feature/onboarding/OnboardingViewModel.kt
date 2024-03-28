package gay.pyrrha.dailyxp.feature.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gay.pyrrha.dailyxp.core.data.repository.UserDataRepository
import gay.pyrrha.dailyxp.core.model.data.Theme
import gay.pyrrha.dailyxp.core.model.data.ThemeMode
import gay.pyrrha.dailyxp.feature.onboarding.OnboardingUiState.*
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class OnboardingScreenViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository,
): ViewModel() {
    val onboardingUiState: StateFlow<OnboardingUiState> =
        userDataRepository.userData
            .map { userData ->
                Success(
                    settings = OnboardingEditableSettings(
                        theme = userData.theme,
                        themeMode = userData.themeMode
                    )
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5.seconds.inWholeMilliseconds),
                initialValue = Loading
            )

    fun updateThemeMode(themeMode: ThemeMode) {
        viewModelScope.launch {
            userDataRepository.setThemeMode(themeMode)
        }
    }

    fun updateTheme(theme: Theme) {
        viewModelScope.launch {
            userDataRepository.setTheme(theme)

            when (theme) {
                Theme.LATTE ->
                    userDataRepository.setThemeMode(ThemeMode.LIGHT)
                Theme.FRAPPE,
                Theme.MACCHIATO,
                Theme.MOCHA ->
                    userDataRepository.setThemeMode(ThemeMode.DARK)
                else -> {}
            }
        }
    }
}

data class OnboardingEditableSettings(
    val theme: Theme,
    val themeMode: ThemeMode
)

sealed interface OnboardingUiState {
    data object Loading : OnboardingUiState
    data class Success(val settings: OnboardingEditableSettings) : OnboardingUiState
}

package gay.pyrrha.dailyxp.feature.more.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gay.pyrrha.dailyxp.core.data.repository.UserDataRepository
import gay.pyrrha.dailyxp.core.model.data.Theme
import gay.pyrrha.dailyxp.core.model.data.ThemeMode
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository
): ViewModel() {
    val settingsUiState: StateFlow<SettingsUiState> =
        userDataRepository.userData
            .map { userData ->
                SettingsUiState.Success(
                    settings = UserEditableSettings(
                        theme = userData.theme,
                        themeMode = userData.themeMode,
                        amoledDark = userData.amoledDark
                    )
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5.seconds.inWholeMilliseconds),
                initialValue = SettingsUiState.Loading
            )

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

    fun updateThemeMode(themeMode: ThemeMode) {
        viewModelScope.launch {
            userDataRepository.setThemeMode(themeMode)
        }
    }

    fun updateAmoledDark(amoledDark: Boolean) {
        viewModelScope.launch {
            userDataRepository.setAmoledDark(amoledDark)
        }
    }
}

data class UserEditableSettings(
    val theme: Theme,
    val themeMode: ThemeMode,
    val amoledDark: Boolean
)

sealed interface SettingsUiState {
    data object Loading : SettingsUiState
    data class Success(val settings: UserEditableSettings) : SettingsUiState
}

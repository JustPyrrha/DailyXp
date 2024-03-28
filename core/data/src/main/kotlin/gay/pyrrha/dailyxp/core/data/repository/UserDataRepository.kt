package gay.pyrrha.dailyxp.core.data.repository

import gay.pyrrha.dailyxp.core.model.data.Theme
import gay.pyrrha.dailyxp.core.model.data.ThemeMode
import gay.pyrrha.dailyxp.core.model.data.UserData
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {
    /**
     * Stream of [UserData]
     */
    val userData: Flow<UserData>

    /**
     * Sets the desired theme.
     */
    suspend fun setTheme(theme: Theme)

    /**
     * Sets the desired theme mode.
     */
    suspend fun setThemeMode(themeMode: ThemeMode)

    /**
     * Sets whether the user has completed the onboarding process.
     */
    suspend fun setHideOnboarding(hideOnboarding: Boolean)

    suspend fun setAmoledDark(amoledDark: Boolean)
}

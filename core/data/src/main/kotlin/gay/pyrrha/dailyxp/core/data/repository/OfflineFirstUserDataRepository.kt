package gay.pyrrha.dailyxp.core.data.repository

import gay.pyrrha.dailyxp.core.datastore.XpPreferencesDataSource
import gay.pyrrha.dailyxp.core.model.data.Theme
import gay.pyrrha.dailyxp.core.model.data.ThemeMode
import gay.pyrrha.dailyxp.core.model.data.UserData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class OfflineFirstUserDataRepository @Inject constructor(
    private val xpPreferencesDataSource: XpPreferencesDataSource,
) : UserDataRepository {
    override val userData: Flow<UserData> =
        xpPreferencesDataSource.userData

    override suspend fun setTheme(theme: Theme) =
        xpPreferencesDataSource.setTheme(theme)

    override suspend fun setThemeMode(themeMode: ThemeMode) =
        xpPreferencesDataSource.setThemeMode(themeMode)

    override suspend fun setHideOnboarding(hideOnboarding: Boolean) =
        xpPreferencesDataSource.setHideOnboarding(hideOnboarding)

    override suspend fun setAmoledDark(amoledDark: Boolean) =
        xpPreferencesDataSource.setAmoledDark(amoledDark)
}

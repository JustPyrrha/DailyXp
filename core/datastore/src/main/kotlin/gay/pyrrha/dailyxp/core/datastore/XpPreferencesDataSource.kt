package gay.pyrrha.dailyxp.core.datastore

import androidx.datastore.core.DataStore
import gay.pyrrha.dailyxp.core.model.data.Theme
import gay.pyrrha.dailyxp.core.model.data.ThemeMode
import gay.pyrrha.dailyxp.core.model.data.UserData
import gay.pyrrha.dailyxp.core.util.supportsDynamicTheming
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class XpPreferencesDataSource @Inject constructor(
    private val userPreferences: DataStore<UserPreferences>
) {
    val userData = userPreferences.data
        .map {
            UserData(
                hideOnboarding = it.hideOnboarding,
                themeMode = when (it.themeMode) {
                    null,
                    ThemeModeProto.UNRECOGNIZED,
                    ThemeModeProto.THEME_MODE_UNSPECIFIED,
                    ThemeModeProto.THEME_MODE_SYSTEM
                    -> ThemeMode.SYSTEM

                    ThemeModeProto.THEME_MODE_LIGHT -> ThemeMode.LIGHT
                    ThemeModeProto.THEME_MODE_DARK -> ThemeMode.DARK
                },
                theme = when (it.theme) {
                    null,
                    ThemeProto.UNRECOGNIZED,
                    ThemeProto.THEME_UNSPECIFIED,
                    ThemeProto.THEME_DEFAULT,
                    -> Theme.DEFAULT

                    ThemeProto.THEME_DYNAMIC -> {
                        if (supportsDynamicTheming()) {
                            Theme.DYNAMIC
                        } else {
                            Theme.DEFAULT
                        }
                    }
                    ThemeProto.THEME_LATTE -> Theme.LATTE
                    ThemeProto.THEME_FRAPPE -> Theme.FRAPPE
                    ThemeProto.THEME_MACCHIATO -> Theme.MACCHIATO
                    ThemeProto.THEME_MOCHA -> Theme.MOCHA

                    ThemeProto.THEME_TRANS_PRIDE -> Theme.TRANS_PRIDE
                },
                amoledDark = it.amoledDark
            )
        }

    suspend fun setTheme(theme: Theme) {
        userPreferences.updateData {
            it.copy {
                this.theme = when (theme) {
                    Theme.DEFAULT -> ThemeProto.THEME_DEFAULT
                    Theme.TRANS_PRIDE -> ThemeProto.THEME_TRANS_PRIDE
                    Theme.DYNAMIC -> ThemeProto.THEME_DYNAMIC

                    Theme.LATTE -> ThemeProto.THEME_LATTE
                    Theme.FRAPPE -> ThemeProto.THEME_FRAPPE
                    Theme.MACCHIATO -> ThemeProto.THEME_MACCHIATO
                    Theme.MOCHA -> ThemeProto.THEME_MOCHA
                }
            }
        }
    }

    suspend fun setThemeMode(themeMode: ThemeMode) {
        userPreferences.updateData {
            it.copy {
                this.themeMode = when (themeMode) {
                    ThemeMode.SYSTEM -> ThemeModeProto.THEME_MODE_SYSTEM
                    ThemeMode.LIGHT -> ThemeModeProto.THEME_MODE_LIGHT
                    ThemeMode.DARK -> ThemeModeProto.THEME_MODE_DARK
                }
            }
        }
    }

    suspend fun setHideOnboarding(hideOnboarding: Boolean) {
        userPreferences.updateData {
            it.copy {
                this.hideOnboarding = hideOnboarding
            }
        }
    }

    suspend fun setAmoledDark(amoledDark: Boolean) {
        userPreferences.updateData {
            it.copy {
                this.amoledDark = amoledDark
            }
        }
    }
}

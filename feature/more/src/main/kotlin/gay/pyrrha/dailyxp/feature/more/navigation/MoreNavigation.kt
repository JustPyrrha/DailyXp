package gay.pyrrha.dailyxp.feature.more.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Text
import androidx.compose.ui.platform.LocalContext
import androidx.core.text.htmlEncode
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.mikepenz.aboutlibraries.Libs
import com.mikepenz.aboutlibraries.util.withContext
import gay.pyrrha.dailyxp.feature.more.screens.MoreScreen
import gay.pyrrha.dailyxp.feature.more.screens.about.AboutScreen
import gay.pyrrha.dailyxp.feature.more.screens.about.OpenSourceLibraryLicenseScreen
import gay.pyrrha.dailyxp.feature.more.screens.about.OpenSourceLicensesScreen
import gay.pyrrha.dailyxp.feature.more.screens.settings.AppearanceSettingsScreen
import gay.pyrrha.dailyxp.feature.more.screens.settings.SettingsScreen

const val MORE_ROUTE = "more"
const val MORE_OVERVIEW_ROUTE = "more/overview"

const val SETTINGS_ROUTE = "more/settings"
const val SETTINGS_OVERVIEW_ROUTE = "more/settings/overview"
const val APPEARANCE_ROUTE = "more/settings/appearance"

const val ABOUT_ROUTE = "more/about"
const val ABOUT_OVERVIEW_ROUTE = "more/about/overview"
const val LICENSES_ROUTE = "more/about/licenses"

fun NavController.navigateToMore(navOptions: NavOptions) =
    navigate(MORE_ROUTE, navOptions)

fun NavGraphBuilder.moreScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    appVersion: String,
    appVersionCode: String
) {
    navigation(startDestination = MORE_OVERVIEW_ROUTE, route = MORE_ROUTE) {
        composable(route = MORE_OVERVIEW_ROUTE) {
            MoreScreen(
                paddingValues = paddingValues,
                onSettingsClick = { navController.navigate(SETTINGS_ROUTE) },
                onAboutClick = { navController.navigate(ABOUT_ROUTE) }
            )
        }

        navigation(startDestination = SETTINGS_OVERVIEW_ROUTE, route = SETTINGS_ROUTE) {
            composable(route = SETTINGS_OVERVIEW_ROUTE) {
                SettingsScreen(
                    onBackClick = { navController.popBackStack() },
                    onAppearanceClick = { navController.navigate(APPEARANCE_ROUTE) }
                )
            }
            composable(route = APPEARANCE_ROUTE) {
                AppearanceSettingsScreen(
                    onBackClick = { navController.popBackStack() }
                )
            }
        }

        navigation(startDestination = ABOUT_OVERVIEW_ROUTE, route = ABOUT_ROUTE) {
            composable(route = ABOUT_OVERVIEW_ROUTE) {
                AboutScreen(
                    appVersion = appVersion,
                    appVersionCode = appVersionCode,
                    onBackClick = { navController.popBackStack() },
                    onLicensesClick = { navController.navigate(LICENSES_ROUTE) }
                )
            }

            composable(route = LICENSES_ROUTE) {
                OpenSourceLicensesScreen(
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}

package gay.pyrrha.dailyxp.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.util.trace
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import gay.pyrrha.dailyxp.feature.more.navigation.MORE_OVERVIEW_ROUTE
import gay.pyrrha.dailyxp.feature.more.navigation.navigateToMore
import gay.pyrrha.dailyxp.feature.quests.navigation.QUESTS_OVERVIEW_ROUTE
import gay.pyrrha.dailyxp.feature.quests.navigation.navigateToQuests
import gay.pyrrha.dailyxp.navigation.TopLevelDestination
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberXpAppState(
    windowSizeClass: WindowSizeClass,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController()
): XpAppState =
    remember(
        navController,
        coroutineScope,
        windowSizeClass,
    ) {
        XpAppState(
            navController = navController,
            coroutineScope = coroutineScope,
            windowSizeClass = windowSizeClass,
        )
    }

@Stable
class XpAppState(
    val navController: NavHostController,
    coroutineScope: CoroutineScope,
    val windowSizeClass: WindowSizeClass,
//    networkMonitor: NetworkMonitor
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when (currentDestination?.route) {
            QUESTS_OVERVIEW_ROUTE -> TopLevelDestination.Quests
            MORE_OVERVIEW_ROUTE -> TopLevelDestination.More
            else -> null
        }

    val shouldShowBottomBar: Boolean
        get() = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact

    val shouldShowNavRail: Boolean
        get() = !shouldShowBottomBar

    val shouldHideBars: Boolean
        @Composable get() = when (currentTopLevelDestination) {
            TopLevelDestination.Quests,
            TopLevelDestination.More -> false

            else -> true
        }

    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.entries

    fun navigateToTopLevel(topLevelDestination: TopLevelDestination) {
        trace("Navigation: ${topLevelDestination.name}") {
            val topLevelNavOptions = navOptions {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = false
                }
                launchSingleTop = true
            }

            when (topLevelDestination) {
                TopLevelDestination.Quests -> navController.navigateToQuests(topLevelNavOptions)
                TopLevelDestination.More -> navController.navigateToMore(topLevelNavOptions)
            }
        }
    }
}

package gay.pyrrha.dailyxp.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import gay.pyrrha.dailyxp.BuildConfig
import gay.pyrrha.dailyxp.feature.more.navigation.moreScreen
import gay.pyrrha.dailyxp.feature.quests.navigation.QUESTS_ROUTE
import gay.pyrrha.dailyxp.feature.quests.navigation.questsScreen
import gay.pyrrha.dailyxp.ui.XpAppState

@Composable
fun XpNavHost(
    appState: XpAppState,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
    startDestination: String = QUESTS_ROUTE
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        questsScreen(navController)
        moreScreen(navController, paddingValues, BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE.toString())
    }
}

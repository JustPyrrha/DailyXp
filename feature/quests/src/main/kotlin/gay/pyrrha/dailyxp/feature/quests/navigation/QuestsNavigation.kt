package gay.pyrrha.dailyxp.feature.quests.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import gay.pyrrha.dailyxp.feature.quests.QuestsScreen
import gay.pyrrha.dailyxp.feature.quests.edit.EditQuestScreen

const val QUESTS_ROUTE = "quests"
const val QUESTS_OVERVIEW_ROUTE = "quests/overview"

const val QUEST_EDIT_SCREEN_PARAM_ID = "questStoreId"
const val QUEST_EDIT_ROUTE = "quests/edit/{${QUEST_EDIT_SCREEN_PARAM_ID}}"

fun NavController.navigateToQuests(navOptions: NavOptions) =
    navigate(QUESTS_ROUTE, navOptions)

fun NavController.navigateToQuestEdit(questId: Int) =
    navigate(QUEST_EDIT_ROUTE.replace("{${QUEST_EDIT_SCREEN_PARAM_ID}}", questId.toString()))

fun NavController.navigateToQuestNew() =
    navigate(QUEST_EDIT_ROUTE.replace("{${QUEST_EDIT_SCREEN_PARAM_ID}}", "-1"))

fun NavGraphBuilder.questsScreen(
    navController: NavController,
) {
    navigation(startDestination = QUESTS_OVERVIEW_ROUTE, route = QUESTS_ROUTE) {
        composable(route = QUESTS_OVERVIEW_ROUTE, ) {
            QuestsScreen(
                navController = navController
            )
        }

        composable(
            route = QUEST_EDIT_ROUTE,
            arguments = listOf(
                navArgument(QUEST_EDIT_SCREEN_PARAM_ID) {
                    type = NavType.IntType
                }
            )
        ) {
            EditQuestScreen(
                storeId = it.arguments?.getInt(QUEST_EDIT_SCREEN_PARAM_ID)!!,
                onBackOrCancel = {
                    navController.popBackStack()
                }
            )
        }
    }
}

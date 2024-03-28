package gay.pyrrha.dailyxp.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import gay.pyrrha.dailyxp.core.design.component.XpCenterAlignedTopAppBar
import gay.pyrrha.dailyxp.core.design.component.XpNavigationBar
import gay.pyrrha.dailyxp.core.design.component.XpNavigationBarItem
import gay.pyrrha.dailyxp.core.design.component.XpNavigationRail
import gay.pyrrha.dailyxp.core.design.component.XpNavigationRailItem
import gay.pyrrha.dailyxp.core.design.icon.XpIcons
import gay.pyrrha.dailyxp.core.design.stringResource
import gay.pyrrha.dailyxp.feature.quests.navigation.navigateToQuestNew
import gay.pyrrha.dailyxp.i18n.MR
import gay.pyrrha.dailyxp.navigation.TopLevelDestination
import gay.pyrrha.dailyxp.navigation.XpNavHost

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun XpApp(
    appState: XpAppState
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            AnimatedVisibility(
                visible = appState.shouldShowBottomBar && !appState.shouldHideBars,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                XpBottomBar(
                    destinations = appState.topLevelDestinations,
                    onNavigate = appState::navigateToTopLevel,
                    currentDestination = appState.currentDestination
                )
            }
        },
        topBar = {
             AnimatedVisibility(
                 visible = appState.currentTopLevelDestination == TopLevelDestination.Quests,
                 enter = fadeIn(),
                 exit = fadeOut()
             ) {
                 XpCenterAlignedTopAppBar(
                     title = MR.strings.top_level_quests
                 )
             }
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = appState.currentTopLevelDestination == TopLevelDestination.Quests,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                FloatingActionButton(
                    onClick = {
                        appState.navController.navigateToQuestNew()
                    },
                    containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    Icon(imageVector = XpIcons.Add, contentDescription = null)
                }
            }
        },
        floatingActionButtonPosition = if (appState.shouldShowNavRail) {
            FabPosition.Start
        } else {
            FabPosition.End
        }
    ) { padding ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .consumeWindowInsets(padding)
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(
                        WindowInsetsSides.Horizontal
                    )
                )
        ) {
            AnimatedVisibility(
                visible = appState.shouldShowNavRail && !appState.shouldHideBars,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                XpNavRail(
                    destinations = appState.topLevelDestinations,
                    onNavigateToDestination = appState::navigateToTopLevel,
                    currentDestination = appState.currentDestination,
                    modifier = Modifier.fillMaxHeight()
                )
            }

            Column(modifier = Modifier.fillMaxSize()) {
                XpNavHost(
                    appState = appState,
                    paddingValues = padding
                )
            }
        }
    }
}

@Composable
private fun XpNavRail(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
) {
    XpNavigationRail(modifier = modifier) {
        destinations.forEach { destination ->
            val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
            XpNavigationRailItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    Icon(
                        imageVector = destination.unselectedIcon,
                        contentDescription = null,
                    )
                },
                selectedIcon = {
                    Icon(
                        imageVector = destination.selectedIcon,
                        contentDescription = null,
                    )
                },
                label = { Text(stringResource(destination.iconText)) },
            )
        }
    }
}

@Composable
private fun XpBottomBar(
    destinations: List<TopLevelDestination>,
    onNavigate: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier
) {
    XpNavigationBar(modifier = modifier) {
        destinations.forEach { destination ->
            val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
            XpNavigationBarItem(
                selected = selected,
                onClick = { onNavigate(destination) },
                icon = {
                    Icon(
                        imageVector = destination.unselectedIcon,
                        contentDescription = null
                    )
                },
                selectedIcon = {
                    Icon(
                        imageVector = destination.selectedIcon,
                        contentDescription = null
                    )
                },
                label = { Text(stringResource(destination.iconText)) }
            )
        }
    }
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false

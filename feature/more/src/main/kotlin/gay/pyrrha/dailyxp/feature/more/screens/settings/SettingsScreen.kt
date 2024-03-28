package gay.pyrrha.dailyxp.feature.more.screens.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import gay.pyrrha.dailyxp.core.design.component.XpTopAppBar
import gay.pyrrha.dailyxp.core.design.icon.XpIcons
import gay.pyrrha.dailyxp.core.design.stringResource
import gay.pyrrha.dailyxp.core.ui.widget.ScrollbarLazyColumn
import gay.pyrrha.dailyxp.core.ui.widget.preference.TextPreferenceWidget
import gay.pyrrha.dailyxp.i18n.MR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBackClick: () -> Unit,
    onAppearanceClick: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.settingsUiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            XpTopAppBar(
                title = MR.strings.pref_category_settings,
                navigationIcon = XpIcons.BackArrow,
                onNavigationIconClick = onBackClick,
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
        ) {
            when (uiState) {
                SettingsUiState.Loading -> {

                }
                is SettingsUiState.Success -> {
                    ScrollbarLazyColumn {
                        item {
                            TextPreferenceWidget(
                                title = stringResource(MR.strings.pref_category_appearance),
                                subtitle = stringResource(MR.strings.pref_category_appearance_subtitle),
                                icon = XpIcons.Palette,
                                onPreferenceClick = onAppearanceClick
                            )
                        }
                    }
                }
            }
        }
    }
}

package gay.pyrrha.dailyxp.feature.more.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gay.pyrrha.dailyxp.core.data.repository.UserDataRepository
import gay.pyrrha.dailyxp.core.design.icon.XpIcons
import gay.pyrrha.dailyxp.core.design.stringResource
import gay.pyrrha.dailyxp.core.ui.widget.ScrollbarLazyColumn
import gay.pyrrha.dailyxp.core.ui.widget.preference.TextPreferenceWidget
import gay.pyrrha.dailyxp.feature.more.LogoHeader
import gay.pyrrha.dailyxp.i18n.MR
import kotlinx.coroutines.launch
import javax.inject.Inject

@Composable
fun MoreScreen(
    paddingValues: PaddingValues,
    onSettingsClick: () -> Unit,
    onAboutClick: () -> Unit,
    viewModel: MoreScreenViewModel = hiltViewModel(),
) {
    ScrollbarLazyColumn(
        modifier = Modifier.padding(paddingValues)
    ) {
        item {
            LogoHeader()
        }

        item {
            TextPreferenceWidget(
                title = stringResource(MR.strings.pref_category_settings),
                icon = XpIcons.Settings,
                onPreferenceClick = onSettingsClick,
                onPreferenceLongClick = {
                    viewModel.showOnboarding()
                }
            )
        }

        item {
            TextPreferenceWidget(
                title = stringResource(MR.strings.pref_category_about),
                icon = XpIcons.Info,
                onPreferenceClick = onAboutClick
            )
        }
    }
}

@HiltViewModel
class MoreScreenViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository
): ViewModel() {
    fun showOnboarding() {
        viewModelScope.launch {
            userDataRepository.setHideOnboarding(false)
        }
    }
}

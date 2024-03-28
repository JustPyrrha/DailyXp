package gay.pyrrha.dailyxp.feature.quests

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import gay.pyrrha.dailyxp.core.design.component.XpCenterAlignedTopAppBar
import gay.pyrrha.dailyxp.core.design.stringResource
import gay.pyrrha.dailyxp.core.design.theme.XpTheme
import gay.pyrrha.dailyxp.core.model.data.shouldRefresh
import gay.pyrrha.dailyxp.core.ui.widget.ScrollbarLazyColumn
import gay.pyrrha.dailyxp.core.ui.widget.quest.QuestWidget
import gay.pyrrha.dailyxp.feature.quests.QuestsUiState.*
import gay.pyrrha.dailyxp.feature.quests.navigation.navigateToQuestEdit
import gay.pyrrha.dailyxp.i18n.MR
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun QuestsScreen(
    navController: NavController,
    viewModel: QuestsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    QuestsScreen(
        onQuestClick = { navController.navigateToQuestEdit(it) },
        uiState = uiState,
    )
}

@Composable
fun QuestsScreen(
    onQuestClick: (Int) -> Unit,
    uiState: QuestsUiState
) {
    when (uiState) {
        Empty -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(MR.strings.quests_empty),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }

        Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        }

        is Success -> {
            ScrollbarLazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                itemsIndexed(
                    uiState.quests,
                    key = { i, _ -> "q-$i" },
                    contentType = { _, _ -> "QuestWidget" }) { index, item ->
                    QuestWidget(
                        quest = item,
                        onClick = {
                            onQuestClick(index)
                        }
                    )
                }
            }
        }
    }
}

@Composable
@PreviewLightDark
fun QuestsScreenEmptyPreview() {
    XpTheme {
        Surface {
            QuestsScreen(
                uiState = Empty,
                onQuestClick = { }
            )
        }
    }
}

@Composable
@PreviewLightDark
fun QuestsScreenLoadingPreview() {
    XpTheme {
        Surface {
            QuestsScreen(
                uiState = Loading,
                onQuestClick = { }
            )
        }
    }
}

package gay.pyrrha.dailyxp.feature.quests

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gay.pyrrha.dailyxp.core.data.repository.QuestDataRepository
import gay.pyrrha.dailyxp.core.model.data.Quest
import gay.pyrrha.dailyxp.core.model.data.shouldRefresh
import gay.pyrrha.dailyxp.feature.quests.QuestsUiState.Empty
import gay.pyrrha.dailyxp.feature.quests.QuestsUiState.Loading
import gay.pyrrha.dailyxp.feature.quests.QuestsUiState.Success
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class QuestsViewModel @Inject constructor(
    questsDataRepository: QuestDataRepository
) : ViewModel() {
    val uiState: StateFlow<QuestsUiState> =
        questsDataRepository.questData
            .map { questData ->
                if (questData.quests.isEmpty()) {
                    Empty
                } else {
                    Success(
                        quests = questData.quests
                    )
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5.seconds.inWholeMilliseconds),
                initialValue = Loading
            )
}

sealed interface QuestsUiState {
    data object Loading : QuestsUiState
    data object Empty : QuestsUiState
    data class Success(val quests: List<Quest>) : QuestsUiState
}

package gay.pyrrha.dailyxp.feature.quests.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import gay.pyrrha.dailyxp.core.datastore.XpQuestsDataSource
import gay.pyrrha.dailyxp.core.model.data.Quest
import gay.pyrrha.dailyxp.feature.quests.edit.EditQuestUiState.Loading
import gay.pyrrha.dailyxp.feature.quests.edit.EditQuestUiState.Success
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration.Companion.seconds

@HiltViewModel(assistedFactory = EditQuestViewModel.Factory::class)
class EditQuestViewModel @AssistedInject constructor(
    @Assisted private val storeId: Int,
    private val xpQuestsDataSource: XpQuestsDataSource
) : ViewModel() {
    val uiState: StateFlow<EditQuestUiState> = xpQuestsDataSource.questsData
        .map { questData ->
            val quest = if (storeId >= 0 && questData.quests.size >= storeId + 1) {
                questData.quests[storeId]
            } else {
                Quest(
                    title = "",
                    segments = emptyMap(),
                    refresh = DatePeriod(days = 1),
                    refreshAt = Clock.System.now()
                        .toLocalDateTime(TimeZone.currentSystemDefault()).time,
                    completedAt = null
                )
            }

            val segments = mutableListOf<EditableSegment>()
            segments.addAll(quest.segments.map { EditableSegment(it.key, it.value) })

            Success(
                editableQuest = EditableQuest(
                    title = quest.title,
                    refreshPeriod = EditableRefreshPeriod(
                        length = if (quest.refresh.days != 0 || (quest.refresh.days == 0 && quest.refresh.months == 0)) {
                            quest.refresh.days
                        } else {
                            quest.refresh.months
                        },
                        daysOrMonths = !(quest.refresh.days != 0 || (quest.refresh.days == 0 && quest.refresh.months == 0))
                    ),
                    refreshTime = quest.refreshAt,
                    segments = segments,
                    completedAt = quest.completedAt
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5.seconds.inWholeMilliseconds),
            initialValue = Loading
        )

    fun save(quest: EditableQuest) {
        viewModelScope.launch {
            val savedQuest = Quest(
                title = quest.title,
                refresh = if (quest.refreshPeriod.daysOrMonths) {
                    DatePeriod(months = quest.refreshPeriod.length)
                } else {
                    DatePeriod(days = quest.refreshPeriod.length)
                },
                refreshAt = quest.refreshTime,
                segments = quest.segments.associate { it.title to it.complete },
                completedAt = if (quest.completedAt != null && quest.segments.all { it.complete }) {
                    quest.completedAt
                } else {
                    if (quest.segments.all { it.complete }) {
                        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
                    } else {
                        null
                    }
                }
            )

            if (storeId >= 0) {
                xpQuestsDataSource.setQuest(storeId, savedQuest)
            } else {
                xpQuestsDataSource.addQuest(savedQuest)
            }
        }
    }

    fun delete() {
        viewModelScope.launch {
           if (storeId >= 0) {
               xpQuestsDataSource.removeQuest(storeId)
           }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(storeId: Int): EditQuestViewModel
    }
}

data class EditableRefreshPeriod(
    val length: Int,
    // false for days, true for months
    val daysOrMonths: Boolean
)

data class EditableSegment(
    val title: String,
    val complete: Boolean
)

data class EditableQuest(
    val title: String,
    val refreshPeriod: EditableRefreshPeriod,
    val refreshTime: LocalTime,
    val segments: List<EditableSegment>,
    val completedAt: LocalDate?
)

sealed interface EditQuestUiState {
    data object Loading : EditQuestUiState
    data class Success(val editableQuest: EditableQuest) : EditQuestUiState
}
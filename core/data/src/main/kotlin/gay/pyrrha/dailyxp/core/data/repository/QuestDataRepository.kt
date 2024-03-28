package gay.pyrrha.dailyxp.core.data.repository

import gay.pyrrha.dailyxp.core.model.data.Quest
import gay.pyrrha.dailyxp.core.model.data.QuestData
import kotlinx.coroutines.flow.Flow

interface QuestDataRepository {
    val questData: Flow<QuestData>
    suspend fun setQuest(index: Int, quest: Quest)
    suspend fun addQuest(quest: Quest)
    suspend fun removeQuest(index: Int)
    suspend fun refreshQuest(index: Int)
    suspend fun refreshAllIfNeeded()
}
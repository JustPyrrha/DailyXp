package gay.pyrrha.dailyxp.core.data.repository

import gay.pyrrha.dailyxp.core.datastore.XpQuestsDataSource
import gay.pyrrha.dailyxp.core.model.data.Quest
import gay.pyrrha.dailyxp.core.model.data.QuestData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class OfflineFirstQuestDataRepository @Inject constructor(
    private val xpQuestsDataSource: XpQuestsDataSource
) : QuestDataRepository {
    override val questData: Flow<QuestData> =
        xpQuestsDataSource.questsData

    override suspend fun setQuest(index: Int, quest: Quest) =
        xpQuestsDataSource.setQuest(index, quest)

    override suspend fun addQuest(quest: Quest) =
        xpQuestsDataSource.addQuest(quest)

    override suspend fun removeQuest(index: Int) =
        xpQuestsDataSource.removeQuest(index)

    override suspend fun refreshQuest(index: Int) =
        xpQuestsDataSource.refreshQuest(index)

    override suspend fun refreshAllIfNeeded() =
        xpQuestsDataSource.refreshAllIfNeeded()
}

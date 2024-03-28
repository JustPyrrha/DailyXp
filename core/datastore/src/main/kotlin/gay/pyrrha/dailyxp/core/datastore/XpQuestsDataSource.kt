package gay.pyrrha.dailyxp.core.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import gay.pyrrha.dailyxp.core.model.data.Quest
import gay.pyrrha.dailyxp.core.model.data.QuestData
import gay.pyrrha.dailyxp.core.model.data.shouldRefresh
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

class XpQuestsDataSource @Inject constructor(
    private val questsStore: DataStore<QuestsProto>
) {
    val questsData = questsStore.data
        .map { proto ->
            val quests = mutableListOf<Quest>()
            quests.addAll(
                proto.questsList.map {
                    it.asDataQuest()
                }
            )

            QuestData(
                totalCompletedCount = proto.totalCompletedCount,
                quests = quests
            )
        }

    suspend fun updateQuests(quests: MutableList<Quest>) {
        questsStore.updateData {
            it.copy {
                quests.forEachIndexed { index, quest ->
                    setQuest(index, quest)
                }
            }
        }
    }

    suspend fun setQuest(index: Int, quest: Quest) {
        questsStore.updateData {
            val builder = QuestsProto.newBuilder(it)
            builder.setQuests(index, quest.asStoreQuest())
            builder.build()
        }
        questsStore.data.collectLatest { delay(1) /* dumb jank to mark the flow as hot again */ }
    }

    suspend fun addQuest(quest: Quest) {
        questsStore.updateData {
            val builder = QuestsProto.newBuilder(it)
            builder.addQuests(quest.asStoreQuest())
            builder.build()
        }
        questsStore.data.collectLatest { delay(1) /* dumb jank to mark the flow as hot again */ }
    }

    suspend fun removeQuest(index: Int) {
        questsStore.updateData {
            val builder = QuestsProto.newBuilder(it)
            builder.removeQuests(index)
            builder.build()
        }
        questsStore.data.collectLatest { delay(1) /* dumb jank to mark the flow as hot again */ }
    }

    suspend fun refreshQuest(index: Int) {
        questsStore.updateData {
            it.copy {
                val questProto = this.quests[index]
                val original = questProto.asDataQuest()
                val refreshedSegments = original.segments.keys.associateWith { _ -> false }
                val refreshed = original.copy(
                    segments = refreshedSegments,
                    completedAt = null
                )

                setQuest(index, refreshed)
            }
        }
    }

    suspend fun refreshAllIfNeeded() {
        questsStore.updateData {
            it.copy {
                this.quests.forEachIndexed { index, it ->
                    val quest = it.asDataQuest()
                    val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                    if (quest.shouldRefresh(now)) {
                        refreshQuest(index)
                    }
                }
            }
        }
    }
}

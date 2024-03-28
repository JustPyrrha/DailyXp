package gay.pyrrha.dailyxp.core.model.data

data class QuestData(
    val totalCompletedCount: Long,
    val quests: MutableList<Quest>,
)

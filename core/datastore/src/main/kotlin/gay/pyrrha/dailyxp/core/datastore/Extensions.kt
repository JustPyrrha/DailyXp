package gay.pyrrha.dailyxp.core.datastore

import android.util.Log
import gay.pyrrha.dailyxp.core.model.data.Quest
import gay.pyrrha.dailyxp.core.model.data.refreshIfNeeded
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun QuestProto.asDataQuest(): Quest {
    val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    return Quest(
        title = this.title,
        segments = this.segmentsMap,
        refresh = DatePeriod(
            months = this.repeats.months,
            days = this.repeats.days
        ),
        refreshAt = try {
            LocalTime.parse(this.repeatsAt)
        } catch (_: Exception) { now.time },
        completedAt = if (this.completedAt.isNotEmpty()) try {
            LocalDate.parse(this.completedAt)
        } catch (_: Exception) { now.date } else null
    ).refreshIfNeeded(now)
}

fun Quest.asStoreQuest(): QuestProto {
    val builder = QuestProto.newBuilder()
        .setTitle(this.title)
        .setRepeats(
            gay.pyrrha.dailyxp.core.datastore.DatePeriod.newBuilder()
                .setMonths(this.refresh.months)
                .setDays(this.refresh.days)
        )
        .setRepeatsAt(this.refreshAt.toString())

    builder.clearSegments()
    builder.putAllSegments(this.segments)

    if (this.completedAt != null) {
        builder.setCompletedAt(this.completedAt!!.toString())
    } else {
        builder.setCompletedAt("")
    }

    return builder.build()
}

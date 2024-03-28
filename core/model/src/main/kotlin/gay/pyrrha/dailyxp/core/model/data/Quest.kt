package gay.pyrrha.dailyxp.core.model.data

import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.daysUntil
import kotlinx.datetime.periodUntil
import kotlinx.datetime.plus

data class Quest(
    val title: String,
    val segments: Map<String, Boolean>,
    val refresh: DatePeriod, // how long since completedAt it can be redone
    val refreshAt: LocalTime, // at what time on the refresh date it should be refreshed
    val completedAt: LocalDate?,
)

fun Quest.datePeriodUntilRefresh(now: LocalDate): DatePeriod? =
    if (completedAt != null) {
        now.periodUntil(completedAt.plus(refresh))
    } else {
        null
    }

fun Quest.shouldRefresh(now: LocalDateTime): Boolean {
    val untilRefresh = datePeriodUntilRefresh(now.date)
    if (untilRefresh != null) {
        val refreshDate = now.date.plus(untilRefresh)
        val daysUntilRefresh = now.date.daysUntil(refreshDate)

        return if(daysUntilRefresh == 0) {
            // today
            now.time >= refreshAt

        } else if (now.date.daysUntil(refreshDate) <= -1) {
            // in the past
            true
        } else {
            // in the future
            false
        }
    } else {
        return false
    }
}

fun Quest.refreshIfNeeded(now: LocalDateTime): Quest {
    if (shouldRefresh(now)) {
        return this.copy(
            completedAt = null,
            segments = this.segments.keys.associateWith { false }
        )
    }
    return this
}

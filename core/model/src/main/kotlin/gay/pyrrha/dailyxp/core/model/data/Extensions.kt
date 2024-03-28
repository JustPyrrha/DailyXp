package gay.pyrrha.dailyxp.core.model.data

import kotlinx.datetime.LocalTime

fun LocalTime.Companion.of(
    hour: Int = 0,
    minute: Int = 0,
    second: Int = 0,
    nanosecond: Int = 0
): LocalTime =
    LocalTime(
        hour = hour,
        minute = minute,
        second = second,
        nanosecond = nanosecond
    )

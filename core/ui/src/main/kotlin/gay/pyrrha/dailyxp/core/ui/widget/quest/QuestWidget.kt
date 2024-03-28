package gay.pyrrha.dailyxp.core.ui.widget.quest


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import gay.pyrrha.dailyxp.core.design.component.SegmentedProgressIndicator
import gay.pyrrha.dailyxp.core.design.icon.XpIcons
import gay.pyrrha.dailyxp.core.design.pluralStringResource
import gay.pyrrha.dailyxp.core.design.stringResource
import gay.pyrrha.dailyxp.core.design.theme.XpTheme
import gay.pyrrha.dailyxp.core.design.theme.secondaryItemAlpha
import gay.pyrrha.dailyxp.core.model.data.Quest
import gay.pyrrha.dailyxp.core.model.data.datePeriodUntilRefresh
import gay.pyrrha.dailyxp.core.model.data.of
import gay.pyrrha.dailyxp.i18n.MR
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

@Composable
fun QuestWidget(
    quest: Quest,
    onClick: (() -> Unit)? = null
) {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = MaterialTheme.shapes.medium
            )
            .fillMaxWidth()
            .height(128.dp)
            .clickable(enabled = onClick != null) { onClick?.invoke() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .alpha(if (quest.completedAt != null) 0.25f else 1f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = XpIcons.PinDrop,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = quest.title,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "${quest.segments.count { it.value }}/${quest.segments.count()}",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .secondaryItemAlpha()
                    )
                }

                // segment footer
                Spacer(
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 16.dp)
                )
                SegmentedProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    progress = quest.segments.count { it.value } / quest.segments.count().toFloat(),
                    numberOfSegments = quest.segments.count()
                )
            }
        }

        if (quest.completedAt != null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = Color.Transparent,
                        shape = MaterialTheme.shapes.medium
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(MR.strings.quest_completed),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                    val daysUntilAvailable =
                        now.date.daysUntil(now.date.plus(quest.datePeriodUntilRefresh(now.date)!!))
                    val timeFormatted = "${quest.refreshAt.hour.toString().padStart(2, '0')}:${quest.refreshAt.minute.toString().padStart(2, '0')}"

                    Text(
                        text = if (daysUntilAvailable == 0) {
                            stringResource(
                                MR.strings.quest_available_today,
                                timeFormatted
                            )
                        } else {
                            pluralStringResource(
                                MR.plurals.quest_available,
                                daysUntilAvailable,
                                daysUntilAvailable,
                                timeFormatted
                            )
                        },
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
fun QuestWidgetPreview() {
    XpTheme {
        QuestWidget(
            quest = Quest(
                title = "Tests Quest",
                segments = mapOf(
                    "Do the thing" to true,
                    "Do something else" to true,
                    "Do the different thing" to false,
                ),
                refresh = DatePeriod(days = 1),
                refreshAt = LocalTime.of(hour = 6),
                completedAt = null,
            )
        )
    }
}

@PreviewLightDark
@Composable
fun QuestWidgetAvailableTodayPreview() {
    XpTheme {
        QuestWidget(
            quest = Quest(
                title = "Tests Quest",
                segments = mapOf(
                    "Do the thing" to true,
                    "Do something else" to true,
                    "Do the different thing" to true,
                ),
                refresh = DatePeriod(days = 0),
                refreshAt = LocalTime.of(hour = 6),
                completedAt = Clock.System.now().toLocalDateTime(TimeZone.UTC).date
            )
        )
    }
}

@PreviewLightDark
@Composable
fun QuestWidgetAvailableTomorrowPreview() {
    XpTheme {
        QuestWidget(
            quest = Quest(
                title = "Tests Quest",
                segments = mapOf(
                    "Do the thing" to true,
                    "Do something else" to true,
                    "Do the different thing" to true,
                ),
                refresh = DatePeriod(days = 1),
                refreshAt = LocalTime.of(hour = 6),
                completedAt = Clock.System.now().toLocalDateTime(TimeZone.UTC).date
            )
        )
    }
}

@PreviewLightDark
@Composable
fun QuestWidgetAvailableInFuturePreview() {
    XpTheme {
        QuestWidget(
            quest = Quest(
                title = "Tests Quest",
                segments = mapOf(
                    "Do the thing" to true,
                    "Do something else" to true,
                    "Do the different thing" to true,
                ),
                refresh = DatePeriod(days = 5),
                refreshAt = LocalTime.of(hour = 6),
                completedAt = Clock.System.now().toLocalDateTime(TimeZone.UTC).date
            )
        )
    }
}

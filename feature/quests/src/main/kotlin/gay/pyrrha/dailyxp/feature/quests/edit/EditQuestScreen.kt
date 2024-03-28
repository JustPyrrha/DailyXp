package gay.pyrrha.dailyxp.feature.quests.edit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import gay.pyrrha.dailyxp.core.design.component.XpTopAppBar
import gay.pyrrha.dailyxp.core.design.icon.XpIcons
import gay.pyrrha.dailyxp.core.design.pluralStringResource
import gay.pyrrha.dailyxp.core.design.stringResource
import gay.pyrrha.dailyxp.core.design.theme.XpTheme
import gay.pyrrha.dailyxp.core.ui.widget.ScrollbarLazyColumn
import gay.pyrrha.dailyxp.core.ui.widget.preference.TextPreferenceWidget
import gay.pyrrha.dailyxp.feature.quests.edit.EditQuestUiState.*
import gay.pyrrha.dailyxp.i18n.MR
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun EditQuestScreen(
    storeId: Int,
    onBackOrCancel: () -> Unit,
    viewModel: EditQuestViewModel = hiltViewModel(
        creationCallback = { factory: EditQuestViewModel.Factory ->
            factory.create(storeId)
        }
    )
) {
    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        }
        is Success -> {
            EditQuestScreen(
                quest = (uiState as Success).editableQuest,
                onSave = viewModel::save,
                onBackOrCancel = onBackOrCancel,
                onDelete = {
                    viewModel.delete()
                    onBackOrCancel()
                },
                showDelete = storeId >= 0
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditQuestScreen(
    quest: EditableQuest,
    onSave: (EditableQuest) -> Unit,
    onBackOrCancel: () -> Unit,
    onDelete: () -> Unit,
    showDelete: Boolean = false,
) {
    var savedQuest by remember { mutableStateOf(quest) }
    var editedQuest by remember { mutableStateOf(quest) }
    var editedSegments by remember { mutableStateOf(quest.segments) }

    val timePickerState = rememberTimePickerState(
        initialHour = editedQuest.refreshTime.hour,
        initialMinute = editedQuest.refreshTime.minute
    )
    var showTimePicker by rememberSaveable { mutableStateOf(false) }
    var showEditSegmentDialog by rememberSaveable { mutableStateOf<Int?>(null) }

    if (showTimePicker) {
        val onDismiss: () -> Unit = {
            editedQuest = editedQuest.copy(
                refreshTime = LocalTime(
                    hour = timePickerState.hour,
                    minute = timePickerState.minute
                )
            )

            showTimePicker = false
        }

        AlertDialog(
            title = {
                Text(
                    text = stringResource(MR.strings.quest_edit_refresh_at),
                    style = MaterialTheme.typography.titleLarge,
                )
            },
            text = {
                TimePicker(state = timePickerState)
            },
            confirmButton = {
                Text(
                    text = stringResource(MR.strings.confirm),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .clickable { onDismiss() }
                )
            },
            onDismissRequest = { onDismiss() },
        )
    }

    if (showEditSegmentDialog != null) {
        var fieldState by rememberSaveable { mutableStateOf("") }

        if (showEditSegmentDialog!! >= 0) {
            fieldState = editedQuest.segments[showEditSegmentDialog!!].title
        }

        val onDismiss: () -> Unit = {
            if (fieldState.isNotBlank()) {
                val segments = mutableListOf<EditableSegment>()
                segments.addAll(editedSegments)
                if (showEditSegmentDialog!! >= 0) {
                    segments[showEditSegmentDialog!!] = segments[showEditSegmentDialog!!].copy(title = fieldState)
                } else {
                    segments.add(
                        EditableSegment(
                            title = fieldState,
                            complete = false
                        )
                    )
                }

                editedSegments = listOf(*segments.toTypedArray())
                editedQuest = editedQuest.copy(segments = editedSegments)

                showEditSegmentDialog = null
            }
        }

        AlertDialog(
            title = {
                Text(
                    text = if (showEditSegmentDialog!! >= 0) {
                        stringResource(MR.strings.quest_edit_edit_task)
                    } else {
                        stringResource(MR.strings.quest_edit_create_task)
                    },
                    style = MaterialTheme.typography.titleLarge,
                )
            },
            text = {
                OutlinedTextField(
                    value = fieldState,
                    onValueChange = { fieldState = it },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    label = {
                        Text(
                            text = stringResource(MR.strings.quest_edit_title),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = XpIcons.Title,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                )
            },
            confirmButton = {
                Text(
                    text = stringResource(MR.strings.confirm),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .clickable { onDismiss() }
                )
            },
            onDismissRequest = { onDismiss() },
        )
    }

    ScrollbarLazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            XpTopAppBar(
                title = stringResource(MR.strings.quest_edit),
                navigationIcon = XpIcons.BackArrow,
                onNavigationIconClick = onBackOrCancel,
                actions = {
                    if (savedQuest != editedQuest && editedQuest.segments.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                savedQuest = editedQuest
                                savedQuest = savedQuest.copy(segments = editedSegments)
                                onSave(savedQuest)
                            }
                        ) {
                            Icon(
                                imageVector = XpIcons.Save,
                                contentDescription = null
                            )
                        }
                    }

                    IconButton(onClick = { showEditSegmentDialog = -1 /* show create dialog */}) {
                        Icon(
                            imageVector = XpIcons.AddTask,
                            contentDescription = null
                        )
                    }

                    if (showDelete) {
                        IconButton(onClick = onDelete) {
                            Icon(
                                imageVector = XpIcons.DeleteForever,
                                contentDescription = null
                            )
                        }
                    }
                }
            )
        }
        item {
            OutlinedTextField(
                value = editedQuest.title,
                onValueChange = { editedQuest = editedQuest.copy(title = it) },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                label = {
                    Text(
                        text = stringResource(MR.strings.quest_edit_title),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = XpIcons.Title,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            )
        }
        item {
            OutlinedTextField(
                value = editedQuest.refreshPeriod.length.toString(),
                onValueChange = {
                    var value = it.toIntOrNull() ?: 1
                    if (value <= 0) value = 1
                    editedQuest =
                        editedQuest.copy(refreshPeriod = editedQuest.refreshPeriod.copy(length = value))
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                label = {
                    Text(
                        text = stringResource(MR.strings.quest_edit_repeatable),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                leadingIcon = {
                    Icon(
                        imageVector = XpIcons.EventRepeat,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                trailingIcon = {
                    MultiChoiceSegmentedButtonRow(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                    ) {
                        SegmentedButton(
                            checked = !editedQuest.refreshPeriod.daysOrMonths,
                            onCheckedChange = { editedQuest = editedQuest.copy(refreshPeriod = editedQuest.refreshPeriod.copy(daysOrMonths = false)) },
                            shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2),
                            label = {
                                Text(
                                    text = pluralStringResource(
                                        MR.plurals.days,
                                        editedQuest.refreshPeriod.length
                                    ),
                                )
                            }
                        )
                        SegmentedButton(
                            checked = editedQuest.refreshPeriod.daysOrMonths,
                            onCheckedChange = { editedQuest = editedQuest.copy(refreshPeriod = editedQuest.refreshPeriod.copy(daysOrMonths = true)) },
                            shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2),
                            label = {
                                Text(
                                    text = pluralStringResource(
                                        MR.plurals.months,
                                        editedQuest.refreshPeriod.length
                                    ),
                                )
                            }
                        )
                    }
                }
            )
        }

        item {
            val timeFormatted = "${editedQuest.refreshTime.hour.toString().padStart(2, '0')}:${editedQuest.refreshTime.minute.toString().padStart(2, '0')}"
            TextPreferenceWidget(
                title = stringResource(MR.strings.quest_edit_refresh_at),
                subtitle = timeFormatted,
                icon = XpIcons.AccessTime,
                onPreferenceClick = { showTimePicker = true }
            )
        }

        item {
            HorizontalDivider(modifier = Modifier.fillMaxWidth())
        }

        itemsIndexed(editedQuest.segments, key = { index, _ ->  "segment-$index" }) { index, item ->
            TextPreferenceWidget(
                title = item.title,
                icon = XpIcons.TaskAlt,
                widget = {
                    Row {
                        Checkbox(
                            checked = item.complete,
                            onCheckedChange = {
                                val segments = mutableListOf(*editedSegments.toTypedArray())
                                segments[index] = EditableSegment(item.title, !item.complete)
                                editedSegments = listOf(*segments.toTypedArray())
                                editedQuest = editedQuest.copy(segments = editedSegments)
                            }
                        )
                        IconButton(
                            onClick = {
                                val segments = mutableListOf(*editedSegments.toTypedArray())
                                segments.removeAt(index)
                                editedSegments = listOf(*segments.toTypedArray())
                                editedQuest = editedQuest.copy(segments = editedSegments)
                            }
                        ) {
                            Icon(
                                imageVector = XpIcons.DeleteForever,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            )
        }
    }
}

@Composable
@PreviewLightDark
fun EditQuestScreenPreview() {
    XpTheme {
        Surface {
            EditQuestScreen(
                quest = EditableQuest(
                    title = "Test Quest",

                    refreshPeriod = EditableRefreshPeriod(
                        length = 1,
                        daysOrMonths = false
                    ),
                    refreshTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time,
                    segments = mutableListOf(
                        EditableSegment("Do the thing", true),
                        EditableSegment("Do something else", true),
                        EditableSegment("Do the different thing", false),
                        EditableSegment("Really really really long title that goes way over or something idk lol", false),
                    ),
                    completedAt = null
                ),
                onBackOrCancel = { },
                onSave = { },
                onDelete = { },
                showDelete = true
            )
        }
    }
}

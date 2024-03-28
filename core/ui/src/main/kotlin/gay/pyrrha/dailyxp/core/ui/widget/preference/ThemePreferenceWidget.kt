package gay.pyrrha.dailyxp.core.ui.widget.preference

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import gay.pyrrha.dailyxp.core.design.theme.XpTheme
import gay.pyrrha.dailyxp.core.design.icon.XpIcons
import gay.pyrrha.dailyxp.core.design.theme.padding
import gay.pyrrha.dailyxp.core.design.theme.secondaryItemAlpha
import gay.pyrrha.dailyxp.core.model.data.Theme
import gay.pyrrha.dailyxp.core.design.stringResource
import gay.pyrrha.dailyxp.core.design.i18n.titleRes
import gay.pyrrha.dailyxp.core.model.data.ThemeMode
import gay.pyrrha.dailyxp.core.util.supportsDynamicTheming
import gay.pyrrha.dailyxp.i18n.MR

@Composable
fun XpThemesListWidget(
    value: Theme,
    themeMode: ThemeMode,
    onItemClick: (Theme) -> Unit
) {
    PreferenceWidget(
        subcomponent = {
            XpThemesList(
                currentTheme = value,
                currentThemeMode = themeMode,
                onItemClick = onItemClick
            )
        }
    )
}

@Composable
fun XpThemesList(
    currentTheme: Theme,
    currentThemeMode: ThemeMode,
    onItemClick: (Theme) -> Unit
) {
    val context = LocalContext.current
    val appThemes = remember { Theme.entries.filter { it.titleRes() != null }.filter { if(!supportsDynamicTheming()) { it != Theme.DYNAMIC } else true } }

    LazyRow(
        contentPadding = PaddingValues(horizontal = PrefsHorizontalPadding),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.padding.small)
    ) {
        items(
            items = appThemes,
            key = { it.name },
        ) { appTheme ->
            Column(
                modifier = Modifier
                    .width(114.dp)
                    .padding(top = 8.dp)
            ) {
                XpTheme(
                    theme = appTheme,
                    darkTheme = when (currentThemeMode) {
                        ThemeMode.SYSTEM -> isSystemInDarkTheme() && appTheme != Theme.LATTE
                        ThemeMode.LIGHT -> false
                        ThemeMode.DARK -> appTheme != Theme.LATTE
                    }
                ) {
                    XpThemePreviewItem(
                        selected = currentTheme == appTheme,
                        onClick = {
                            onItemClick(appTheme)
                            (context as? Activity)?.let { ActivityCompat.recreate(it) }
                        }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(appTheme.titleRes()!!),
                    modifier = Modifier
                        .fillMaxWidth()
                        .secondaryItemAlpha(),
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    minLines = 2,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun XpThemePreviewItem(
    selected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(9f / 16f)
            .border(
                width = 4.dp,
                color = if (selected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    DividerDefaults.color
                },
                shape = RoundedCornerShape(17.dp)
            )
            .padding(4.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.background)
            .clickable(onClick = onClick)
    ) {
        // App Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight(0.8f)
                    .weight(0.7f)
                    .padding(end = 4.dp)
                    .background(
                        color = MaterialTheme.colorScheme.onSurface,
                        shape = MaterialTheme.shapes.small
                    )
            )
            Box(
                modifier = Modifier.weight(0.3f),
                contentAlignment = Alignment.CenterEnd
            ) {
                if (selected) {
                    Icon(
                        imageVector = XpIcons.CheckCircle,
                        contentDescription = stringResource(MR.strings.selected),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .padding(start = 8.dp, top = 2.dp)
                .background(
                    color = DividerDefaults.color,
                    shape = MaterialTheme.shapes.small
                )
                .fillMaxWidth(0.5f)
                .aspectRatio(3f / 3f)
        ) {
            Row(
                modifier = Modifier
                    .padding(4.dp)
                    .size(width = 24.dp, height = 16.dp)
                    .clip(RoundedCornerShape(5.dp))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(12.dp)
                        .background(MaterialTheme.colorScheme.tertiary)
                )
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(12.dp)
                        .background(MaterialTheme.colorScheme.secondary)
                )
            }
        }

        // Bottom Bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.BottomCenter
        ) {
            Surface(
                tonalElevation = 3.dp
            ) {
                Row(
                    modifier = Modifier
                        .height(32.dp)
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(17.dp)
                            .background(
                                color = MaterialTheme.colorScheme.primary,
                                shape = CircleShape
                            )
                    )
                    Box(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .alpha(0.6f)
                            .height(17.dp)
                            .weight(1f)
                            .background(
                                color = MaterialTheme.colorScheme.onSurface,
                                shape = MaterialTheme.shapes.small
                            )
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun ThemePreferenceWidgetPreview() {
    var appTheme by remember { mutableStateOf(Theme.DYNAMIC) }
    val appThemeMode by remember { mutableStateOf(ThemeMode.SYSTEM) }
    XpTheme(theme = appTheme) {
        Surface {
            XpThemesList(
                currentTheme = appTheme,
                currentThemeMode = appThemeMode,
                onItemClick = { appTheme = it }
            )
        }
    }
}

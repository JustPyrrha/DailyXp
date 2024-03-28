package gay.pyrrha.dailyxp.feature.more.screens.about

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.core.content.getSystemService
import gay.pyrrha.dailyxp.core.design.component.XpTopAppBar
import gay.pyrrha.dailyxp.core.design.icon.BrandIcons
import gay.pyrrha.dailyxp.core.design.icon.XpIcons
import gay.pyrrha.dailyxp.core.design.icon.brandicons.Discord
import gay.pyrrha.dailyxp.core.design.icon.brandicons.Github
import gay.pyrrha.dailyxp.core.design.stringResource
import gay.pyrrha.dailyxp.core.ui.widget.ScrollbarLazyColumn
import gay.pyrrha.dailyxp.core.ui.widget.preference.TextPreferenceWidget
import gay.pyrrha.dailyxp.feature.more.BuildConfig
import gay.pyrrha.dailyxp.feature.more.LogoHeader
import gay.pyrrha.dailyxp.feature.more.util.getDebugInfo
import gay.pyrrha.dailyxp.i18n.MR
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(
    onBackClick: () -> Unit,
    appVersion: String,
    appVersionCode: String,
    onLicensesClick: () -> Unit,
) {
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current

    Scaffold(
        topBar = {
            XpTopAppBar(
                title = MR.strings.pref_category_about,
                navigationIcon = XpIcons.BackArrow,
                onNavigationIconClick = onBackClick,
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
        ) {
            ScrollbarLazyColumn {
                item {
                    LogoHeader()
                }

                item {
                    TextPreferenceWidget(
                        title = stringResource(MR.strings.version),
                        subtitle = getFormattedVersionName(appVersion),
                        onPreferenceClick = {
                            context.copyToClipboard("Debug information", getDebugInfo(appVersion, appVersionCode))
                        }
                    )
                }

                if (!BuildConfig.DEBUG) {
                    item {
                        TextPreferenceWidget(
                            title = stringResource(MR.strings.whats_new),
                            onPreferenceClick = { uriHandler.openUri("https://github.com/JustPyrrha/DailyXp/releases/latest") },
                        )
                    }
                }

                item {
                    TextPreferenceWidget(
                        title = stringResource(MR.strings.licenses),
                        onPreferenceClick = onLicensesClick,
                    )
                }

                item {
                    TextPreferenceWidget(
                        title = stringResource(MR.strings.privacy_policy),
                        onPreferenceClick = { uriHandler.openUri("https://github.com/JustPyrrha/DailyXp/blob/main/legal/privacy-policy.md") },
                    )
                }

                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        IconButton(
                            onClick = { uriHandler.openUri("https://discord.gg/EXhmazx7t5") }
                        ) {
                            Icon(
                                imageVector = BrandIcons.Discord,
                                contentDescription = "Discord",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                        IconButton(
                            onClick = { uriHandler.openUri("https://github.com/JustPyrrha/DailyXp/releases/latest") }
                        ) {
                            Icon(
                                imageVector = BrandIcons.Github,
                                contentDescription = "GitHub",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun getFormattedVersionName(versionName: String): String {
    return when {
        BuildConfig.DEBUG -> {
            "Debug $versionName".let {
                "$it (${BuildConfig.COMMIT_SHA}, ${getFormattedBuildTime()})"
            }
        }
        else -> {
            "Stable $versionName".let {
                "$it (${BuildConfig.COMMIT_SHA}, ${getFormattedBuildTime()})"
            }
        }
    }
}

private fun getFormattedBuildTime(): String {
    return try {
        val df = DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneId.of("UTC"))
        val buildTime = LocalDateTime.from(df.parse(BuildConfig.BUILD_TIME))

        buildTime!!.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
    } catch (_: Exception) {
        BuildConfig.BUILD_TIME
    }
}


private fun Context.copyToClipboard(label: String, content: String) {
    if (content.isBlank()) return

    try {
        val clipboard = getSystemService<ClipboardManager>()!!
        clipboard.setPrimaryClip(ClipData.newPlainText(label, content))

    } catch (_: Throwable) { }
}
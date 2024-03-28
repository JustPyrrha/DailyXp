package gay.pyrrha.dailyxp.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import dev.icerock.moko.resources.StringResource
import gay.pyrrha.dailyxp.core.design.icon.XpIcons
import gay.pyrrha.dailyxp.i18n.MR

enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconText: StringResource,
    val titleText: StringResource
) {
    Quests(
        selectedIcon = XpIcons.ViewStream,
        unselectedIcon = XpIcons.ViewStreamOutline,
        iconText = MR.strings.top_level_quests,
        titleText = MR.strings.top_level_quests,
    ),
    More(
        selectedIcon = XpIcons.MoreHoriz,
        unselectedIcon = XpIcons.MoreHoriz,
        iconText = MR.strings.top_level_more,
        titleText = MR.strings.top_level_more
    )
}

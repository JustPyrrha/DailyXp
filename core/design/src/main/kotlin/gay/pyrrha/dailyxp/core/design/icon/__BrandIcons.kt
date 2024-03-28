package gay.pyrrha.dailyxp.core.design.icon

import androidx.compose.ui.graphics.vector.ImageVector
import gay.pyrrha.dailyxp.core.design.icon.brandicons.Discord
import gay.pyrrha.dailyxp.core.design.icon.brandicons.Github
import kotlin.collections.List as ____KtList

public object BrandIcons

private var __All: ____KtList<ImageVector>? = null

public val BrandIcons.All: ____KtList<ImageVector>
  get() {
    if (__All != null) {
      return __All!!
    }
    __All= listOf(Discord, Github)
    return __All!!
  }

package gay.pyrrha.dailyxp.core.ui.widget.preference

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun SwitchPreferenceWidget(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    icon: ImageVector? = null,
    enabled: Boolean = true,
    checked: Boolean = false,
    onCheckedChange: (Boolean) -> Unit,
) {
    TextPreferenceWidget(
        title = title,
        subtitle = subtitle,
        icon = icon,
        widget = {
            Switch(
                checked = checked,
                onCheckedChange = null,
                modifier = Modifier.padding(start = TrailingWidgetBuffer),
                enabled = enabled
            )
        },
        onPreferenceClick = { if(enabled) { onCheckedChange(!checked) } },
        modifier = modifier
    )
}

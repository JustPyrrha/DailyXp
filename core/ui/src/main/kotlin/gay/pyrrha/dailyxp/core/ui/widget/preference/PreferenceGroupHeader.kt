package gay.pyrrha.dailyxp.core.ui.widget.preference

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.StringResource
import gay.pyrrha.dailyxp.core.design.stringResource

@Composable
fun PreferenceGroupHeaderWidget(
    title: StringResource
) {
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp, top = 14.dp)
    ) {
        Text(
            text = stringResource(title),
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(horizontal = PrefsHorizontalPadding),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

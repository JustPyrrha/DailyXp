package gay.pyrrha.dailyxp.feature.more

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import gay.pyrrha.dailyxp.core.design.theme.XpTheme
import gay.pyrrha.dailyxp.core.model.data.Theme
import gay.pyrrha.dailyxp.core.ui.DevicePreviews

@Composable
fun LogoHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(R.drawable.feature_more_ic_logo),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(vertical = 16.dp)
                .size(128.dp)
        )

        HorizontalDivider()
    }
}

@Composable
@DevicePreviews
private fun LogoHeaderPreview() {
    XpTheme(theme = Theme.DEFAULT) {
        Surface {
            LogoHeader()
        }
    }
}

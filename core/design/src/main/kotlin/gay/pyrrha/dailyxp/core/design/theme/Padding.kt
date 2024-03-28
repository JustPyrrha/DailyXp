package gay.pyrrha.dailyxp.core.design.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.unit.dp

class Padding {
    val extraLarge = 32.dp

    val large = 24.dp

    val medium = 16.dp

    val small = 8.dp

    val extraSmall = 4.dp
}

val MaterialTheme.padding: Padding
    get() = Padding()

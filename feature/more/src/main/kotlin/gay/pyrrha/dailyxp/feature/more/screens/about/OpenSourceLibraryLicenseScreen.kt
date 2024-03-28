package gay.pyrrha.dailyxp.feature.more.screens.about


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mikepenz.aboutlibraries.entity.Library

@Composable
fun OpenSourceLibraryLicenseScreen(
    library: Library,
    contentPadding: PaddingValues
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(contentPadding)
            .padding(16.dp)
    ) {
        Text(
            text = library.licenses.firstOrNull()?.licenseContent ?: "Unable to load license text.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

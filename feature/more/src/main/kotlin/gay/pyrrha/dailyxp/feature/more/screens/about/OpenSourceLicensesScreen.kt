package gay.pyrrha.dailyxp.feature.more.screens.about

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import com.mikepenz.aboutlibraries.entity.Library
import com.mikepenz.aboutlibraries.ui.compose.m3.LibrariesContainer
import gay.pyrrha.dailyxp.core.design.component.XpTopAppBar
import gay.pyrrha.dailyxp.core.design.icon.XpIcons
import gay.pyrrha.dailyxp.core.design.stringResource
import gay.pyrrha.dailyxp.i18n.MR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OpenSourceLicensesScreen(
    onBackClick: () -> Unit
) {
    val uriHandler = LocalUriHandler.current
    var selectedLibrary by remember { mutableStateOf<Library?>(null) }

    Scaffold(
        topBar = {
            XpTopAppBar(
                title = selectedLibrary?.name ?: stringResource(MR.strings.licenses),
                navigationIcon = XpIcons.BackArrow,
                onNavigationIconClick = {
                    if (selectedLibrary != null) {
                        selectedLibrary = null
                    } else {
                        onBackClick()
                    }
                },
                actions = {
                    if (selectedLibrary?.website != null) {
                        IconButton(onClick = { uriHandler.openUri(selectedLibrary!!.website!!) }) {
                            Icon(
                                imageVector = XpIcons.Public,
                                contentDescription = selectedLibrary!!.website!!
                            )
                        }
                    }
                }
            )
        }
    ) { padding ->
        if (selectedLibrary != null) {
            OpenSourceLibraryLicenseScreen(
                library = selectedLibrary!!,
                contentPadding = padding
            )
        } else {
            LibrariesContainer(
                modifier = Modifier.fillMaxSize(),
                contentPadding = padding,
                onLibraryClick = { selectedLibrary = it }
            )
        }
    }
}

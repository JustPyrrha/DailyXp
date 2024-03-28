package gay.pyrrha.dailyxp.core.design.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import dev.icerock.moko.resources.StringResource
import gay.pyrrha.dailyxp.core.design.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun XpCenterAlignedTopAppBar(
    title: StringResource,
    modifier: Modifier = Modifier,
    navigationIcon: ImageVector? = null,
    onNavigationIconClick: (() -> Unit)? = null,
    navigationIconContentDescription: String? = null,
    colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors()
) {
    CenterAlignedTopAppBar(
        title = { Text(text = stringResource(title)) },
        navigationIcon = {
            if (navigationIcon != null && onNavigationIconClick != null) {
                IconButton(onClick = onNavigationIconClick) {
                    Icon(
                        imageVector = navigationIcon,
                        contentDescription = navigationIconContentDescription,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        },
        colors = colors,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun XpTopAppBar(
    title: StringResource,
    navigationIcon: ImageVector,
    onNavigationIconClick: () -> Unit,
    modifier: Modifier = Modifier,
    navigationIconContentDescription: String? = null,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(),
    actions: @Composable (RowScope.() -> Unit)? = null
) {
    XpTopAppBar(
        title = stringResource(resource = title),
        navigationIcon = navigationIcon,
        onNavigationIconClick = onNavigationIconClick,
        modifier = modifier,
        navigationIconContentDescription = navigationIconContentDescription,
        colors = colors,
        actions = actions
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun XpTopAppBar(
    title: String,
    navigationIcon: ImageVector,
    onNavigationIconClick: () -> Unit,
    modifier: Modifier = Modifier,
    navigationIconContentDescription: String? = null,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(),
    actions: @Composable (RowScope.() -> Unit)? = null
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(
                    imageVector = navigationIcon,
                    contentDescription = navigationIconContentDescription,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        colors = colors,
        modifier = modifier,
        actions = { if(actions != null) actions() }
    )
}

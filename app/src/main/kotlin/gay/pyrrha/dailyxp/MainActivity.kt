package gay.pyrrha.dailyxp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import gay.pyrrha.dailyxp.core.data.util.NetworkMonitor
import gay.pyrrha.dailyxp.core.design.theme.XpTheme
import gay.pyrrha.dailyxp.core.model.data.Theme
import gay.pyrrha.dailyxp.core.model.data.ThemeMode
import gay.pyrrha.dailyxp.feature.onboarding.OnboardingScreen
import gay.pyrrha.dailyxp.ui.XpApp
import gay.pyrrha.dailyxp.ui.rememberXpAppState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

//    @Inject
//    lateinit var networkMonitor: NetworkMonitor

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        var uiState: MainActivityUiState by mutableStateOf(MainActivityUiState.Loading)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState
                    .onEach { uiState = it }
                    .collect()
            }
        }

        splashScreen.setKeepOnScreenCondition {
            when (uiState) {
                MainActivityUiState.Loading -> true
                is MainActivityUiState.Success -> false
            }
        }

        enableEdgeToEdge()

        setContent {
            val darkTheme = shouldUseDarkTheme(uiState)

            DisposableEffect(darkTheme) {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.auto(
                        android.graphics.Color.TRANSPARENT,
                        android.graphics.Color.TRANSPARENT
                    ) { darkTheme },
                    navigationBarStyle = SystemBarStyle.auto(
                        lightScrim,
                        darkScrim
                    ) { darkTheme },
                )

                onDispose {  }
            }

            val appState = rememberXpAppState(
                windowSizeClass = calculateWindowSizeClass(this),
//                networkMonitor = networkMonitor
            )

            XpTheme(
                theme = getAppTheme(uiState),
                darkTheme = darkTheme,
                amoledDark = shouldUseAmoledDarkMode(uiState)
            ) {
                if(shouldHideOnboarding(uiState)) {
                    XpApp(appState)
                } else {
                    OnboardingScreen(onComplete = {
                        viewModel.setShouldHideOnboarding(true)
                    })
                }
            }
        }
    }
}

@Composable
private fun shouldUseDarkTheme(
    uiState: MainActivityUiState,
): Boolean = when (uiState) {
    MainActivityUiState.Loading -> isSystemInDarkTheme()
    is MainActivityUiState.Success -> when (uiState.userData.theme) {
        Theme.DEFAULT,
        Theme.TRANS_PRIDE,
        Theme.DYNAMIC -> when (uiState.userData.themeMode) {
            ThemeMode.SYSTEM -> isSystemInDarkTheme()
            ThemeMode.LIGHT -> false
            ThemeMode.DARK -> true
        }

        // some themes require specific modes
        Theme.LATTE -> false
        Theme.FRAPPE,
        Theme.MACCHIATO,
        Theme.MOCHA -> true
    }
}

@Composable
private fun shouldHideOnboarding(
    uiState: MainActivityUiState
): Boolean = when (uiState) {
    MainActivityUiState.Loading -> false
    is MainActivityUiState.Success -> uiState.userData.hideOnboarding
}

@Composable
private fun getAppTheme(
    uiState: MainActivityUiState
) : Theme = when (uiState) {
    MainActivityUiState.Loading -> Theme.DEFAULT
    is MainActivityUiState.Success -> uiState.userData.theme
}

@Composable
private fun shouldUseAmoledDarkMode(
    uiState: MainActivityUiState
): Boolean = when (uiState) {
    MainActivityUiState.Loading -> false
    is MainActivityUiState.Success -> uiState.userData.amoledDark
}

private val lightScrim = android.graphics.Color.argb(0xe6, 0xFF, 0xFF, 0xFF)
private val darkScrim = android.graphics.Color.argb(0x80, 0x1b, 0x1b, 0x1b)

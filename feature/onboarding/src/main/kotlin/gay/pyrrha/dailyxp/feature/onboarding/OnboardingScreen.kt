package gay.pyrrha.dailyxp.feature.onboarding

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import gay.pyrrha.dailyxp.core.design.icon.XpIcons
import gay.pyrrha.dailyxp.core.design.theme.padding
import gay.pyrrha.dailyxp.core.model.data.Theme
import gay.pyrrha.dailyxp.core.model.data.ThemeMode
import gay.pyrrha.dailyxp.core.design.stringResource
import gay.pyrrha.dailyxp.core.ui.screen.InfoScreen
import gay.pyrrha.dailyxp.i18n.MR
import soup.compose.material.motion.animation.materialSharedAxisX
import soup.compose.material.motion.animation.rememberSlideDistance

@Composable
fun OnboardingScreen(
    onComplete: () -> Unit,
    viewModel: OnboardingScreenViewModel = hiltViewModel()
) {
    val uiState by viewModel.onboardingUiState.collectAsStateWithLifecycle()
    OnboardingScreen(
        onComplete = onComplete,
        onboardingUiState = uiState,
        onChangeThemeMode = viewModel::updateThemeMode,
        onChangeTheme = viewModel::updateTheme
    )
}

@Composable
fun OnboardingScreen(
    onComplete: () -> Unit,
    onboardingUiState: OnboardingUiState,
    onChangeThemeMode: (ThemeMode) -> Unit,
    onChangeTheme: (Theme) -> Unit
) {
    val slideDistance = rememberSlideDistance()

    var currentStep by rememberSaveable { mutableIntStateOf(0) }
    val steps = remember {
        listOf(
            ThemeStep()
        )
    }
    val isLastStep = currentStep == steps.lastIndex

    BackHandler(enabled = currentStep != 0, onBack = { currentStep-- })

    InfoScreen(
        icon = XpIcons.RocketLaunch,
        headingText = stringResource(MR.strings.onboarding_heading),
        subheadingText = stringResource(MR.strings.onboarding_description),
        acceptText = stringResource(
            if (isLastStep) {
                MR.strings.onboarding_action_finish
            } else {
                MR.strings.onboarding_action_next
            }
        ),
        canAccept = steps[currentStep].isComplete,
        onAcceptClick = {
            if (isLastStep) {
                onComplete()
            } else {
                currentStep++
            }
        }
    ) {
        Box(
            modifier = Modifier
                .padding(vertical = MaterialTheme.padding.small)
                .clip(MaterialTheme.shapes.small)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            AnimatedContent(
                targetState = currentStep,
                transitionSpec = {
                    materialSharedAxisX(
                        forward = targetState > initialState,
                        slideDistance = slideDistance
                    )
                },
                label = "stepContent"
            ) {
                steps[it].Content(
                    onboardingUiState,
                    onChangeThemeMode,
                    onChangeTheme
                )
            }
        }
    }
}

package gay.pyrrha.dailyxp.core.design.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.progressSemantics
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SegmentedProgressIndicator(
    progress: Float,
    numberOfSegments: Int,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    backgroundColor: Color = color.copy(alpha = BackgroundOpacity),
    progressHeight: Dp = ProgressHeight,
    segmentGap: Dp = SegmentGap,
    reversed: Boolean = false
) {
    check(progress in 0f..1f) { "Invalid progress $progress" }
    check(numberOfSegments > 0) { "Number of segments must be greater than 0" }

    val gap: Float
    val barHeight: Float

    with(LocalDensity.current) {
        gap = segmentGap.toPx()
        barHeight = progressHeight.toPx()
    }

    Canvas(
        modifier = modifier
            .progressSemantics(progress)
            .height(progressHeight)
    ) {
        drawSegments(1f, backgroundColor, barHeight, numberOfSegments, gap, reversed)
        drawSegments(progress, color, barHeight, numberOfSegments, gap, reversed)
    }
}

private fun DrawScope.drawSegments(
    progress: Float,
    color: Color,
    segmentsHeight: Float,
    segments: Int,
    segmentGap: Float,
    reversed: Boolean,
) {
    val width = size.width
    val gaps = (segments - 1) * segmentGap
    val segmentWidth = (width - gaps) / segments
    val barsWidth = segmentWidth * segments
    val start: Float
    val end: Float

    if (reversed) {
        start = width
        end = (width - (barsWidth * progress + (progress * (segments - 1)).toInt() * segmentGap))
    } else {
        start = 0f
        end = barsWidth * progress + (progress * segments).toInt() * segmentGap
    }

    repeat(segments) { index ->
        val offset = index * (segmentWidth + segmentGap)
        val segmentStart: Float
        val segmentEnd: Float

        if(reversed) {
            segmentStart = width - offset
            segmentEnd = (segmentStart - segmentWidth).coerceAtLeast(end)
        } else {
            segmentStart = start + offset
            segmentEnd = (segmentStart + segmentWidth).coerceAtMost(end)

        }

        if (!reversed && offset <= end || reversed && segmentEnd > end) {
            drawRoundRect(
                color,
                Offset(segmentStart, 0f),
                Size(segmentEnd - segmentStart, segmentsHeight),
                cornerRadius = CornerRadius(4f)
            )
        }
    }
}

private const val BackgroundOpacity = 0.25f
private val ProgressHeight = 4.dp
private val SegmentGap = 8.dp

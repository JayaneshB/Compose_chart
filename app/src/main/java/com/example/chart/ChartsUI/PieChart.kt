package com.example.chart.ChartsUI

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.unit.dp
import kotlin.math.min


@Composable
fun PieChart(
    modifier: Modifier,
    progress: List<Float>,
    colors: List<Color>
) {
    if (progress.isEmpty() || progress.size != colors.size) return

    val totalSum = progress.sum()
    val portions = progress.map {
        it * 100 / totalSum
    }
    val angleProgress = portions.map {
        360 * it / 100
    }

    var startAngle = 270f

    BoxWithConstraints(modifier = modifier) {
        val sideSize = min(constraints.maxWidth - 10f, constraints.maxHeight - 10f)
        val padding = (sideSize * 20) / 100f // Fixed padding for pie chart

        val pathPortion = remember {
            Animatable(initialValue = 0f)
        }

        LaunchedEffect(key1 = true) {
            pathPortion.animateTo(
                1f, animationSpec = tween(1000)
            )
        }

        val size = Size(sideSize.toFloat() - padding, sideSize.toFloat() - padding)

        Canvas(
            modifier = Modifier
                .width(sideSize.dp)
                .height(sideSize.dp)
        ) {
            angleProgress.forEachIndexed { index, value ->
                drawSlice(
                    color = colors[index],
                    startAngle = startAngle,
                    sweepAngle = value * pathPortion.value,
                    size = size,
                    padding = padding
                )
                startAngle += value
            }
        }
    }
}

fun DrawScope.drawSlice(
    color: Color,
    startAngle: Float,
    sweepAngle: Float,
    size: Size,
    padding: Float
) {
    drawArc(
        color = color,
        startAngle = startAngle,
        sweepAngle = sweepAngle,
        useCenter = true, // Always use center to draw filled slices
        size = size,
        style = Fill, // Always filled for a pie chart
        topLeft = Offset(padding / 2, padding / 2)
    )
}

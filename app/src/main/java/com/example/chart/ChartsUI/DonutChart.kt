package com.example.chart.ChartsUI


import android.graphics.Paint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlin.math.atan2
import kotlin.math.min
import kotlin.math.roundToInt

@Composable
fun DonutChart(
    modifier: Modifier,
    progress: List<Float>,
    colors: List<Color>,
    percentColor: Color = Color.Black
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
    val progressSize = mutableListOf<Float>()
    progressSize.add(angleProgress.first())
    for (x in 1 until angleProgress.size)
        progressSize.add(angleProgress[x] + progressSize[x - 1])
    var activePie by remember {
        mutableStateOf(-1)
    }
    BoxWithConstraints(modifier = modifier) {
        val sideSize = min(constraints.maxWidth - 10f, constraints.maxHeight - 10f)
        val padding = (sideSize * 20) / 100f
        val pathPortion = remember {
            Animatable(initialValue = 0f)
        }
        LaunchedEffect(key1 = true) {
            pathPortion.animateTo(
                1f, animationSpec = tween(1000)
            )
        }
        val size = Size(sideSize - padding, sideSize - padding)
        Canvas(
            modifier = Modifier
                .width(sideSize.dp)
                .height(sideSize.dp)
                .pointerInput(true) {
                    detectTapGestures {
                        val clickedAngle = convertTouchEventPointToAngle(
                            sideSize,
                            sideSize,
                            it.x,
                            it.y
                        )
                        progressSize.forEachIndexed { index, item ->
                            if (clickedAngle <= item) {
                                activePie = if (activePie == index) -1 else index
                                return@detectTapGestures
                            }
                        }
                    }
                }
        ) {
            angleProgress.forEachIndexed { index, value ->
                drawSlice(
                    colors[index],
                    startAngle,
                    value * pathPortion.value,
                    size,
                    padding = padding,
                    isActive = activePie == index
                )
                startAngle += value
            }
            if (activePie != -1) {
                drawContext.canvas.nativeCanvas.apply {
                    val fontSize = 60.toDp().toPx()
                    drawText(
                        "${portions[activePie].roundToInt()}%",
                        sideSize / 2f, // Center X
                        sideSize / 2f + fontSize / 3, // Center Y with slight offset
                        Paint().apply {
                            color = percentColor.toArgb()
                            textSize = fontSize
                            textAlign = Paint.Align.CENTER
                        }
                    )
                }
            } else {
                drawContext.canvas.nativeCanvas.apply {
                    val fontSize = 60.toDp().toPx()
                    drawText(
                        "Donut Chart",
                        sideSize / 2f,
                        sideSize / 2f + fontSize / 3,
                        Paint().apply {
                            color = Color.Black.toArgb()
                            textSize = fontSize
                            textAlign = Paint.Align.CENTER
                        }
                    )
                }
            }
        }
    }
}

fun DrawScope.drawSlice(
    color: Color,
    startAngle: Float,
    sweepAngle: Float,
    size: Size,
    padding: Float,
    isActive: Boolean = false
) {
    drawArc(
        color = color,
        startAngle = startAngle,
        sweepAngle = sweepAngle,
        useCenter = false,
        size = size,
        style = Stroke(
            width = if (isActive) 150f else 130f,
        ),
        topLeft = Offset(padding / 2, padding / 2)
    )
}

private fun convertTouchEventPointToAngle(
    width: Float,
    height: Float,
    xPos: Float,
    yPos: Float
): Double {
    var x = xPos - (width * 0.5f)
    val y = yPos - (height * 0.5f)
    var angle = Math.toDegrees(atan2(y.toDouble(), x.toDouble()) + Math.PI / 2)
    angle = if (angle < 0) angle + 360 else angle
    return angle
}


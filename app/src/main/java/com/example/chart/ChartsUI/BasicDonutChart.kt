package com.example.chart.ChartsUI


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun BasicDonutChart(
    progress: List<Float>,
    colors: List<Color>,
) {
    val totalSum = progress.sum()
    val portions = progress.map {
        it * 100 / totalSum
    }
    val angleProgress = portions.map {
        360 * it / 100
    }
    var startAngle = 270f

    Canvas(modifier = Modifier.fillMaxSize()) {
        val padding = 500f
        val outerCircleStrokeWidth = 30f
        val innerCircleStrokeWidth = outerCircleStrokeWidth - padding
        val outerCircleRadius = (size.minDimension - padding) / 2
        val innerCircleRadius = outerCircleRadius - outerCircleStrokeWidth / 2

        // Draw the outer grey circle
        drawCircle(
            color = Color(0xFFEBEBEB),
            radius = outerCircleRadius,
            center = center,
            style = Stroke(width = outerCircleStrokeWidth)
        )

        // Draw the donut slices
        angleProgress.forEachIndexed { index, sweepAngle ->
            drawSlice(
                color = colors[index],
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                radius = innerCircleRadius,
                strokeWidth = innerCircleStrokeWidth
            )
            startAngle += sweepAngle
        }
    }
}

fun DrawScope.drawSlice(
    color: Color,
    startAngle: Float,
    sweepAngle: Float,
    radius: Float,
    strokeWidth: Float
) {
    val size = Size(2 * radius, 2 * radius)
    val topLeft = Offset(center.x - radius, center.y - radius)

    drawArc(
        color = color,
        startAngle = startAngle,
        sweepAngle = sweepAngle,
        useCenter = false,
        size = size,
        style = Stroke(width = strokeWidth),
        topLeft = topLeft
    )
}



fun DrawScope.DrawSlice(
    color: Color,
    startAngle: Float,
    sweepAngle: Float,

    ) {
    val padding = 500f

    drawArc(
        color = color,
        startAngle = startAngle,
        sweepAngle = sweepAngle,
        useCenter = false,
        size = Size(width = size.width - padding, height = size.width - padding),
        style = Stroke(width = 150f),
        topLeft = Offset(padding / 2f, padding / 2f)
    )

}

@Preview
@Composable
fun PreviewDonutChart() {
    BasicDonutChart(
        progress = listOf(20f, 60f, 10f),
        colors = listOf(Color.Red, Color.Green, Color.Cyan)
    )
}
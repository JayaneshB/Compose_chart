package com.example.chart.ChartsUI

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chart.WS
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun HalfSemiChart(
    segments: List<WS>,
) {
    val totalPercentage = segments.sumOf { it.percentage }
    val normalizationFactor = if (totalPercentage == 0) 1f else 100f / totalPercentage

    val pathPortion = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        pathPortion.snapTo(0f)
        pathPortion.animateTo(1f, animationSpec = tween(1000)) // Animate all segments together
    }

    Box(
        modifier = Modifier
            .offset(y = 100.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .size(200.dp)
                .fillMaxWidth()
                .align(Alignment.Center)
        ) {
            var startAngle = 180f // Start at the top of the circle
            val strokeWidth = 80f
            val radius = size.minDimension / 2 // Radius of the chart

            // Draw all segments smoothly in one go
            segments.forEach { segment ->
                val normalizedPercentage = segment.percentage * normalizationFactor
                val sweepAngle = (180 * normalizedPercentage / 100) * pathPortion.value // Scale sweep angle during animation

                drawArc(
                    color = segment.color,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
                )

                // Calculate label position
                val angle = Math.toRadians((startAngle + sweepAngle / 2).toDouble())
                val labelRadius = radius + 130
                val x = center.x + labelRadius * cos(angle).toFloat()
                val y = center.y + labelRadius * sin(angle).toFloat()

                // Draw text labels
                if (segment.percentage != 0) {
                    drawContext.canvas.nativeCanvas.apply {
                        val textPaint = android.graphics.Paint().apply {
                            color = android.graphics.Color.parseColor("#6E6E72")
                            textAlign = android.graphics.Paint.Align.CENTER
                            textSize = 30f
                        }

                        val valueTextStyle = android.graphics.Paint().apply {
                            color = android.graphics.Color.BLACK
                            textAlign = android.graphics.Paint.Align.CENTER
                            textSize = 40f
                            typeface = android.graphics.Typeface.DEFAULT_BOLD
                        }
                        drawText(segment.label, x, y, textPaint)
                        drawText("${segment.percentage}%", x, y + 50, valueTextStyle)
                    }
                }

                startAngle += sweepAngle // Move to the next segment
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.offset(x = 0.dp, y = (-10).dp)
        ) {
            Text(
                text = "Overall Wealth",
                style = TextStyle(fontSize = 16.sp, color = Color.Black)
            )
            Text(
                text = "₹40Cr",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            )
        }
    }
}



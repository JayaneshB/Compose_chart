package com.example.chart.ChartsUI

import android.util.Log
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


//@Composable
//fun HalfSemiChart(
//    segments: List<WS>,
//) {
//    val totalPercentage = segments.sumOf { it.percentage }
//    val normalizationFactor = if (totalPercentage == 0) 1f else 100f / totalPercentage
//
//    val pathPortion = remember { Animatable(0f) }
//
//
////    Animation for Segments
////    LaunchedEffect(segments) {
////        pathPortion.snapTo(0f)
////        pathPortion.animateTo(1f, animationSpec = tween(1000))
////    }
//
//    // Animation for Entire chart
//    LaunchedEffect(Unit) {
//        pathPortion.snapTo(0f)
//        pathPortion.animateTo(1f, animationSpec = tween(1000)) // Animate all segments together
//    }
//
//    Box(
//        modifier = Modifier
//            .offset(y = 100.dp)
//            .fillMaxWidth(),
//        contentAlignment = Alignment.Center
//    ) {
//        Canvas(
//            modifier = Modifier
//                .size(200.dp)
//                .fillMaxWidth()
//                .align(Alignment.Center)
//        ) {
//            var startAngle = 180f // Start at the top of the circle
//            val strokeWidth = 80f
//            val radius = size.minDimension / 2 // Radius of the chart
//
//            // Draw arc segments
//            segments.forEach { segment ->
//                val normalizedPercentage = segment.percentage * normalizationFactor
//                val sweepAngle = (180 * normalizedPercentage / 100) * pathPortion.value
//
//                drawArc(
//                    color = segment.color,
//                    startAngle = startAngle,
//                    sweepAngle = sweepAngle * pathPortion.value,
//                    useCenter = false,
//                    style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
//                )
//
//
////                drawArc(
////                    color = segment.color,
////                    startAngle = startAngle,
////                    sweepAngle = sweepAngle,
////                    useCenter = false,
////                    style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
////                )
//
//                // Calculate label position
//                val angle = Math.toRadians((startAngle + sweepAngle / 2).toDouble())
//                val labelRadius = radius + 130
//                val x = center.x + labelRadius * cos(angle).toFloat()
//                val y = center.y + labelRadius * sin(angle).toFloat()
//
//                // Draw the text label
//                if (segment.percentage != 0) {
//                    drawContext.canvas.nativeCanvas.apply {
//                        val textPaint = android.graphics.Paint().apply {
//                            color = android.graphics.Color.parseColor("#6E6E72")
//                            textAlign = android.graphics.Paint.Align.CENTER
//                            textSize = 30f
//                        }
//
//                        val valueTextStyle = android.graphics.Paint().apply {
//                            color = android.graphics.Color.BLACK
//                            textAlign = android.graphics.Paint.Align.CENTER
//                            textSize = 40f
//                            typeface = android.graphics.Typeface.DEFAULT_BOLD
//                        }
//                        drawText(segment.label, x, y, textPaint)
//                        drawText("${segment.percentage}%", x, y + 50, valueTextStyle)
//                    }
//                }
//                startAngle += sweepAngle
//            }
//        }
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            modifier = Modifier.offset(x = 0.dp, y = (-20).dp)
//        ) {
//            Text(
//                text = "Overall Wealth",
//                style = TextStyle(fontSize = 16.sp, color = Color.Black)
//            )
//            Text(
//                text = "₹40Cr",
//                style = TextStyle(
//                    fontSize = 20.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = Color.Black
//                )
//            )
//        }
//    }
//}


@Composable
fun HalfSemiChart(segments: List<WS>) {
    val totalPercentage = segments.sumOf { it.percentage } // Keep it Double
    val normalizationFactor = if (totalPercentage == 0.0) 1.0 else 100.0 / totalPercentage // Convert 100f to 100.0

    val minSweepAngle = 20.0 // Convert to Double

    val adjustedSegments = segments.map { segment ->
        val normalizedPercentage = segment.percentage * normalizationFactor
        val sweepAngle = (180.0 * normalizedPercentage / 100.0) // Convert 180f to 180.0

        val adjustedSweepAngle = if (segment.percentage < 1) {
            minSweepAngle
        } else {
            maxOf(sweepAngle, minSweepAngle) // Ensure sweepAngle is at least minSweepAngle
        }
        segment to adjustedSweepAngle
    }


    val totalAdjustedSweep = adjustedSegments.sumOf { it.second }
    val scaleFactor = if (totalAdjustedSweep > 180.0) 180.0 / totalAdjustedSweep else 1.0

    val finalSegments = adjustedSegments.map { (segment, sweepAngle) ->
        segment to sweepAngle * scaleFactor
    }

    val pathPortion = remember { Animatable(0f) }

    LaunchedEffect(segments) {
        pathPortion.snapTo(0f)
        pathPortion.animateTo(1f, animationSpec = tween(1000))
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
            var startAngle = 180f
            val strokeWidth = 80f
            val radius = size.minDimension / 2

            finalSegments.forEach { (segment, sweepAngle) ->
                drawArc(
                    color = segment.color,
                    startAngle = startAngle,
                    sweepAngle = (sweepAngle * pathPortion.value).toFloat(),
                    useCenter = false,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
                )

                // Calculate label position
                val angle = Math.toRadians((startAngle + sweepAngle / 2).toDouble())
                val labelRadius = radius + 130
                val x = center.x + labelRadius * cos(angle).toFloat()
                val y = center.y + labelRadius * sin(angle).toFloat()

                // Draw text labels
                if (segment.percentage > 0) {
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

                startAngle += sweepAngle.toFloat()
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.offset(x = 0.dp, y = (-20).dp)
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




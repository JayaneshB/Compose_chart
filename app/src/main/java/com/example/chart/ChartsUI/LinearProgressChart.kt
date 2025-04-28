package com.example.chart.ChartsUI

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LinearProgressChart(
    credits: Float,
    debits: Float,
    modifier: Modifier = Modifier,
    progressColor: Color = Color(0xFF4CAF50),
    backgroundColor: Color = Color(0xFFFFAB91),
    animationDuration: Int = 1000
) {
    val surplus = credits - debits
    val percentage = ((surplus / credits) * 100).toInt()
    
    val animatedProgress = remember { Animatable(0f) }
    
    LaunchedEffect(credits, debits) {
        animatedProgress.animateTo(
            targetValue = credits / (credits + debits),
            animationSpec = tween(
                durationMillis = animationDuration,
                easing = FastOutSlowInEasing
            )
        )
    }
    
    Column(modifier = modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Monthly Surplus ₹${String.format("%.1f", surplus)}L",
                fontSize = 16.sp,
                color = Color.Gray
            )
            Text(
                text = "(${percentage}%)",
                fontSize = 16.sp,
                color = progressColor
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(animatedProgress.value)
                    .height(16.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(progressColor)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(backgroundColor)
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(progressColor)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Credits ₹${String.format("%.1f", credits)}L",
                    fontSize = 16.sp
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(backgroundColor)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Debits ₹${String.format("%.1f", debits)}L",
                    fontSize = 16.sp
                )
            }
        }
    }
}

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
    creditPercentage: Float,
    debitPercentage: Float,
    creditValue: String = "",
    debitValue: String = "",
    modifier: Modifier = Modifier,
    progressColor: Color = Color(0xFF4CAF50),
    backgroundColor: Color = Color(0xFFFFAB91),
    animationDuration: Int = 1000
) {
    // Validate and adjust percentages if they exceed 100%
    val validatedCreditPercentage = creditPercentage.coerceIn(0f, 100f)
    val validatedDebitPercentage = debitPercentage.coerceIn(0f, 100f - validatedCreditPercentage)
    
    val surplus = validatedCreditPercentage - validatedDebitPercentage
    
    val animatedProgress = remember { Animatable(0f) }
    
    LaunchedEffect(validatedCreditPercentage, validatedDebitPercentage) {
        animatedProgress.animateTo(
            targetValue = when {
                validatedDebitPercentage == 0f -> 1f
                validatedCreditPercentage == 0f -> 0f
                else -> validatedCreditPercentage / (validatedCreditPercentage + validatedDebitPercentage)
            },
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
                text = "Monthly Surplus ${if (creditValue.isNotEmpty()) creditValue else "${surplus.toInt()}%"}",
                fontSize = 16.sp,
                color = Color.Gray
            )
            Text(
                text = "(${surplus.toInt()}%)",
                fontSize = 16.sp,
                color = progressColor
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(modifier = Modifier.fillMaxWidth()) {
            when {
                // Show full width green bar when only credit exists
                validatedDebitPercentage == 0f -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(16.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(progressColor)
                    )
                }
                // Show full width orange bar when only debit exists
                validatedCreditPercentage == 0f -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(16.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(backgroundColor)
                    )
                }
                // Show split view when both values exist
                else -> {
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
            }
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
                    text = "Credits ${if (creditValue.isNotEmpty()) creditValue else "${validatedCreditPercentage.toInt()}%"}",
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
                    text = "Debits ${if (debitValue.isNotEmpty()) debitValue else "${validatedDebitPercentage.toInt()}%"}",
                    fontSize = 16.sp
                )
            }
        }
    }
}

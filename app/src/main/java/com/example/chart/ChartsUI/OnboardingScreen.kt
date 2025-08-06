package com.example.chart.ChartsUI

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Preview
@Composable
fun OnboardingScreen() {
    val pages = listOf(
        OnboardingPage("Your money is scattered"),
        OnboardingPage("Introducing Money Vault"),
        OnboardingPage("Why does it matter?"),
        OnboardingPage("Is it safe though?"),
        OnboardingPage("One view your finance deserves", showButton = true)
    )

    var currentPage by remember { mutableStateOf(0) }

    // Auto-scroll
    LaunchedEffect(currentPage) {
        if (currentPage < pages.lastIndex) {
            delay(5000)
            currentPage++
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {

            Spacer(modifier = Modifier.height(48.dp))

            SegmentedProgressBar(
                totalSegments = pages.size,
                currentSegment = currentPage
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ✅ Dynamic Page Content
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                OnboardingPageView(
                    title = pages[currentPage].title,
                    showButton = pages[currentPage].showButton
                )
            }
        }

        // ✅ Tap Zones (Back/Next)
        Row(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        enabled = currentPage > 0
                    ) { currentPage-- }
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        enabled = currentPage < pages.lastIndex
                    ) { currentPage++ }
            )
        }
    }
}



@Composable
fun SegmentedProgressBar(
    totalSegments: Int,
    currentSegment: Int,
    segmentDurationMillis: Int = 5000
) {
    val animatedProgress = remember { Animatable(0f) }

    // Animate on current segment change
    LaunchedEffect(currentSegment) {
        animatedProgress.snapTo(0f)
        animatedProgress.animateTo(
            1f,
            animationSpec = tween(durationMillis = segmentDurationMillis)
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        repeat(totalSegments) { index ->
            val trackColor = Color.LightGray
            val progressColor = Color(0xFF00796B)

            val segmentModifier = Modifier
                .weight(1f)
                .height(6.dp)
                .clip(RoundedCornerShape(50))

            Box(
                modifier = segmentModifier
                    .background(trackColor)
            ) {
                when {
                    index < currentSegment -> {
                        // Fully filled
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .clip(RoundedCornerShape(50))
                                .background(progressColor)
                        )
                    }

                    index == currentSegment -> {
                        // Animate only if progress > threshold (to avoid green dot)
                        if (animatedProgress.value > 0.05f) {
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(fraction = animatedProgress.value)
                                    .clip(RoundedCornerShape(50))
                                    .background(progressColor)
                            )
                        }
                    }

                    // Remaining segments: only background
                }
            }
        }
    }
}

@Composable
fun OnboardingPageView(
    title: String,
    showButton: Boolean
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Center
        )

        if (showButton) {
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = { /* TODO: Navigate or perform action */ }) {
                Text("Activate Now")
            }
        }
    }
}


data class OnboardingPage(val title: String, val showButton: Boolean = false)

package com.example.chart.ChartsUI

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.waitForUpOrCancellation
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

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

    LaunchedEffect(key1 = currentPage) {
        if (currentPage < pages.lastIndex) {
            delay(5000)
            currentPage++
        }
    }
    Column(modifier = Modifier.fillMaxSize()) {

        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close",
                modifier = Modifier.clickable { /* Dismiss the screen logic */ }
            )
            if (currentPage != pages.lastIndex) {
                Text(
                    text = "Skip",
                    modifier = Modifier.clickable {
                        currentPage = pages.lastIndex
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Main Content
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                SegmentedProgressBar(
                    totalSegments = pages.size,
                    currentSegment = currentPage
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Dynamic Page Content
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

            // Tap Zones (Back/Next)
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
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .clip(RoundedCornerShape(50))
                                .background(progressColor)
                        )
                    }

                    index == currentSegment -> {
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 50.dp,horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            maxLines = 2,
            softWrap = true
        )

        if (showButton) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Activate Now",
                modifier = Modifier
                    .background(Color(0xFF6A1B9A), shape = RoundedCornerShape(50))
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                color = Color.White
            )
        }
    }

}


@Preview
@Composable
fun ViewPagerOnboardingScreen() {
    val pages = listOf(
        OnboardingPage("Your money is scattered"),
        OnboardingPage("Introducing Money Vault"),
        OnboardingPage("Why does it matter?"),
        OnboardingPage("Is it safe though?"),
        OnboardingPage("One view your finance deserves", showButton = true)
    )

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = { pages.size }
    )
    var isAutoScrollPaused by remember { mutableStateOf(false) }
    var isPaused by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    /**
     *  Scroll happens proeprly but without animation, slightly abruptly
     */

//    LaunchedEffect(Unit) {
//        while (true) {
//            delay(5000)
//            if (!isAutoScrollPaused && pagerState.currentPage < pages.lastIndex) {
//                pagerState.scrollToPage(pagerState.currentPage + 1)
//            }
//        }
//    }

    /**
     *  this looks good and clear
     */

    LaunchedEffect(Unit) {
        delay(2000)
        while (true) {
            delay(5000)
            if (!isAutoScrollPaused && pagerState.currentPage < pages.lastIndex) {
                try {
                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                } catch (_: CancellationException) {
                    // Safe to ignore
                }
            }
        }
    }

    /**
     *  Disturbs the animation and alignment
     */

//    LaunchedEffect(pagerState.currentPage, isAutoScrollPaused) {
//        if (!isAutoScrollPaused && pagerState.currentPage < pages.lastIndex) {
//            delay(5000)
//            pagerState.animateScrollToPage(pagerState.currentPage + 1)
//        }
//    }

    // UI
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    modifier = Modifier.clickable {

                    }
                )

                if (pagerState.currentPage != pages.lastIndex) {
                    Text(
                        text = "Skip",
                        modifier = Modifier.clickable {
                            coroutineScope.launch {
                                pagerState.scrollToPage(pages.lastIndex)
                            }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Progress Bar
            NewSegmentedProgressBar(
                totalSegments = pages.size,
                currentSegment = pagerState.currentPage,
                isPaused = isPaused
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Pager with tap zones and long-press handling
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) { page ->
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        // Page content
                        OnboardingPageView(
                            title = pages[page].title,
                            showButton = pages[page].showButton
                        )

                        Row(modifier = Modifier.fillMaxSize()) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                                    .pointerInput(Unit) {
                                        while (true) {
                                            awaitPointerEventScope {
                                                val down = awaitFirstDown()
                                                isPaused = true
                                                isAutoScrollPaused = true
                                                val up = waitForUpOrCancellation()
                                                isPaused = false
                                                isAutoScrollPaused = false

                                                if (up != null) {
                                                    if (pagerState.currentPage > 0) {
                                                        coroutineScope.launch {
                                                            pagerState.animateScrollToPage(
                                                                pagerState.currentPage - 1
                                                            )
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                            )
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                                    .pointerInput(Unit) {
                                        while (true) {
                                            awaitPointerEventScope {
                                                val down = awaitFirstDown()
                                                isPaused = true
                                                isAutoScrollPaused = true
                                                val up = waitForUpOrCancellation()
                                                isPaused = false
                                                isAutoScrollPaused = false

                                                if (up != null) {
                                                    if (pagerState.currentPage < pages.lastIndex) {
                                                        coroutineScope.launch {
                                                            pagerState.animateScrollToPage(
                                                                pagerState.currentPage + 1
                                                            )
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                            )
                        }
                    }
                }
            }

        }
    }
}

// Progress bar with animation
@Composable
fun NewSegmentedProgressBar(
    totalSegments: Int,
    currentSegment: Int,
    segmentDurationMillis: Int = 5000,
    isPaused: Boolean
) {
    val animatedProgress = remember { Animatable(0f) }

    // Time tracking for pause/resume
    var lastUpdateTime by remember { mutableStateOf(System.currentTimeMillis()) }
    var progressFraction by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(currentSegment) {
        // Reset state on page change
        progressFraction = 0f
        animatedProgress.snapTo(0f)
        lastUpdateTime = System.currentTimeMillis()
    }

    LaunchedEffect(isPaused, currentSegment) {
        while (progressFraction < 1f && !isPaused) {
            val currentTime = System.currentTimeMillis()
            val delta = currentTime - lastUpdateTime
            lastUpdateTime = currentTime

            val progressDelta = delta.toFloat() / segmentDurationMillis
            progressFraction += progressDelta
            animatedProgress.snapTo(progressFraction.coerceAtMost(1f))

            delay(16) // ~60 FPS
        }
    }

    // UI
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

            Box(modifier = segmentModifier.background(trackColor)) {
                when {
                    index < currentSegment -> {
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .clip(RoundedCornerShape(50))
                                .background(progressColor)
                        )
                    }

                    index == currentSegment -> {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(animatedProgress.value)
                                .clip(RoundedCornerShape(50))
                                .background(progressColor)
                        )
                    }
                }
            }
        }
    }
}


data class OnboardingPage(val title: String, val showButton: Boolean = false)

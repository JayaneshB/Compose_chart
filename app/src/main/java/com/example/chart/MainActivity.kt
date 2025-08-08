package com.example.chart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.chart.ChartsUI.DonutChart
import com.example.chart.ChartsUI.HalfSemiChart
import com.example.chart.ChartsUI.LinearProgressChart
import com.example.chart.ChartsUI.PeriodPicker
import com.example.chart.ChartsUI.ViewPagerOnboardingScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
//            ShowChart()

//            LinearProgressChart(
//                creditPercentage = 60f,  // 2.5L
//                debitPercentage = 40f,   // 1.0L
//                modifier = Modifier.padding(16.dp)
//            )

            var showPicker by remember { mutableStateOf(false) }

            /*PeriodPicker(
                showPicker = showPicker,
                onDismiss = { showPicker = false },
                onPeriodSelected = { selectedMonth ->
                    // Handle selected month
                    showPicker = false
                }
            )

// To show the picker:
            Button(onClick = { showPicker = true }) {
                Text("Show Period Picker")
            }*/
            /*PieChart(modifier = Modifier
                       .weight(1f)
                       .fillMaxWidth(),
                       progress = listOf(14f, 21f, 60f),
                       colors = listOf(
                           Color(0xFFbf95d4),
                           Color(0xFFf4ac1a),
                           Color(0xFF8b0a50),
                       )
                   )*/

            /*BasicDonutChart(
                progress = listOf(20f, 60f, 10f),
                colors = listOf(Color.Red, Color.Green, Color.Cyan)
            )*/

            ViewPagerOnboardingScreen()

//            Column {
//                BoxWithConstraints(
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    val chartSize = maxWidth * 0.5f  // 40% of available width
//
//                    Row(modifier = Modifier.fillMaxWidth()) {
//                        Spacer(modifier = Modifier.weight(1f))
//
//                        Row {
//                            DonutChart(
//                                modifier = Modifier.size(chartSize),
//                                progress = listOf(14f, 21f, 60f, 44f, 32f, 5f),
//                                colors = listOf(
//                                    Color(0xFFbf95d4),
//                                    Color(0xFFf4ac1a),
//                                    Color(0xFF8b0a50),
//                                    Color(0xFFF09480),
//                                    Color(0xFF7BE7F9),
//                                    Color(0xFFF7F727)
//                                )
//                            )
//                        }
//
//                    }
//                }
//            }


//            Column {
//                Row {
//                    Spacer(modifier = Modifier.weight(1f))
//
//                    DonutChart(
//                        modifier = Modifier
//                            .size(300.dp)
//                            .fillMaxWidth(),
//                        progress = listOf(14f, 21f, 60f,44f,32f,5f),
//                        colors = listOf(
//                            Color(0xFFbf95d4),
//                            Color(0xFFf4ac1a),
//                            Color(0xFF8b0a50),
//                            Color(0xFFF09480),
//                            Color(0xFF7BE7F9),
//                            Color(0xFFF7F727)
//                        )
//                    )
//                }
//            }
        }
    }
}

@Composable
fun ShowChart() {
    val assetSegments = listOf(
        WS("Equity", 5.5, Color.Magenta),
        WS("Debt", 2.2, Color.Yellow),
        WS("Hybrid", 3.3, Color.Cyan)
    )

    val productSegments = listOf(
        WS("Gold", 0.05, Color.Red),
        WS("Real Estate", 1.95, Color.Blue),
        WS("Commodities", 8.4, Color.Green),
        WS("ETF", 80.0, Color.Yellow)
    )

    Box(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(Color(0xFFF6FDFA), Color(0xFFF5F7FD))
                ),
                shape = RoundedCornerShape(20.dp)
            )
    ) {
        Card(
            modifier = Modifier.wrapContentHeight(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        ) {
            Column {
                TabSwitcher(
                    tabs = listOf("Asset", "Product")
                ) { selectedTab ->
                    when (selectedTab) {
                        "Asset" -> HalfSemiChart(assetSegments)
                        "Product" -> HalfSemiChart(productSegments)
                    }
                }
            }
        }
    }
}

data class WS(
    val label: String,
    val percentage: Double,
    val color: Color
)
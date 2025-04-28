package com.example.chart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.chart.ChartsUI.HalfSemiChart
import com.example.chart.ChartsUI.LinearProgressChart

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
//            ShowChart()

            LinearProgressChart(
                creditPercentage = 60f,  // 2.5L
                debitPercentage = 40f,   // 1.0L
                modifier = Modifier.padding(16.dp)
            )

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

            /*Column {
                DonutChart(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    progress = listOf(14f, 21f, 60f,44f,32f,5f),
                    colors = listOf(
                        Color(0xFFbf95d4),
                        Color(0xFFf4ac1a),
                        Color(0xFF8b0a50),
                        Color(0xFFF09480),
                        Color(0xFF7BE7F9),
                        Color(0xFFF7F727)
                    )
                )
            }*/
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
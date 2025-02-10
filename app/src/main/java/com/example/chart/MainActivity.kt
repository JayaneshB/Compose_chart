package com.example.chart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.chart.ChartsUI.HalfSemiChart

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShowChart()

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

            /*DonutChart(
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
            )*/
        }
    }
}

@Composable
fun ShowChart() {
    val assetSegments = listOf(
        WS("Equity", 50, Color.Magenta),
        WS("Debt", 20, Color.Yellow),
        WS("Hybrid", 30, Color.Cyan)
    )

    val productSegments = listOf(
        WS("Gold", 40, Color.Red),
        WS("Real Estate", 30, Color.Blue),
        WS("Commodities", 30, Color.Green)
    )

    Card(
        modifier = Modifier.wrapContentHeight(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F7E9)),
        elevation = CardDefaults.elevatedCardElevation(2.dp)
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

data class WS(
    val label: String,
    val percentage: Int,
    val color: Color
)
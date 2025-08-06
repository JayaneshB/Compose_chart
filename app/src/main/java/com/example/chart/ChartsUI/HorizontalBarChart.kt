import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun SegmentedBar(
    rawValues: List<Any>,
    colors: List<Color>,
    modifier: Modifier = Modifier,
    minPercentage: Float = 20f
) {
    // Step 1: Convert and pair values with colors
    val segments = rawValues.mapIndexedNotNull { index, item ->
        val value = when (item) {
            is Int -> item.toFloat()
            is Float -> item
            is Double -> item.toFloat()
            is String -> item.toFloatOrNull()
            else -> null
        } ?: return@mapIndexedNotNull null

        val color = colors.getOrNull(index) ?: Color.Gray
        value to color
    }.sortedByDescending { it.first }

    if (segments.isEmpty()) return

    // Step 2: Normalize values
    val total = segments.sumOf { it.first.toDouble() }.toFloat()
    if (total == 0f) return

    val normalizedSegments = segments.map { (value, color) ->
        SegmentData(
            value = value,
            normalized = value / total,
            color = color
        )
    }

    // Step 3: Apply minimum threshold logic
    val minFraction = minPercentage / 100f
    val small = normalizedSegments.filter { it.normalized < minFraction }
    val large = normalizedSegments.filter { it.normalized >= minFraction }

    val totalMin = small.size * minPercentage
    val remaining = 100f - totalMin
    val totalLarge = large.sumOf { it.normalized.toDouble() }.toFloat()

    val finalSegments = normalizedSegments.map {
        val percent = when {
            it in small -> minPercentage
            it in large -> (it.normalized / totalLarge) * remaining
            else -> 0f
        }
        it.copy(finalPercentage = percent)
    }

    // Step 4: Render UI
    Row(
        modifier = modifier.height(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        finalSegments.forEachIndexed { index, segment ->
            val weight = segment.finalPercentage / 100f
            val shape = when (index) {
                0 -> RoundedCornerShape(topStart = 5.dp, bottomStart = 5.dp)
                finalSegments.lastIndex -> RoundedCornerShape(topEnd = 5.dp, bottomEnd = 5.dp)
                else -> RoundedCornerShape(0.dp)
            }

            Box(
                modifier = Modifier
                    .weight(weight)
                    .fillMaxHeight()
                    .clip(shape)
                    .background(segment.color)
            )

            if (index != finalSegments.lastIndex) {
                Spacer(modifier = Modifier.width(5.dp))
            }
        }
    }
}

private data class SegmentData(
    val value: Float,
    val normalized: Float,
    val color: Color,
    val finalPercentage: Float = 0f
)



//@Composable
//fun SegmentedBar(
//    rawValues: List<Any>,
//    colors: List<Color>,
//    modifier: Modifier = Modifier,
//    minPercentage: Float = 20f
//) {
//    // Convert raw values to Float
//    val floatValues: List<Float> = rawValues.mapNotNull {
//        when (it) {
//            is Int -> it.toFloat()
//            is Float -> it
//            is Double -> it.toFloat()
//            is String -> it.toFloatOrNull()
//            else -> null
//        }
//    }
//
//    if (floatValues.isEmpty()) return
//
//    // Zip with colors and sort by value descending
//    val combined = floatValues.zip(colors).sortedByDescending { it.first }
//
//    val sortedValues = combined.map { it.first }
//    val sortedColors = combined.map { it.second }
//
//    val total = sortedValues.sum()
//    if (total == 0f) return
//
//    // Normalize values as fractions
//    val normalized = sortedValues.map { it / total }
//
//    // Separate small and large values based on threshold
//    val minFraction = minPercentage / 100f
//    val smallIndices = normalized.withIndex().filter { it.value < minFraction }.map { it.index }
//    val largeIndices = normalized.withIndex().filter { it.value >= minFraction }.map { it.index }
//
//    val totalMinPercent = smallIndices.size * minPercentage
//    val remainingPercent = 100f - totalMinPercent
//
//    val totalLargeFraction = largeIndices.sumOf { normalized[it].toDouble() }.toFloat()
//
//    // Final percentages after enforcing min threshold
//    val result = normalized.mapIndexed { index, fraction ->
//        when {
//            index in smallIndices -> minPercentage
//            index in largeIndices -> ((fraction / totalLargeFraction) * remainingPercent)
//            else -> 0f
//        }
//    }
//
//    // Render the segments
//    Row(
//        modifier = modifier.height(20.dp),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        result.forEachIndexed { index, percentage ->
//            val weight = percentage / 100f
//            val shape = when (index) {
//                0 -> RoundedCornerShape(topStart = 5.dp, bottomStart = 5.dp)
//                result.lastIndex -> RoundedCornerShape(topEnd = 5.dp, bottomEnd = 5.dp)
//                else -> RoundedCornerShape(0.dp)
//            }
//
//            Box(
//                modifier = Modifier
//                    .weight(weight)
//                    .fillMaxHeight()
//                    .clip(shape)
//                    .background(sortedColors.getOrElse(index) { Color.Gray })
//            )
//
//            if (index != result.lastIndex) {
//                Spacer(modifier = Modifier.width(5.dp))
//            }
//        }
//    }
//}


@Preview(showBackground = true)
@Composable
fun SegmentedBarPreview() {
    val mixedValues = listOf(100,900,50,5) // Any combination works
    val colors = listOf(
        Color(0xFF00BFA5),
        Color(0xFF00D4A6),
        Color(0xFF00E5C5),
        Color(0xFF00F2D1),
        Color(0xFF00FFE5)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFFF5F5F5), RoundedCornerShape(12.dp))
            .padding(8.dp)
    ) {
        SegmentedBar(
            rawValues = mixedValues,
            colors = colors,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

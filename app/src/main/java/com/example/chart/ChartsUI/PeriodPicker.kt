package com.example.chart.ChartsUI

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun PeriodPicker(
//    showPicker: Boolean,
//    onDismiss: () -> Unit,
//    onPeriodSelected: (String) -> Unit,
//    modifier: Modifier = Modifier
//) {
//    if (showPicker) {
//        ModalBottomSheet(
//            onDismissRequest = onDismiss,
//            modifier = modifier,
//            containerColor = Color.White,
//            dragHandle = null,
//            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
//        ) {
//            val months = listOf(
//                "January", "February", "March", "April", "May", "June",
//                "July", "August", "September", "October", "November", "December"
//            )
//
//            val listState = rememberLazyListState()
//            val coroutineScope = rememberCoroutineScope()
//            var selectedMonth by remember { mutableStateOf(months[0]) }
//
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .wrapContentHeight()
//            ) {
//                Column(
//                    modifier = Modifier.padding(16.dp)
//                ) {
//                    // Header
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(bottom = 24.dp),
//                        horizontalArrangement = Arrangement.SpaceBetween,
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Text(
//                            text = "Select",
//                            fontSize = 24.sp,
//                            fontWeight = FontWeight.Medium
//                        )
//                        IconButton(onClick = onDismiss) {
//                            Text(
//                                text = "✕",
//                                fontSize = 24.sp,
//                                fontWeight = FontWeight.Medium
//                            )
//                        }
//                    }
//
//                    // Month Picker
//                    Box(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(200.dp)
//                    ) {
//                        // Divider lines
//                        Column(
//                            modifier = Modifier
//                                .fillMaxSize()
//                                .padding(horizontal = 16.dp),
//                            verticalArrangement = Arrangement.Center
//                        ) {
//                            Divider(
//                                modifier = Modifier.fillMaxWidth(),
//                                color = Color(0xFF4CAF50),
//                                thickness = 1.dp
//                            )
//                            Spacer(modifier = Modifier.height(48.dp))
//                            Divider(
//                                modifier = Modifier.fillMaxWidth(),
//                                color = Color(0xFF4CAF50),
//                                thickness = 1.dp
//                            )
//                        }
//
//                        LazyColumn(
//                            state = listState,
//                            modifier = Modifier.fillMaxSize(),
//                            contentPadding = PaddingValues(vertical = 76.dp)
//                        ) {
//                            items(months.size) { index ->
//                                val month = months[index]
//                                val isSelected = selectedMonth == month
//
//                                Box(
//                                    modifier = Modifier
//                                        .fillMaxWidth()
//                                        .height(50.dp),
//                                    contentAlignment = Alignment.Center
//                                ) {
//                                    Text(
//                                        text = month,
//                                        fontSize = if (isSelected) 20.sp else 16.sp,
//                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
//                                        color = if (isSelected) Color.Black else Color.Gray,
//                                        modifier = Modifier.padding(vertical = 8.dp)
//                                    )
//                                }
//                            }
//                        }
//                    }
//
//                    // Select Button
//                    Button(
//                        onClick = {
//                            onPeriodSelected(selectedMonth)
//                            onDismiss()
//                        },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(56.dp)
//                            .padding(top = 16.dp),
//                        shape = RoundedCornerShape(8.dp),
//                        colors = ButtonDefaults.buttonColors(
//                            containerColor = Color(0xFF4CAF50)
//                        )
//                    ) {
//                        Text(
//                            text = "Select",
//                            fontSize = 18.sp,
//                            color = Color.White
//                        )
//                    }
//                }
//            }
//
//            // Handle scroll snapping and selection
//            LaunchedEffect(listState.firstVisibleItemScrollOffset) {
//                if (listState.firstVisibleItemScrollOffset in 0..50) {
//                    val centerIndex = listState.firstVisibleItemIndex
//                    if (centerIndex in months.indices) {
//                        selectedMonth = months[centerIndex]
//                    }
//                }
//            }
//
//            // Snap to the nearest month when scrolling stops
//            LaunchedEffect(listState.isScrollInProgress) {
//                if (!listState.isScrollInProgress) {
//                    val centerIndex = listState.firstVisibleItemIndex
//                    coroutineScope.launch {
//                        listState.animateScrollToItem(centerIndex)
//                    }
//                }
//            }
//        }
//    }
//}
enum class Month(val displayName: String) {
    JANUARY("January"),
    FEBRUARY("February"),
    MARCH("March"),
    APRIL("April"),
    MAY("May"),
    JUNE("June"),
    JULY("July"),
    AUGUST("August"),
    SEPTEMBER("September"),
    OCTOBER("October"),
    NOVEMBER("November"),
    DECEMBER("December");

    companion object {
        val allMonths = values()
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PeriodPicker(
    showPicker: Boolean,
    onDismiss: () -> Unit,
    onPeriodSelected: (Month) -> Unit,
    modifier: Modifier = Modifier
) {
    if (showPicker) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            modifier = modifier,
            containerColor = Color.White,
            dragHandle = null,
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
        ) {
            val months = Month.allMonths // Use enum for months
            val listState = rememberLazyListState()
            var selectedMonth by remember { mutableStateOf(months[0]) }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    // Header
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 24.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Select",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Medium
                        )
                        IconButton(onClick = onDismiss) {
                            Text(
                                text = "✕",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    // Month Picker
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    ) {
                        // Divider lines
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            HorizontalDivider(
                                modifier = Modifier.fillMaxWidth(),
                                thickness = 1.dp,
                                color = Color(0xFF4CAF50)
                            )
                            Spacer(modifier = Modifier.height(48.dp))
                            HorizontalDivider(
                                modifier = Modifier.fillMaxWidth(),
                                thickness = 1.dp,
                                color = Color(0xFF4CAF50)
                            )
                        }

                        LazyColumn(
                            state = listState,
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(vertical = 76.dp)
                        ) {
                            items(months.size) { index ->
                                val month = months[index]
                                val isSelected = selectedMonth == month
                                val textStyle = TextStyle(
                                    fontSize = if (isSelected) 20.sp else 16.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSelected) Color.Black else Color.Gray
                                )

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(50.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = month.displayName,
                                        style = textStyle,
                                        modifier = Modifier.padding(vertical = 8.dp)
                                    )
                                }
                            }
                        }
                    }

                    // Select Button
                    Button(
                        onClick = {
                            onPeriodSelected(selectedMonth) // Pass enum value instead of String
                            onDismiss()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .padding(top = 16.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4CAF50)
                        )
                    ) {
                        Text(
                            text = "Select",
                            fontSize = 18.sp,
                            color = Color.White
                        )
                    }
                }
            }

            // Handle scroll snapping and selection
            LaunchedEffect(listState.firstVisibleItemScrollOffset) {
                val centerIndex = listState.firstVisibleItemIndex
                if (centerIndex in months.indices) {
                    selectedMonth = months[centerIndex]
                }
            }

            // Snap to the nearest month when scrolling stops
            LaunchedEffect(listState.isScrollInProgress) {
                if (!listState.isScrollInProgress) {
                    val centerIndex = listState.firstVisibleItemIndex
                    listState.animateScrollToItem(centerIndex)
                }
            }
        }
    }
}



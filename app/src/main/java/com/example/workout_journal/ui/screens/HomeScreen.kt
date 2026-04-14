package com.example.workout_journal.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workout_journal.R
import com.example.workout_journal.ui.theme.Workout_journalTheme
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {

    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var currentMonth by remember { mutableStateOf(YearMonth.now()) }


    val highlightedDates = remember {
        setOf(
            LocalDate.now(),
            LocalDate.now().plusDays(2),
            LocalDate.now().minusDays(3)
        )
    }

    val tabs = listOf("Home", "Profile")
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.workout_journal)) },
                modifier = Modifier.fillMaxWidth(),
            )
        },

    ){ contentPadding ->
        Column(modifier = Modifier.padding(contentPadding)){}
    }
}


//@Composable
//fun CalendarView(
//    currentMonth: YearMonth,
////    selectedDate: LocalDate,
//    highlightedDates: Set<LocalDate>,
//    onDateSelected: (LocalDate) -> Unit,
//    onPreviousMonth: () -> Unit,
//    onNextMonth: () -> Unit
//) {
//    Column(modifier = Modifier.padding(16.dp)) {
//
//        // Month header
//        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
//            IconButton(onClick = onPreviousMonth) {
//                Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, "Previous", tint = Color(0xFF6C63FF))
//            }
//            Text(
//                text = "${currentMonth.month.getDisplayName(TextStyle.FULL, Locale.getDefault())} ${currentMonth.year}",
//                modifier = Modifier.weight(1f),
//                textAlign = TextAlign.Center,
//                fontWeight = FontWeight.Bold,
//                fontSize = 16.sp,
//                color = Color(0xFF222244)
//            )
//            IconButton(onClick = onNextMonth) {
//                Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, "Next", tint = Color(0xFF6C63FF))
//            }
//        }
//
//        // Day-of-week labels
//        Row(modifier = Modifier.fillMaxWidth()) {
//            listOf("Su", "Mo", "Tu", "We", "Th", "Fr", "Sa").forEach {
//                Text(
//                    text = it,
//                    modifier = Modifier.weight(1f),
//                    textAlign = TextAlign.Center,
//                    fontSize = 11.sp,
//                    fontWeight = FontWeight.SemiBold,
//                    color = Color(0xFFAAAAAA)
//                )
//            }
//        }
//
//        Spacer(Modifier.height(6.dp))
//
//        // Grid
//        val daysInMonth = currentMonth.lengthOfMonth()
//        val startOffset = currentMonth.atDay(1).dayOfWeek.value % 7
//        val rows = ((startOffset + daysInMonth) + 6) / 7
//
//        for (row in 0 until rows) {currentStreak
//            Row(modifier = Modifier.fillMaxWidth()) {
//                for (col in 0..6) {
//                    val day = row * 7 + col - startOffset + 1
//                    if (day in 1..daysInMonth) {
//                        val date = currentMonth.atDay(day)
////                        val isSelected = date == selectedDate
//                        val isToday = date == LocalDate.now()
//                        val isHighlighted = date in highlightedDates
//
//                        DayCell(
//                            day = day,
////                            isSelected = isSelected,
//                            isToday = isToday,
//                            isHighlighted = isHighlighted,
//                            onClick = { onDateSelected(date) },
//                            modifier = Modifier.weight(1f)
//                        )
//                    } else {
//                        Spacer(Modifier.weight(1f))
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun DayCell(
//    day: Int,
////    isSelected: Boolean,
//    isToday: Boolean,
//    isHighlighted: Boolean,
//    onClick: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    Box(
//        modifier = modifier
//            .aspectRatio(1f)
//            .padding(2.dp)
//            .clip(CircleShape)
//            .background(
//                when {
////                    isSelected -> Color(0xFF6C63FF)
//                    isHighlighted -> Color(0xFFE8E6FF)
//                    else -> Color.Transparent
//                }
//            )
//            .clickable { onClick() },
//        contentAlignment = Alignment.Center
//    ) {
//        Text(
//            text = day.toString(),
//            fontSize = 13.sp,
//            fontWeight = if (isHighlighted) FontWeight.Bold else FontWeight.Normal,
//            color = when {
////                isSelected -> Color.White
//                isHighlighted || isToday -> Color(0xFF6C63FF)
//                else -> Color(0xFFAAAAAA)
//            }
//        )
//    }
//}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Workout_journalTheme {
        HomeScreen()
    }
}
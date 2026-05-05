package com.example.workout_journal.ui.content

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workout_journal.ui.cards.RunStatCard
import com.example.workout_journal.ui.cards.WeightStatCard
import com.example.workout_journal.ui.viewmodel.StatsUiState

@Composable
fun StatsScreenContent(stats: StatsUiState) {

    var weightsExpanded by remember { mutableStateOf(true) }
    var runsExpanded by remember { mutableStateOf(true) }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { weightsExpanded = !weightsExpanded }
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Weights",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.Black)

                Icon(
                    imageVector = if (weightsExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null
                )
            }
            HorizontalDivider(
                color = Color.Black, thickness = 1.dp, modifier = Modifier.padding(vertical = 1.dp)
            )
        }

        if (!stats.isLoading) {
            val chunked = stats.weightPBs.chunked(2)
            items(chunked) { rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    rowItems.forEach { weightPB ->
                        WeightStatCard(
                            weightPB = weightPB, modifier = Modifier.weight(1f)
                        )
                    }
                }
                if (rowItems.size == 1) {
                    Spacer(modifier = Modifier.width(3.dp))
                }
            }
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { runsExpanded = !runsExpanded }
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Runs",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.Black)
                Icon(
                    imageVector = if (runsExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null
                )

            }
            HorizontalDivider(
                color = Color.Black, thickness = 1.dp, modifier = Modifier.padding(vertical = 1.dp)
            )
        }

        if (!stats.isLoading) {
            val chunked = stats.runPBs.chunked(2)
            items(chunked) { rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    rowItems.forEach { runPB ->
                        RunStatCard(
                            runPB = runPB, modifier = Modifier.weight(1f)
                        )
                    }  // single item composable
                }
                if (rowItems.size == 1) {
                    Spacer(modifier = Modifier.width(3.dp))
                }
            }
        }
    }
}





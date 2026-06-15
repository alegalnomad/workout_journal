package com.example.workout_journal.ui.content

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.unit.dp
import com.example.workout_journal.data.entity.MeasureUnit
import com.example.workout_journal.data.entity.WeightExerciseName
import com.example.workout_journal.ui.viewmodel.SettingsUiState
import com.example.workout_journal.utils.toTitleCase

@Composable
fun SettingsScreenContent(
    uiState: SettingsUiState,
    allExercises: List<WeightExerciseName>,
    selectedExercises: Set<Int>,
    onMeasureUnitChange: (MeasureUnit) -> Unit,
    onUserNameChange: (String) -> Unit,
) {
    val currentUnit = uiState.measureUnit


    LazyColumn(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.DarkGray)
            ) {
                Column(
                    modifier = Modifier.padding(8.dp),

                    ) {
                    Box(
                        modifier = Modifier.padding(5.dp)
                    ) {
                        Text(text = "Measurement Unit")
                    }

                    Row(modifier = Modifier.padding(8.dp)) {
                        Button(
                            onClick = {onMeasureUnitChange(MeasureUnit.METRIC)},
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (currentUnit == MeasureUnit.METRIC) Color.Green else Gray,
                                contentColor = if (currentUnit == MeasureUnit.METRIC) Color.Black else Color.Black
                            )
                            ) {
                            Text(text = "Metric (Kg / Km)")
                        }

                        Spacer(modifier = Modifier.padding(horizontal = 10.dp))

                        Button(
                            onClick = {onMeasureUnitChange(MeasureUnit.IMPERIAL)},
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (currentUnit == MeasureUnit.IMPERIAL) Color.Green else Gray,
                                contentColor = if (currentUnit == MeasureUnit.IMPERIAL) Color.Black else Color.Black
                            )
                        ) {
                            Text(text = "Imperial (Lbs / Mi)")
                        }
                    }
                }
            }
        }

        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.DarkGray)
            ) { }
        }
    }
}



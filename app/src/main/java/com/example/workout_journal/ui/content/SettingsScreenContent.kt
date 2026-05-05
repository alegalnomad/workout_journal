package com.example.workout_journal.ui.content

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.workout_journal.data.entity.MeasureUnit
import com.example.workout_journal.data.entity.WeightExerciseName
import com.example.workout_journal.ui.viewmodel.SettingsUiState
import com.example.workout_journal.utils.toTitleCase

@Composable
fun SettingsScreenContent(
    uiState: SettingsUiState,
    exercises: List<WeightExerciseName>,
    onMeasureUnitChange: (MeasureUnit) -> Unit,
    onUserNameChange: (String) -> Unit,
) {
    var showDialog by remember { mutableStateOf(false) }
    var textInput by remember { mutableStateOf(" ") }
    LazyColumn(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Username", color = Color.Black)
                Text(text = uiState.userName,
                    color = Color.Black,
                    modifier = Modifier.clickable{showDialog = !showDialog})
                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = { showDialog = false },
                        title = { Text("Enter New Username") },
                        text = {
                            OutlinedTextField(
                                value = textInput,
                                onValueChange = { textInput = it },
                                placeholder = {Text(text = "Username")})
                        },
                        confirmButton = {
                            TextButton(onClick = {
                                onUserNameChange(textInput)
                                showDialog = false
                            }) { Text("OK") }
                        }
                    )
                }
            }
        }
        item {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Measuring System", color = Color.Black)
                Text(text = toTitleCase(uiState.measureUnit.name), color = Color.Black)
            }
        }
    }
}
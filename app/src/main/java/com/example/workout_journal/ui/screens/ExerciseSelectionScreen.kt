package com.example.workout_journal.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.workout_journal.ui.viewmodel.SettingsViewModel


@Composable
fun ExerciseSelectionScreen(
    viewModel: SettingsViewModel,
    onSave:(Set<Int>) -> Unit
){
    Box(){
        Text(text = "Selection Screen coming soon!")
    }
}
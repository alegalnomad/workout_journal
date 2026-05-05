package com.example.workout_journal.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.workout_journal.ui.viewmodel.RunViewModel

@Composable
fun RunSummaryScreen(
    viewModel: RunViewModel,
    onSave: (String?, String?) -> Unit
){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Run Summary Screen - Coming Soon",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}
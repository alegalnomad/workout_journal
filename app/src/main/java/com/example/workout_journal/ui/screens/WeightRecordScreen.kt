package com.example.workout_journal.ui.screens

import android.health.connect.datatypes.WeightRecord
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.workout_journal.ui.viewmodel.HomeViewModel
import com.example.workout_journal.ui.viewmodel.WeightsViewModel

@Composable
fun WeightRecordScreen(
    viewModel: WeightsViewModel,
    onFinish: () -> Unit
)
{
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Weight Record Screen - Coming Soon",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}
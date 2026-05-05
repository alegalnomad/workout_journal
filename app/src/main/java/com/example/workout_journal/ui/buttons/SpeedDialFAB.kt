package com.example.workout_journal.ui.buttons

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AvTimer
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
@Composable
fun SpeedDialFAB(
    onOptionSelected:(String) -> Unit
) {
    var isMenuOpen by remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(
        targetValue = if (isMenuOpen) 45f else 0f,
        label = "fab_rotation"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // 1. SCRIM (Click outside to close)
        if (isMenuOpen) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { isMenuOpen = false }
            )
        }

        // 2. FAB COLUMN
        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // OPTIONS MENU
            AnimatedVisibility(
                visible = isMenuOpen,
                enter = fadeIn() + slideInVertically(initialOffsetY = { it / 2 }),
                exit = fadeOut() + slideOutVertically(targetOffsetY = { it / 2 })
            ) {
                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Option 1
                    SpeedDialOption(icon = Icons.Filled.FitnessCenter, label = "Weights") {
                        isMenuOpen = false
                        onOptionSelected("weight")
                    }
                    // Option 2
                    SpeedDialOption(icon = Icons.AutoMirrored.Filled.DirectionsRun, label = "Run") {
                        isMenuOpen = false
                        onOptionSelected("run")
                    }
                    // Option 3
                    SpeedDialOption(icon = Icons.Filled.AvTimer, label = "HIIT") {
                        isMenuOpen = false
                        onOptionSelected("hiit")
                    }
                }
            }

            // MAIN BUTTON (Always shows the Add icon)
            FloatingActionButton(
                onClick = { isMenuOpen = !isMenuOpen },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = "Open Menu",
                    modifier = Modifier.rotate(rotation)
                )
            }
        }
    }
}

@Composable
fun SpeedDialOption(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        // THE LABEL
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 4.dp,
        ) {
            Text(
                text = label,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                style = MaterialTheme.typography.labelLarge
            )
        }

        // THE MINI FAB
        SmallFloatingActionButton(
            onClick = onClick,
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        ) {
            Icon(icon, contentDescription = label)
        }
    }
}

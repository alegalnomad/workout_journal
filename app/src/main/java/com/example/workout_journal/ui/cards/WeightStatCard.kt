package com.example.workout_journal.ui.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workout_journal.data.relations.WeightPbWithNames

@Composable
fun WeightStatCard(weightPB: WeightPbWithNames, modifier: Modifier) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .width(150.dp)
            .height((80.dp))
            .padding(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center

        ) {
            Text(
                text = weightPB.exerciseName.name, fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))


            Text(
                text = "${weightPB.pb.weightKg} Kg",
                fontSize = 20.sp
            )

        }
    }
}
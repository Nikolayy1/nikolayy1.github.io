package com.example.to_docompose.ui.screens.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.to_docompose.data.models.Stats

@Composable
fun Stats(
    stats: Stats,
    onStatUpgrade: (String) -> Unit
) {
    android.util.Log.d("Stats", "Stats passed: $stats")

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        StatRow(
            statName = "Discipline",
            statValue = stats.discipline,
            statPoints = stats.statPoints,
            onUpgrade = { onStatUpgrade("discipline") }
        )
        StatRow(
            statName = "Productivity",
            statValue = stats.productivity,
            statPoints = stats.statPoints,
            onUpgrade = { onStatUpgrade("productivity") }
        )
        StatRow(
            statName = "Energy",
            statValue = stats.energy,
            statPoints = stats.statPoints,
            onUpgrade = { onStatUpgrade("energy") }
        )
    }
}

@Composable
fun StatRow(
    statName: String,
    statValue: Int,
    statPoints: Int,
    onUpgrade: () -> Unit
) {
    // Log the current state of statPoints for each stat
    android.util.Log.d("StatRow", "Stat: $statName, StatPoints: $statPoints")

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 4.dp)
        ) {
            Text(
                text = statName,
                fontSize = 18.sp,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(end = 8.dp),
                color = MaterialTheme.colorScheme.primary
            )
            //Check if the `+` icon should be displayed
            if (statPoints > 0) {
                android.util.Log.d("StatRow", "Displaying + icon for $statName")
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Upgrade $statName",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable { onUpgrade() }
                )
            } else {
                android.util.Log.d("StatRow", "Hiding + icon for $statName")
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            repeat(6) { index ->
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "Stat Level",
                    tint = if (index < statValue) Color.Black else Color.LightGray,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}


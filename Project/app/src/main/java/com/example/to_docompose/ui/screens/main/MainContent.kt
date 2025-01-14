package com.example.to_docompose.ui.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.to_docompose.R
import com.example.to_docompose.data.models.ToDoTask

@Composable
fun MainContent(
    navigateToListScreen: () -> Unit,
    avatarLevel: Int,
    currentXP: Int,
    xpForNextLevel: Int,
    xpProgress: Float,
    quickBoardTasks: List<ToDoTask>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Level and Avatar Display
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Level $avatarLevel",
                fontSize = 24.sp,
                style = MaterialTheme.typography.titleMedium
            )
            Image(
                painter = painterResource(id = R.drawable.avatar),
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(50))
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // XP Progress Bar and XP Status
        Text(
            text = "XP Progress",
            fontSize = 18.sp,
            style = MaterialTheme.typography.bodyMedium
        )
        LinearProgressIndicator(
            progress = xpProgress,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp)),
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "$currentXP / $xpForNextLevel EXP",
            fontSize = 14.sp,
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Quick Board Section
        Text(text = "Quick Board", fontSize = 18.sp)
        if (quickBoardTasks.isEmpty()) {
            Text(text = "No tasks marked for Quick Board.")
        } else {
            QuickBoard(tasks = quickBoardTasks)
        }

        Spacer(modifier = Modifier.weight(1f))

        // Quest List Button
        Button(onClick = navigateToListScreen) {
            Text(text = "Quest List")
        }
    }
}

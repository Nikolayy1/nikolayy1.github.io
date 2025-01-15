package com.example.to_docompose.ui.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.to_docompose.R
import com.example.to_docompose.data.models.ToDoTask
import com.example.to_docompose.ui.theme.Custom_beige
import com.example.to_docompose.ui.theme.Custom_dark_blue
import com.example.to_docompose.ui.theme.Custom_orange

@Composable
fun MainContent(
    navigateToListScreen: () -> Unit,
    avatarLevel: Int,
    currentXP: Int,
    xpForNextLevel: Int,
    xpProgress: Float,
    quickBoardTasks: List<ToDoTask>,
    navigateToTaskScreen: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Custom_beige) // Set background color
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header Section
        Text(
            text = "Daily Quest",
            fontSize = 28.sp,
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Avatar and Level Display
        Image(
            painter = painterResource(id = R.drawable.avatar),
            contentDescription = "Avatar",
            modifier = Modifier
                .size(120.dp)
                .clipToBounds()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Level $avatarLevel",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        // XP Progress Section
        Text(
            text = "XP Progress",
            fontSize = 16.sp,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        LinearProgressIndicator(
            progress = xpProgress,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "$currentXP / $xpForNextLevel EXP",
            fontSize = 16.sp,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Quick Board Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // Allow Quick Board to take up most available space
                .padding(bottom = 16.dp) // Space above the button
                .background(Custom_dark_blue) // Dark blue background for Quick Board
                .padding(8.dp) // Inner padding
        ) {
            // Quick Board Title
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray)
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center // Center title and icon
            ) {
                Text(
                    text = "Quick Board",
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black // Set title color to black
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Filled.Bookmark,
                    contentDescription = "Quick Board Bookmark",
                    tint = Color.Black // Keep icon black
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (quickBoardTasks.isEmpty()) {
                Text(
                    text = "No quests marked for Quick Board",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = Color.White
                )
            } else {
                QuickBoard(
                    tasks = quickBoardTasks,
                    onTaskClick = navigateToTaskScreen
                )
            }
        }

        // Footer Button
        Button(
            onClick = navigateToListScreen,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(0.dp),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = Custom_orange
            )
        ) {
            Text(
                text = "QUEST LIST",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

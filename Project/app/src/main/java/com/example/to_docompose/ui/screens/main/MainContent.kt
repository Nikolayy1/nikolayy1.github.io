package com.example.to_docompose.ui.screens.main

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.to_docompose.data.models.Stats
import com.example.to_docompose.data.models.ToDoTask
import com.example.to_docompose.ui.theme.Custom_white_background
import com.example.to_docompose.ui.theme.Custom_dark_blue
import com.example.to_docompose.ui.theme.Custom_orange

/**
 * Main content view (Title, stats, avatar, Quick Board, List button)
 */

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainContent(
    navigateToListScreen: () -> Unit,
    avatarLevel: Int,
    currentXP: Int,
    xpForNextLevel: Int,
    xpProgress: Float,
    quickBoardTasks: List<ToDoTask>,
    navigateToTaskScreen: (Int) -> Unit,
    stats: Stats,
    onStatUpgrade: (String) -> Unit,
    selectedAvatar: Int,
    onAvatarChange: (Int) -> Unit
) {
    // State to show/hide avatar selection dialog
    var showAvatarSelection by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Custom_white_background)
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

        // Split Layout for Avatar, Level, and Stats
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left Section: Avatar and Level
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                // Avatar with Long Press
                Image(
                    painter = painterResource(id = selectedAvatar),
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(150.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .combinedClickable(
                            onClick = { /* Handle regular click if needed */ },
                            onLongClick = { showAvatarSelection = true } // Trigger dialog on long press
                        )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Level $avatarLevel",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            // Right Section: Stats
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                Stats(
                    stats = stats,
                    onStatUpgrade = onStatUpgrade
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // XP Progress Section
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Title
            Text(
                text = "EXP Progress",
                fontSize = 20.sp,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Progress Bar Container
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                // Progress Indicator
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(fraction = xpProgress) // Progress fraction
                        .clip(MaterialTheme.shapes.medium)
                        .background(MaterialTheme.colorScheme.primary)
                        .animateContentSize() // Smooth animation when progress changes
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // XP Text
            Text(
                text = "$currentXP / $xpForNextLevel EXP",
                fontSize = 16.sp,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Quick Board Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.5f)
                .padding(bottom = 8.dp)
                .padding(8.dp)
                .clip(MaterialTheme.shapes.medium)
                .background(Custom_dark_blue)
        ) {
            // Quick Board Title
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                    .background(Custom_white_background)
                    .border(8.dp, Custom_dark_blue, MaterialTheme.shapes.medium)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Quick Board",
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.titleMedium,
                    color = Custom_dark_blue
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Filled.Bookmark,
                    contentDescription = "Quick Board Bookmark",
                    tint = Custom_dark_blue
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (quickBoardTasks.isEmpty()) {
                Text(
                    text = "No quests marked for Quick Board.",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp),
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
                .height(50.dp)
                .padding(top = 8.dp),
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

    // Avatar Selection Dialog
    if (showAvatarSelection) {
        AvatarSelectionDialog(
            selectedAvatar = selectedAvatar,
            onAvatarSelected = { avatar ->
                onAvatarChange(avatar)
                showAvatarSelection = false
            },
            onDismiss = { showAvatarSelection = false }
        )
    }
}

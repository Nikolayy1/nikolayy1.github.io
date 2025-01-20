package com.example.to_docompose.ui.screens.task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.to_docompose.R
import com.example.to_docompose.components.PriorityDropDown
import com.example.to_docompose.data.models.Priority
import com.example.to_docompose.ui.theme.Custom_beige
import com.example.to_docompose.ui.theme.Custom_dark_blue
import com.example.to_docompose.ui.theme.Custom_light_blue
import com.example.to_docompose.ui.theme.Custom_orange
import com.example.to_docompose.ui.theme.Custom_white
import com.example.to_docompose.ui.theme.LARGE_PADDING
import com.example.to_docompose.ui.theme.MEDIUM_PADDING

@Composable
fun TaskContent(
    modifier: Modifier = Modifier,
    title: String,
    onTitleChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    priority: Priority,
    onPrioritySelected: (Priority) -> Unit,
    showCompleteButton: Boolean,
    onCompleteTask: () -> Unit
) {
    var showCompleteDialog by remember { mutableStateOf(false) }

    // Randomly select a motivational message
    val randomMessage by remember {
        mutableStateOf(motivationalMessages.random())
    }

    val xpReward = when (priority) {
        Priority.LOW -> 1
        Priority.MEDIUM -> 5
        Priority.HIGH -> 10
        else -> 0
    }

    val motivationalMessage = "$randomMessage Completing this quest will reward you +$xpReward EXP."

    if (showCompleteDialog) {
        FancyAlertDialog(
            title = "Quest Complete!",
            message = motivationalMessage,
            openDialog = showCompleteDialog,
            onDismissRequest = { showCompleteDialog = false },
            onConfirm = {
                onCompleteTask()
                showCompleteDialog = false
            }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Custom_beige)
            .padding(all = LARGE_PADDING)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = title,
            onValueChange = { onTitleChange(it) },
            label = { Text(text = stringResource(id = R.string.title)) },
            textStyle = MaterialTheme.typography.bodyMedium,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Custom_dark_blue,
                unfocusedBorderColor = Custom_light_blue,
                focusedLabelColor = Custom_dark_blue,
                unfocusedLabelColor = Custom_dark_blue,
            )
        )
        HorizontalDivider(
            modifier = Modifier.height(MEDIUM_PADDING),
            color = Custom_beige
        )
        PriorityDropDown(
            priority = priority,
            onPrioritySelected = onPrioritySelected
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            value = description,
            onValueChange = { onDescriptionChange(it) },
            label = { Text(text = stringResource(id = R.string.description)) },
            textStyle = MaterialTheme.typography.bodyMedium,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Custom_dark_blue,
                unfocusedBorderColor = Custom_light_blue,
                focusedLabelColor = Custom_dark_blue,
                unfocusedLabelColor = Custom_dark_blue,
            )
        )
        if (showCompleteButton) {
            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = { showCompleteDialog = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Custom_orange,
                    contentColor = Custom_white,
                    disabledContentColor = Custom_white.copy(alpha = 0.4f),
                    disabledContainerColor = Custom_orange.copy(alpha = 0.4f)
                )
            ) {
                Text(text = "Complete Quest")
            }
        }
    }
}


@Composable
@Preview
private fun TaskContentPreview() {
    TaskContent(
        title = "Sample Task",
        onTitleChange = {},
        description = "Sample Description",
        onDescriptionChange = {},
        priority = Priority.MEDIUM,
        onPrioritySelected = {},
        showCompleteButton = true,
        onCompleteTask = {}
    )
}

@Composable
fun FancyAlertDialog(
    title: String,
    message: String,
    openDialog: Boolean,
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit
) {
    if (openDialog) {
        Dialog(onDismissRequest = onDismissRequest) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .background(MaterialTheme.colorScheme.background)
                        .clip(MaterialTheme.shapes.medium),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Title
                    Text(
                        text = title,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    // Message
                    Text(
                        text = message,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // "Confirm Completion" Text
                    Text(
                        text = "Confirm Completion",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.secondary,
                        textAlign = TextAlign.Left,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // Buttons
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // "No" Button
                        Button(
                            onClick = onDismissRequest,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.error,
                                contentColor = Color.White
                            ),
                            modifier = Modifier.padding(end = 8.dp)
                        ) {
                            Text(text = "No")
                        }

                        // "Yes" Button
                        Button(
                            onClick = onConfirm,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = Color.White
                            )
                        ) {
                            Text(text = "Yes")
                        }
                    }
                }
            }
        }
    }
}

val motivationalMessages = listOf(
    "Effort brings growth.",
    "Progress is forged through action.",
    "Your diligence has been rewarded.",
    "Each completed quest strengthens your resolve.",
    "Every step forward brings mastery.",
    "Victory builds confidence.",
    "With effort comes great rewards.",
    "Your journey advances with each step.",
    "Another quest completed, another milestone achieved.",
    "No challenge can stand in your way.",
    "That quest didn’t stand a chance!",
    "Boom! Another one bites the dust.",
    "Somewhere, the procrastination gods are weeping.",
    "Hasta la vista, quest.",
    "Do or do not; there is no try.",
    "Quest complete. Victory achieved!",
    "The cake was a lie, but the quest wasn’t.",
    "You’ve entered the God Mode of productivity.",
    "From zero to hero. Quest completed!",
    "Another quest bites the dust.",
    "You’ve claimed victory.",
    "The harder the quest, the sweeter the reward.",
    "Carpe diem!",
    "Courage conquers all.",
    "Strength comes from persistence.",
    "Action is the foundation of success.",
    "Turn obstacles into stepping stones.",
    "Every mountain is climbed one step at a time.",
    "Flawless execution.",
    "Your to-do list is begging for mercy.",
    "Step by step, you carve your own legacy.",
    "Quest neutralized.",
    "System update: Efficiency increased.",
    "Hard work paves the way to greatness.",
    "Virtue is forged through effort.",
    "Through perseverance, you rise.",
    "The reward lies in the journey.",
    "Look at you, all productive and stuff.",
    "Mastery is born of discipline.",
    "A hero’s path is paved with completed quests.",
    "Glory is earned, not given.",
    "Discipline breeds greatness.",
    "Through struggle comes strength.",
    "Completion brings wisdom.",
    "Even small efforts lead to great gains.",
    "Every quest completed is a step toward mastery.",
    "The greatest treasures lie at the end of the hardest quests.",
    "A calm mind conquers the fiercest quests.",
    "Through the ashes, you rise triumphant.",
    "The quest is complete. Well done!",
    "Impressive. Most impressive.",
    "The force is strong with you.",
    "Progress: Made. Motivation: Boosted.",
    "Quest completed. Onto the next victory.",
    "Every quest completed weakens the enemy: procrastination.",
    "To subdue your quest list without effort is the highest form of victory.",
    "Opportunities multiply as you strike quests off the list.",
    "Procrastination is the enemy. Crush it before it grows.",
    "Move swiftly to complete; hesitate, and the list will grow.",
    "The greatest victories come from quests no one thought you’d finish.",
    "Productivity is the path to mastery. Walk it wisely.",
    "Strike quickly. Strike efficiently. Let no quest survive.",
    "Master the quest in front of you, and you master yourself.",
    "Control your quest list, or your quest list will control you.",
    "A focused mind is sharper than any blade.",
    "One does not complete the quest list in haste but with clarity and resolve.",
    "If you know your quests and know yourself, you need not fear the to-do list.",
    "The best way to predict the future is to finish today’s to-do list.",
    "The early bird gets the worm, but the second mouse gets the cheese.",
    "In every quest lies a lesson."
)

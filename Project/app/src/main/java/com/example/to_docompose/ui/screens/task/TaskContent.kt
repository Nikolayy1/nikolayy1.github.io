package com.example.to_docompose.ui.screens.task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
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
    showCompleteButton: Boolean, // New parameter to control button visibility
    onCompleteTask: () -> Unit
) {
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
            modifier = Modifier.fillMaxSize().weight(1f),
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
        if (showCompleteButton) { // Conditionally show the button
            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = onCompleteTask
            ) {
                Text(text = "Complete Task")
            }
        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = onCompleteTask,
            colors = ButtonColors(
                containerColor = Custom_orange,
                contentColor = Custom_white,
                disabledContentColor = Custom_white.copy(alpha = 0.4f),
                disabledContainerColor = Custom_orange.copy(alpha = 0.4f)
            )
        ) {
            Text(text = "Complete Task")
        }
    }
}

}@Composable
@Preview
private fun TaskContentPreview() {
    TaskContent(
        title = "Sample Task",
        onTitleChange = {},
        description = "Sample Description",
        onDescriptionChange = {},
        priority = Priority.MEDIUM,
        onPrioritySelected = {},
        showCompleteButton = true, // Set to true for preview
        onCompleteTask = {}
    )
}

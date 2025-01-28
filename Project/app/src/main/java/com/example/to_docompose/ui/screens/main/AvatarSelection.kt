package com.example.to_docompose.ui.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.to_docompose.R

/**
 * Composable for Avatar Selection
 */

@Composable
fun AvatarSelectionDialog(
    selectedAvatar: Int,
    onAvatarSelected: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    val avatars = listOf(
        R.drawable.geekygirl,
        R.drawable.hatguy,
        R.drawable.pirate,
        R.drawable.prof,
        R.drawable.redhairgirl,
        R.drawable.blackhairgirl,
        R.drawable.confused,
        R.drawable.hijab,
        R.drawable.turban
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select Your Avatar") },
        text = {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(8.dp).height(200.dp)
            ) {
                items(avatars) { avatar ->
                    Image(
                        painter = painterResource(id = avatar),
                        contentDescription = "Avatar Option",
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(40.dp))
                            .clickable { onAvatarSelected(avatar) } // Select avatar
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}



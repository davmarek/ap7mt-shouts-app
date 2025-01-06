package cz.davmarek.shouts.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun NavigationBackButton(
    onClick: () -> Unit
) {
    var enabled by remember { mutableStateOf(true) }

    IconButton(
        onClick = {
            enabled = false
            onClick()
        },
        enabled = enabled
    ) {
        Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Back")
    }
}
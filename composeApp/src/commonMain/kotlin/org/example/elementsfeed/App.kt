package org.example.elementsfeed

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import org.example.elementsfeed.presentation.ui.MainScreen
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App() {
    MaterialTheme {
        MainScreen()
    }
}
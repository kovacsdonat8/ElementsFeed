package org.example.elementsfeed.presentation.ui

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.example.elementsfeed.presentation.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val viewModel: MainViewModel = remember { MainViewModel() }
    val feedItems by viewModel.feedItems.collectAsState()
    val commandText by viewModel.commandText.collectAsState()
    val shouldScrollToTop by viewModel.shouldScrollToTop.collectAsState()

    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    LaunchedEffect(shouldScrollToTop) {
        if (shouldScrollToTop && feedItems.isNotEmpty()) {
            coroutineScope.launch {
                listState.animateScrollToItem(0)
                viewModel.onScrollCompleted()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Title
        Text(
            text = "Elements Feed",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        // Feed list
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            reverseLayout = true
        ) {
            items(feedItems, key = { it.id }) { item ->
                FeedItemCard(item)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Command input
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = commandText,
                onValueChange = { viewModel.updateCommandText(it) },
                label = {
                    Text("Enter command (Start, Stop, Pause, Resume): ",
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                modifier = Modifier.weight(1f),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        if (commandText.trim().isNotEmpty()) {
                            viewModel.executeCommand()
                        }
                    }
                )
            )
        }
    }
}
package org.example.elementsfeed.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.example.elementsfeed.data.model.FeedItem
import org.example.elementsfeed.data.repository.FeedRepository
import org.example.elementsfeed.domain.usecase.FeedUseCase

class MainViewModel : ViewModel() {
    private val repository = FeedRepository()
    private val feedUseCase = FeedUseCase(repository)

    private val _commandText = MutableStateFlow("")
    val commandText: StateFlow<String> = _commandText.asStateFlow()

    private val _shouldScrollToTop = MutableStateFlow(false)
    val shouldScrollToTop: StateFlow<Boolean> = _shouldScrollToTop.asStateFlow()

    val feedItems: StateFlow<List<FeedItem>> = feedUseCase.feedItems
    val isStarted: StateFlow<Boolean> = feedUseCase.isStarted
    val isPaused: StateFlow<Boolean> = feedUseCase.isPaused

    init {
        viewModelScope.launch {
            feedItems.collect { items ->
                if (items.isNotEmpty()) {
                    _shouldScrollToTop.value = true
                }
            }
        }
    }

    fun updateCommandText(text: String) {
        _commandText.value = text
    }

    fun executeCommand() {
        val command = _commandText.value.trim()
        if (command.isNotEmpty() && feedUseCase.isValidCommand(command)) {
            feedUseCase.executeCommand(command)
            _commandText.value = ""
        }
    }

    fun onScrollCompleted() {
        _shouldScrollToTop.value = false
    }

    override fun onCleared() {
        super.onCleared()
        repository.stopFetching()
    }
}
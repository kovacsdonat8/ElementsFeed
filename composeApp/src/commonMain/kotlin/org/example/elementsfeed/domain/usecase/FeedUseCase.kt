package org.example.elementsfeed.domain.usecase

import kotlinx.coroutines.flow.StateFlow
import org.example.elementsfeed.data.model.FeedItem
import org.example.elementsfeed.data.repository.FeedRepository

class FeedUseCase(private val repository: FeedRepository) {
    val feedItems: StateFlow<List<FeedItem>> = repository.feedItems
    val isStarted: StateFlow<Boolean> = repository.isStarted
    val isPaused: StateFlow<Boolean> = repository.isPaused

    fun executeCommand(command: String): Boolean {
        return when (command.lowercase().trim()) {
            "start" -> {
                if (!repository.isStarted.value) {
                    repository.addCommand("Start")
                    repository.startFetching()
                    true
                }
                else {
                    false
                }
            }
            "stop" -> {
                repository.addCommand("Stop")
                repository.stopFetching()
                true
            }
            "pause" -> {
                if (repository.isStarted.value && !repository.isPaused.value) {
                    repository.addCommand("Pause")
                    repository.pauseDisplay()
                    true
                }
                else {
                    false
                }
            }
            "resume" -> {
                if (repository.isStarted.value && repository.isPaused.value) {
                    repository.addCommand("Resume")
                    repository.resumeDisplay()
                    true
                }
                else {
                    false
                }
            }
            else -> false
        }
    }

    fun isValidCommand(command: String): Boolean {
        return command.lowercase().trim() in listOf("start", "stop", "pause", "resume")
    }
}
package org.example.elementsfeed.data.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.example.elementsfeed.data.model.FeedItem
import org.example.elementsfeed.data.network.ApiClient

class FeedRepository {
    private val apiClient = ApiClient()
    private var currentSkip = 0
    private var fetchJob: Job? = null

    private val _feedItems = MutableStateFlow<List<FeedItem>>( emptyList())
    val feedItems: StateFlow<List<FeedItem>> = _feedItems.asStateFlow()

    private val _isStarted = MutableStateFlow(false)
    val isStarted: StateFlow<Boolean> = _isStarted.asStateFlow()

    private val _isPaused = MutableStateFlow(false)
    val isPaused: StateFlow<Boolean> = _isPaused.asStateFlow()

    private val pausedFeeds = mutableListOf<FeedItem.Feed>()

    fun startFetching() {
        if (_isStarted.value) {
            return
        }

        _isStarted.value = true
        _isPaused.value = false

        fetchJob = CoroutineScope(Dispatchers.Default).launch {
            while (isActive && _isStarted.value) {
                fetchNextFeed()
                delay(5000)
            }
        }
    }

    fun stopFetching() {
        _isStarted.value = false
        _isPaused.value = false
        fetchJob?.cancel()
        pausedFeeds.clear()
        apiClient.close()
    }

    fun pauseDisplay() {
        if (!_isStarted.value) {
            return
        }
        _isPaused.value = true
    }

    fun resumeDisplay() {
        if (!_isStarted.value || !_isPaused.value) {
            return
        }
        _isPaused.value = false

        if (pausedFeeds.isNotEmpty()) {
            val currentItems = _feedItems.value.toMutableList()
            currentItems.addAll(0, pausedFeeds)
            _feedItems.value = currentItems
            pausedFeeds.clear()
        }
    }

    private suspend fun fetchNextFeed() {
        apiClient.getProduct(currentSkip).fold(
            onSuccess = { response ->
                if (response.products.isNotEmpty()) {
                    val product = response.products.first()
                    val feedItem = FeedItem.Feed(
                        id = "feed_${product.id}_${currentSkip}",
                        content = product.description,
                        timestamp = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                    )

                    if (_isPaused.value) {
                        pausedFeeds.add(0, feedItem)
                    } else {
                        addFeedItem(feedItem)
                    }
                    currentSkip++
                }
            },
            onFailure = {
                // handle error
            }
        )
    }

    private fun addFeedItem(item: FeedItem) {
        val currentItems = _feedItems.value.toMutableList()
        currentItems.add(0, item)
        _feedItems.value = currentItems
    }
}
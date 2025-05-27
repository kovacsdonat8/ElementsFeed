package org.example.elementsfeed.data.repository

import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.elementsfeed.data.model.FeedItem
import org.example.elementsfeed.data.network.ApiClient

class FeedRepository {
    private val apiClient = ApiClient()
    private var currentSkip = 0
    private var fetchJob: Job? = null

    private val _feedItems = MutableStateFlow<List<FeedItem>>( emptyList())
    val feedItems: StateFlow<List<FeedItem>> = _feedItems.asStateFlow()
}
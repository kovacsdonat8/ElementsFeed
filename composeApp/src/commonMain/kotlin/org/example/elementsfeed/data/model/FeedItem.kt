package org.example.elementsfeed.data.model

import kotlinx.datetime.LocalDateTime

sealed class FeedItem {
    abstract val id: String
    abstract val timestamp: LocalDateTime

    data class Feed(
        override val id: String,
        val content: String,
        val imageUrl: String,
        override val timestamp: LocalDateTime
    ) : FeedItem()

    data class Command(
        override val id: String,
        val command: String,
        override val timestamp: LocalDateTime
    ) : FeedItem()
}
package org.example.elementsfeed.presentation.ui.util

import kotlinx.datetime.LocalDateTime

fun formatTimestamp(timestamp: LocalDateTime): String {
    return "${timestamp.hour.toString().padStart(2, '0')}:" +
              "${timestamp.minute.toString().padStart(2, '0')}: " +
              "${timestamp.second.toString().padStart(2, '0')}"
}
package org.example.elementsfeed

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
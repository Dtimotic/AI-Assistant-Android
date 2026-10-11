package com.aiassistant.app.ui.assistant

data class AssistantUiState(
    val transcript: String = "",
    val response: String = "Ready to help.",
    val isListening: Boolean = false,
    val isSpeaking: Boolean = false,
    val habits: List<String> = emptyList()
)

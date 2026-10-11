package com.aiassistant.app.ai

data class AiRequest(
    val model: String,
    val messages: List<MessagePayload>
)

data class MessagePayload(
    val role: String,
    val content: String
)

package com.aiassistant.app.data.repository

import com.aiassistant.app.ai.AIService
import com.aiassistant.app.ai.AiRequest
import com.aiassistant.app.ai.MessagePayload
import com.aiassistant.app.data.local.AssistantDao
import com.aiassistant.app.data.local.HabitEntity
import com.aiassistant.app.data.local.MessageEntity

class AssistantRepository(
    private val dao: AssistantDao,
    private val aiService: AIService
) {
    suspend fun saveHabit(name: String, description: String) {
        dao.insertHabit(HabitEntity(name = name, description = description))
    }

    suspend fun saveMessage(sender: String, content: String, direction: String) {
        dao.insertMessage(MessageEntity(sender = sender, content = content, direction = direction))
    }

    suspend fun getHabits(): List<String> = dao.getHabits().map { it.name }

    suspend fun askAssistant(prompt: String): String {
        val request = AiRequest(
            model = "gpt-4o-mini",
            messages = listOf(MessagePayload(role = "user", content = prompt))
        )

        return try {
            val response = aiService.chat(request)
            response.choices.firstOrNull()?.message?.content ?: "I can help with that."
        } catch (e: Exception) {
            "I can help with your plans, messages, calls, and habits."
        }
    }
}

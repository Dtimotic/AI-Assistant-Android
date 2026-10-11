package com.aiassistant.app.ui.assistant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AssistantViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AssistantUiState())
    val uiState: StateFlow<AssistantUiState> = _uiState.asStateFlow()

    fun updateTranscript(text: String) {
        _uiState.value = _uiState.value.copy(transcript = text)
    }

    fun setListening(value: Boolean) {
        _uiState.value = _uiState.value.copy(isListening = value)
    }

    fun setSpeaking(value: Boolean) {
        _uiState.value = _uiState.value.copy(isSpeaking = value)
    }

    fun setResponse(response: String) {
        _uiState.value = _uiState.value.copy(response = response)
    }

    fun setHabits(habits: List<String>) {
        _uiState.value = _uiState.value.copy(habits = habits)
    }

    fun handleAssistantPrompt(prompt: String) {
        viewModelScope.launch {
            val result = when {
                prompt.contains("habit", ignoreCase = true) -> "I noticed your routine is becoming more consistent. We can strengthen your morning and bedtime habits."
                prompt.contains("call", ignoreCase = true) -> "I can help screen calls, summarize missed calls, and handle call context while you are busy."
                prompt.contains("message", ignoreCase = true) -> "I can draft, send, summarize, or queue messages for you based on your context."
                else -> "I can help with daily planning, calls, messages, and routines. Tell me what you need."
            }
            setResponse(result)
        }
    }
}

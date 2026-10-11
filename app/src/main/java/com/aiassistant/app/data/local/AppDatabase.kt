package com.aiassistant.app.ui.assistant

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AssistantScreen(
    viewModel: AssistantViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.SmartToy,
                        contentDescription = null
                    )
                    Text(
                        text = "AI Assistant",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(text = state.response)
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text("Live transcript")
                    Text(text = if (state.transcript.isBlank()) "Listening for your command..." else state.transcript)
                }
            }

            Button(
                onClick = {
                    viewModel.setListening(!state.isListening)
                    viewModel.updateTranscript(if (!state.isListening) "" else "Listening started")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(imageVector = Icons.Default.Mic, contentDescription = null)
                Text(text = if (state.isListening) "Stop Listening" else "Start Listening")
            }

            Button(
                onClick = {
                    viewModel.setSpeaking(true)
                    viewModel.setResponse("Speaking your next action aloud.")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(imageVector = Icons.Default.RecordVoiceOver, contentDescription = null)
                Text("Speak Reply")
            }

            Button(
                onClick = {
                    viewModel.handleAssistantPrompt("I want help with my daily habits and messages.")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ask Assistant")
            }

            if (state.habits.isNotEmpty()) {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text("Habits learned")
                        state.habits.forEach { habit ->
                            Text("• $habit")
                        }
                    }
                }
            }
        }
    }
}

package com.aiassistant.app.ai

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AIService {
    @POST("/v1/chat/completions")
    suspend fun chat(
        @Body request: AiRequest,
        @Header("Authorization") authorization: String = "Bearer YOUR_API_KEY"
    ): AiResponse
}

object AIServiceFactory {
    fun create(): AIService {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openai.com")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(AIService::class.java)
    }
}

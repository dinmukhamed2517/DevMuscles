package kz.just_code.devmuscles.repository.gpt.model

import com.google.gson.annotations.SerializedName

data class RequestBody(
    @SerializedName("model")
    val model: String = "gpt-3.5-turbo",

    @SerializedName("messages")
    val messages: List<Message>,

    @SerializedName("temperature")
    val temperature: Int = 1,

    @SerializedName("top_p")
    val topP: Int = 1,

    @SerializedName("n")
    val n: Int = 1,
    @SerializedName("stream")
    val stream: Boolean = false,

    @SerializedName("max_tokens")
    val maxTokens: Int = 250,

    @SerializedName("presence_penalty")
    val presencePenalty: Int = 0,

    @SerializedName("frequency_penalty")
    val frequencyPenalty: Int = 0,
)

package kz.just_code.devmuscles.repository.gpt.model

import com.google.gson.annotations.SerializedName

data class RequestBody(
    @SerializedName("model")
    val model: String,

    @SerializedName("messages")
    val messages: List<Message>,

    @SerializedName("temperature")
    val temperature: Int,

    @SerializedName("top_p")
    val topP: Int,

    @SerializedName("n")
    val n: Int,
    @SerializedName("stream")
    val stream: Boolean,

    @SerializedName("max_tokens")
    val maxTokens: Int,

    @SerializedName("presence_penalty")
    val presencePenalty: Int,

    @SerializedName("frequency_penalty")
    val frequencyPenalty: Int,
)

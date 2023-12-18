package kz.just_code.devmuscles.utilities

import androidx.annotation.DrawableRes

data class AdDao(
    val id: Int,
    val title: String,
    val description: String,
    @DrawableRes
    val url: Int,
)
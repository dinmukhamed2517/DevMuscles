package kz.just_code.devmuscles.repository.workout.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize

data class Workout(
    val bodyPart: String? = null,
    val equipment: String? = null,
    val gifUrl: String? = null,
    val id: Int? = null,
    val name: String? = null,
    val target: String? = null,
    val secondaryMuscles: List<String>? = null,
    val instructions: List<String>? = null,
) : Parcelable

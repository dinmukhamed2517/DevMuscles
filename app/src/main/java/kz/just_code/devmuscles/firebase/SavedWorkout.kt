package kz.just_code.devmuscles.firebase

import kz.just_code.devmuscles.repository.workout.model.Workout

data class SavedWorkout(
    val workout: Workout = Workout(),
    val savedTime: String = "",
    val completed:Boolean = false,
)
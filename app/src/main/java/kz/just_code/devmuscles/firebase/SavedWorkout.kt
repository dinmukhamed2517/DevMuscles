package kz.just_code.devmuscles.firebase

import kz.just_code.devmuscles.repository.workout.model.Workout

data class SavedWorkout(
    val workout: Workout,
    val savedTime: String,
)
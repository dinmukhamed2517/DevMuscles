package kz.just_code.devmuscles.repository.workout

import kz.just_code.devmuscles.repository.workout.model.Workout


interface WorkoutRepository {
    suspend fun getWorkoutList(): List<Workout>?

    suspend fun getWorkoutListByTarget(target: String): List<Workout>?

}
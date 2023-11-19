package kz.just_code.devmuscles.repository

import dagger.Binds
import javax.inject.Singleton



interface WorkoutRepository {
    suspend fun getWorkoutList():List<Workout>?

    suspend fun getWorkoutListByTarget(target:String):List<Workout>?

}
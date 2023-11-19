package kz.just_code.devmuscles.repository.workout

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kz.just_code.devmuscles.network.workout.WorkoutApi
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepositoryModule(api: WorkoutApi): WorkoutRepository {
        return WorkoutRepositoryImpl(api)
    }
}
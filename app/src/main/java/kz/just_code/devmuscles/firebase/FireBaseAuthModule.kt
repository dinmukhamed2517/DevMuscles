package kz.just_code.devmuscles.firebase

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
object FireBaseAuthModule {

    @Provides
    fun provideFireBaseAuth():FirebaseAuth{
        return FirebaseAuth.getInstance()
    }

    @Provides
    fun provideUserDao(firebaseAuth:FirebaseAuth):UserDao{
        return UserDao(firebaseAuth)
    }

}
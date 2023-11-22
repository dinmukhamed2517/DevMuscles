package kz.just_code.devmuscles.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
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

}
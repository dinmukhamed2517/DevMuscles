package kz.just_code.devmuscles.fragments

import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.userProfileChangeRequest
import dagger.hilt.android.AndroidEntryPoint
import kz.just_code.devmuscles.base.BaseFragment
import kz.just_code.devmuscles.databinding.FragmentEditProfileBinding
import javax.inject.Inject


@AndroidEntryPoint
class EditProfileFragment:BaseFragment<FragmentEditProfileBinding>(FragmentEditProfileBinding::inflate) {

    @Inject lateinit var firebaseAuth:FirebaseAuth
    override var showBottomNavigation: Boolean = false
    override fun onBindView() {
        val user =firebaseAuth.currentUser
        super.onBindView()
        with(binding){
            saveBtn.setOnClickListener {
                val newDisplayName = etName.text.toString()
                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(newDisplayName)
                    .build()

                user?.updateProfile(profileUpdates)?.addOnCompleteListener {
                    if(it.isSuccessful){
                        Toast.makeText(requireContext(), "Name is changed", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }
}
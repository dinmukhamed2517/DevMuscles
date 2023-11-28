package kz.just_code.devmuscles.fragments

import android.net.Uri
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.hilt.android.AndroidEntryPoint
import kz.just_code.devmuscles.base.BaseFragment
import kz.just_code.devmuscles.databinding.FragmentEditProfileBinding
import kz.just_code.devmuscles.firebase.Gender
import kz.just_code.devmuscles.firebase.Goal
import kz.just_code.devmuscles.firebase.Level
import kz.just_code.devmuscles.firebase.User
import kz.just_code.devmuscles.firebase.FRDBWrapper
import kz.just_code.devmuscles.firebase.UserDao
import kz.just_code.devmuscles.repository.workout.model.Workout
import javax.inject.Inject

@AndroidEntryPoint
class EditProfileFragment : BaseFragment<FragmentEditProfileBinding>(FragmentEditProfileBinding::inflate) {

    @Inject
    lateinit var firebaseAuth: FirebaseAuth
    private lateinit var storageReference: StorageReference
    private var imageUri: Uri? = null
    private lateinit var userDao:UserDao



    override var showBottomNavigation: Boolean = false

    override fun onBindView() {
        super.onBindView()
        val user = firebaseAuth.currentUser
        val uid = user?.uid
        storageReference = FirebaseStorage.getInstance().getReference("Users/" + "${uid}")
        userDao = UserDao(firebaseAuth)
        with(binding) {
            saveBtn.setOnClickListener {
                binding.loading.isVisible = true
                userDao.saveData(getUser()){
                    binding.loading.isVisible = false
            }

            uploadBtn.setOnClickListener {

                }
            }
            avatar.setOnClickListener {
                resultLauncher.launch("image/*")
            }
        }
    }
    private fun getUser() = User(
        "Dimash",
        "Serik",
        20,
        "This is my bio",
        188,
        65,
        75,
        Goal.GAIN_MUSCLES,
        Level.INTERMEDIATE,
    )

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()){
            imageUri = it
    }

    private fun uploadProfilePic(){
        imageUri?.let {
            storageReference.putFile(it).addOnSuccessListener {
                Toast.makeText(requireContext(), "Successfully uploaded", Toast.LENGTH_SHORT).show()


            }.addOnFailureListener{
                Toast.makeText(requireContext(), "Unable to upload profile pic", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

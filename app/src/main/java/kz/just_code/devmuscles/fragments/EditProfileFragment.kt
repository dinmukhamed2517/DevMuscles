package kz.just_code.devmuscles.fragments

import android.net.Uri
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.hilt.android.AndroidEntryPoint
import kz.just_code.devmuscles.base.BaseFragment
import kz.just_code.devmuscles.databinding.FragmentEditProfileBinding
import kz.just_code.devmuscles.firebase.UserDao
import javax.inject.Inject

@AndroidEntryPoint
class EditProfileFragment : BaseFragment<FragmentEditProfileBinding>(FragmentEditProfileBinding::inflate) {

    @Inject
    lateinit var firebaseAuth: FirebaseAuth
    private lateinit var storageReference: StorageReference
    private var imageUri: Uri? = null

    @Inject
    lateinit var userDao:UserDao



    override var showBottomNavigation: Boolean = false

    override fun onBindView() {
        super.onBindView()
        val user = firebaseAuth.currentUser
        val uid = user?.uid
        storageReference = FirebaseStorage.getInstance().getReference("Users/" + "${uid}")
        with(binding) {

            avatar.setOnClickListener {
                resultLauncher.launch("image/*")
            }

            backBtn.setOnClickListener {
                findNavController().popBackStack()
            }
            saveBtn.setOnClickListener {
                binding.loading.isVisible = true
                if(imageUri != null){
                    uploadProfilePic()
                }
                updateProfileInfo()
            }
        }
    }

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()){
            binding.avatar.setImageURI(it)
            imageUri = it
    }

    private fun uploadProfilePic(){
        imageUri?.let {
            storageReference.putFile(it).addOnSuccessListener {task->
                task.metadata?.reference?.downloadUrl?.addOnSuccessListener {uri->
                    val imgUrl = uri.toString()
                    userDao.saveProfilePic(imgUrl)
                    binding.loading.isVisible = false
                    showCustomDialog("Success", "Picture saved")

                }


            }.addOnFailureListener{
                Toast.makeText(requireContext(), "Unable to upload profile pic", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun updateProfileInfo(){
        if(binding.etBio.text?.isNotEmpty() == true){
            binding.tilBio.isErrorEnabled = false
            userDao.saveBio(binding.etBio.text.toString())
        }
        else{
            binding.tilBio.error = "Fill up"
            binding.tilBio.isErrorEnabled = true
        }

        if(binding.etGoalWeight.text?.isNotEmpty() == true){
            binding.tilGoalweight.isErrorEnabled = false
            userDao.saveGoalWeight(binding.etGoalWeight.text.toString().toInt())
        }
        else{
            binding.tilGoalweight.error = "Fill up"
            binding.tilGoalweight.isErrorEnabled = true
        }
        binding.loading.isVisible = false
    }
}

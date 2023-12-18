package kz.just_code.devmuscles.fragments


import android.net.Uri
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.StorageReference
import dagger.hilt.android.AndroidEntryPoint
import kz.just_code.devmuscles.base.BaseFragment
import kz.just_code.devmuscles.base.SharedViewModel
import kz.just_code.devmuscles.databinding.FragmentUserInformationBinding
import kz.just_code.devmuscles.firebase.User
import kz.just_code.devmuscles.firebase.UserDao
import javax.inject.Inject


@AndroidEntryPoint
class UserInformationFragment :
    BaseFragment<FragmentUserInformationBinding>(FragmentUserInformationBinding::inflate) {
    private val viewModel: SharedViewModel by activityViewModels()

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    @Inject
    lateinit var userDao: UserDao
    private var imageUri: Uri? = null

    @Inject
    lateinit var storageReference: StorageReference


    override var showBottomNavigation: Boolean = false

    override fun onBindView() {
        super.onBindView()
        var ok = true
        with(binding) {
            nextBtn.setOnClickListener {

                if (imageUri != null) {
                    uploadProfilePic()
                }
                if (name.text?.isEmpty() == true) {
                    nameLayout.error = "Fill up"
                    nameLayout.isErrorEnabled = true
                    ok = false
                } else {
                    nameLayout.isErrorEnabled = false
                    viewModel.name = name.text.toString()
                    ok = true
                }
                if (lastname.text?.isEmpty() == true) {
                    lastnameLayout.error = "Fill up"
                    lastnameLayout.isErrorEnabled = true
                    ok = false

                } else {
                    lastnameLayout.isErrorEnabled = false
                    viewModel.lastname = lastname.text.toString()
                    ok = true
                }
                if (bio.text?.isEmpty() == true) {
                    bioLayout.error = "Fill up"
                    bioLayout.isErrorEnabled = true
                    ok = false

                } else {
                    bioLayout.isErrorEnabled = false
                    viewModel.bio = bio.text.toString()
                    ok = true

                }
                if (ok) {
                    val user = User(
                        viewModel.name,
                        viewModel.lastname,
                        viewModel.age,
                        viewModel.bio,
                        viewModel.height,
                        viewModel.weight,
                        viewModel.goalWeight,
                        viewModel.goal,
                        viewModel.level
                    )
                    userDao.saveData(user)
                    findNavController().navigate(
                        UserInformationFragmentDirections.actionUserInformationFragmentToHome()
                    )
                }
            }
            avatar.setOnClickListener {
                resultLauncher.launch("image/*")
            }
        }
    }

    private fun uploadProfilePic() {
        imageUri?.let {
            storageReference.putFile(it).addOnSuccessListener { task ->
                task.metadata?.reference?.downloadUrl?.addOnSuccessListener { uri ->
                    val imgUrl = uri.toString()
                    userDao.saveProfilePic(imgUrl)
                    showCustomDialog("Success", "Information saved")

                }

            }.addOnFailureListener {
                Toast.makeText(requireContext(), "Unable to upload profile pic", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) {
        binding.avatar.setImageURI(it)
        imageUri = it
    }
}
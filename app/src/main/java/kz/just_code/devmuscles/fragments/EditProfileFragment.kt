package kz.just_code.devmuscles.fragments

import android.net.Uri
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.hilt.android.AndroidEntryPoint
import kz.just_code.devmuscles.R
import kz.just_code.devmuscles.base.BaseFragment
import kz.just_code.devmuscles.databinding.FragmentEditProfileBinding
import javax.inject.Inject


@AndroidEntryPoint
class EditProfileFragment:BaseFragment<FragmentEditProfileBinding>(FragmentEditProfileBinding::inflate) {

    @Inject lateinit var firebaseAuth:FirebaseAuth
    override var showBottomNavigation: Boolean = false
    private lateinit var storageRef:StorageReference
    private lateinit var firestore:FirebaseFirestore
    private var imageUri: Uri? = null
    override fun onBindView() {
        super.onBindView()
        val user = firebaseAuth.currentUser
        initVars()
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

            avatar.setOnClickListener {
                resultLauncher.launch("image/*")
            }


            uploadBtn.setOnClickListener {
                if (user != null) {
                    imageUri?.let { uri -> uploadImage(user.uid, uri) }
                }


            }
        }
    }

    private fun uploadImage(userId: String?, imageUri: Uri) {
        val storageRef = userId?.let {
            FirebaseStorage.getInstance().reference.child("user_images").child(
                it
            )
        }

        storageRef?.putFile(imageUri)
            ?.addOnSuccessListener {
                storageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                    saveImageUriToDatabase(userId, downloadUri.toString())
                }
            }
            ?.addOnFailureListener { exception ->
                Toast.makeText(requireContext(), "Failed to upload image: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun saveImageUriToDatabase(userId: String?, imageUrl: String) {
        userId?.let { uid ->
            val userImageMap = hashMapOf("pic" to imageUrl)

            firestore.collection("user_images").document(uid)
                .set(userImageMap)
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), "Image saved successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(requireContext(), "Failed to save image: ${exception.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
    private fun loadUserImage(userId: String?) {
        userId?.let { uid ->
            firestore.collection("user_images").document(uid)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        val imageUrl = documentSnapshot.getString("pic")
                        imageUrl?.let {
                             Glide.with(requireContext())
                                 .load(it)
                                 .into(binding.avatar)
                        }
                    } else {

                        Toast.makeText(requireContext(), "there is no image", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(requireContext(), "Failed to retrieve image: ${exception.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }



    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()){
        imageUri = it
    }
    private fun initVars(){
        storageRef = FirebaseStorage.getInstance().reference.child("user_images")

        firestore = FirebaseFirestore.getInstance()
    }
}
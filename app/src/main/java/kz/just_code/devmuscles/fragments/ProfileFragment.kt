package kz.just_code.devmuscles.fragments

import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.hilt.android.AndroidEntryPoint
import kz.just_code.devmuscles.R
import kz.just_code.devmuscles.base.BaseFragment
import kz.just_code.devmuscles.databinding.FragmentProfileBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment :BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {
    @Inject  lateinit var firebaseAuth:FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var storageReference: StorageReference
    override fun onBindView() {
        super.onBindView()

        val currentUser = firebaseAuth.currentUser

        with(binding){
            username.text = currentUser?.displayName
            if(currentUser?.metadata?.creationTimestamp != null){
                val lastSignIn = getNormalData(currentUser?.metadata?.lastSignInTimestamp)
                lastOnline.text = "Joined:\n $lastSignIn"
            }

            signOutBtn.setOnClickListener {
                signOut()
            }
            editProfileBtn.setOnClickListener {
                findNavController().navigate(
                    R.id.action_profile_to_editProfileFragment
                )
            }
        }
    }

//    override fun onResume() {
//        super.onResume()
//        getProfileImage()
//    }
    private fun getNormalData(millis:Long?):String{
        val millis = millis?.let { Date(it) }
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val formattedData = dateFormat.format(millis)
        return formattedData
    }

    private fun getProfileImage(){
        if(firebaseAuth.currentUser != null){
            storageReference = FirebaseStorage.getInstance().reference.child("Users/${firebaseAuth.currentUser?.uid}")
            val localFile = File.createTempFile("tempImage", "jpg")
            storageReference.getFile(localFile).addOnSuccessListener {
                val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                binding.avatar.setImageBitmap(bitmap)

            }.addOnFailureListener{
                Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }else{
            binding.avatar.setImageResource(R.drawable.avatar_placeholder)
        }
    }
    private fun signOut(){
        var alertDialog: AlertDialog? = null
        alertDialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle("Sign out")
            .setMessage("Are you sure you want to sign out?")
            .setPositiveButton("Yes") { _, _ ->
                firebaseAuth.signOut()
                alertDialog?.dismiss()
                findNavController().navigate(
                    R.id.action_profile_to_loginFragment
                )
            }
            .setNegativeButton("Cancel") { _, _ ->
                alertDialog?.dismiss()
            }
            .show()
    }
}
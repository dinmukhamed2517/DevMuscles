package kz.just_code.devmuscles.fragments

import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import kz.just_code.devmuscles.R
import kz.just_code.devmuscles.base.BaseFragment
import kz.just_code.devmuscles.databinding.FragmentProfileBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment :BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {
    @Inject  lateinit var firebaseAuth:FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    override fun onBindView() {
        super.onBindView()
        firestore = FirebaseFirestore.getInstance()

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
    private fun getNormalData(millis:Long?):String{
        val millis = millis?.let { Date(it) }
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val formattedData = dateFormat.format(millis)
        return formattedData
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


    override fun onStart() {
        super.onStart()
        if(firebaseAuth.currentUser == null){
            findNavController().navigate(
                R.id.action_profile_to_loginFragment
            )
        }
        else {
            loadUserImage(firebaseAuth.currentUser!!.uid)
        }
    }
}
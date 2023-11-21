package kz.just_code.devmuscles.fragments

import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
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
    override fun onBindView() {
        super.onBindView()
        with(binding){
            val currentUser = firebaseAuth.currentUser

            username.text = currentUser?.displayName
            if(currentUser?.metadata?.lastSignInTimestamp != null){
                val lastSignIn = getNormalData(currentUser?.metadata?.lastSignInTimestamp)
                lastOnline.text = "Last online:\n $lastSignIn"
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
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
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


    override fun onStart() {
        super.onStart()
        if(firebaseAuth.currentUser == null){
            findNavController().navigate(
                R.id.action_profile_to_loginFragment
            )
        }
    }
}
package kz.just_code.devmuscles.fragments

import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kz.just_code.devmuscles.base.BaseFragment
import kz.just_code.devmuscles.databinding.FragmentSignUpBinding
import javax.inject.Inject


@AndroidEntryPoint

class SignUpFragment : BaseFragment<FragmentSignUpBinding>(FragmentSignUpBinding::inflate) {
    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    override var showBottomNavigation: Boolean = false

    override fun onBindView() {
        super.onBindView()
        binding.signUpBtn.setOnClickListener {
            val email = binding.emailInput.text.toString()
            val password = binding.passwordInput.text.toString()
            val confirmPassword = binding.passwordConfInput.text.toString()



            if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                if (password == confirmPassword) {
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                binding.emailLayout.isErrorEnabled = false
                                findNavController().navigate(
                                    SignUpFragmentDirections.actionSignUpToGenderFragment()
                                )
                            } else {
                                binding.emailLayout.isErrorEnabled = true
                                binding.passwordConfLayout.isErrorEnabled = true
                                binding.passwordLayout.isErrorEnabled = true
                                binding.passwordConfLayout.error = "Something is wrong"
                                binding.passwordLayout.error = "Something is wrong"
                                binding.emailLayout.error = "Something is wrong"
                            }
                        }

                } else {
                    Toast.makeText(
                        requireContext(),
                        "Passwords are not matching",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(requireContext(), "Enter something", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onPause() {
        super.onPause()

    }

    override fun onResume() {
        super.onResume()

    }
}
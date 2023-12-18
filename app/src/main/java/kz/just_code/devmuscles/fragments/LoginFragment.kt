package kz.just_code.devmuscles.fragments

import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kz.just_code.devmuscles.R
import kz.just_code.devmuscles.base.BaseFragment
import kz.just_code.devmuscles.databinding.FragmentLoginBinding
import javax.inject.Inject


@AndroidEntryPoint

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    override var showBottomNavigation: Boolean = false

    override fun onBindView() {
        super.onBindView()

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        binding.loginBtn.setOnClickListener {
            val email = binding.emailInput.text.toString()
            val password = binding.passwordInput.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(
                            R.id.action_loginFragment_to_home
                        )
                    } else {
                        binding.emailLayout.isErrorEnabled = true
                        binding.passwordLayout.isErrorEnabled = true
                        binding.passwordLayout.error = "Something is wrong"
                        binding.emailLayout.error = "Something is wrong"
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Enter something", Toast.LENGTH_SHORT).show()
            }
        }
        binding.noAccount.setOnClickListener {
            findNavController().navigate(
                R.id.action_loginFragment_to_sign_up
            )
        }
    }

    val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            findNavController().navigate(
                R.id.action_loginFragment_to_home
            )
        }
    }
}


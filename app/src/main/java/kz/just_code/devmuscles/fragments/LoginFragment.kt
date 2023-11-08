package kz.just_code.devmuscles.fragments

import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import kz.just_code.devmuscles.R
import kz.just_code.devmuscles.base.BaseFragment
import kz.just_code.devmuscles.databinding.FragmentLoginBinding

class LoginFragment:BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onBindView() {
        super.onBindView()
        firebaseAuth = FirebaseAuth.getInstance()
        binding.loginBtn.setOnClickListener {
            val email = binding.emailInput.text.toString()
            val password = binding.passwordInput.text.toString()
            if(email.isNotEmpty() && password.isNotEmpty()){
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if(it.isSuccessful){
                        Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(
                            R.id.action_loginFragment_to_home
                        )
                    }
                    else{
                        Toast.makeText(requireContext(), it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else{
                Toast.makeText(requireContext(), "Enter something", Toast.LENGTH_SHORT).show()
            }
        }

    }
}
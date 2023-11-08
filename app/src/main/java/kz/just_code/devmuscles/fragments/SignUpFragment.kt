package kz.just_code.devmuscles.fragments

import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import kz.just_code.devmuscles.R
import kz.just_code.devmuscles.base.BaseFragment
import kz.just_code.devmuscles.databinding.FragmentSignUpBinding

class SignUpFragment:BaseFragment<FragmentSignUpBinding>(FragmentSignUpBinding::inflate) {
    private lateinit var firebaseAuth:FirebaseAuth
    override fun onBindView() {
        super.onBindView()

        binding.loginBtn.setOnClickListener {
            findNavController().navigate(
                R.id.action_profile_to_loginFragment
            )
        }
        firebaseAuth = FirebaseAuth.getInstance()
        binding.signUpBtn.setOnClickListener {
            val email = binding.emailInput.text.toString()
            val password = binding.passwordInput.text.toString()
            val confirmPassword = binding.passwordConfInput.text.toString()



            if(email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()){
                if(password == confirmPassword){
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                        if(it.isSuccessful){
                            findNavController().navigate(
                                R.id.action_profile_to_loginFragment
                            )
                        }
                        else{
                            Toast.makeText(requireContext(), it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }

                }
                else{
                    Toast.makeText(requireContext(), "Passwords are not matching", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(requireContext(), "Enter something", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
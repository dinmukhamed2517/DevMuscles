package kz.just_code.devmuscles.fragments

import androidx.navigation.fragment.findNavController
import kz.just_code.devmuscles.R
import kz.just_code.devmuscles.base.BaseFragment
import kz.just_code.devmuscles.databinding.FragmentProfileBinding

class ProfileFragment:BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {
    override fun onBindView() {
        super.onBindView()

        binding.signOutBtn.setOnClickListener {
            findNavController().navigate(
                R.id.action_profile_to_loginFragment
            )
        }
    }
}
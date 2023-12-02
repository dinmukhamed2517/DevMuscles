package kz.just_code.devmuscles.onboarding

import androidx.navigation.fragment.findNavController
import kz.just_code.devmuscles.R
import kz.just_code.devmuscles.base.BaseFragment
import kz.just_code.devmuscles.databinding.FragmentSecondBinding

class SecondFragment:BaseFragment<FragmentSecondBinding>(FragmentSecondBinding::inflate) {
    override var showBottomNavigation: Boolean = false
    override fun onBindView() {
        super.onBindView()
        binding.skipBtn.setOnClickListener {
            findNavController().navigate(
                R.id.action_viewPagerFragment_to_home
            )
        }
    }
}
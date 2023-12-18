package kz.just_code.devmuscles.fragments

import androidx.navigation.fragment.findNavController
import kz.just_code.devmuscles.base.BaseFragment
import kz.just_code.devmuscles.databinding.FragmentGenderBinding

class GenderFragment : BaseFragment<FragmentGenderBinding>(FragmentGenderBinding::inflate) {
    override var showBottomNavigation: Boolean = false

    override fun onBindView() {
        super.onBindView()
        binding.nextBtn.setOnClickListener {
            findNavController().navigate(
                GenderFragmentDirections.actionGenderFragmentToAgeFragment()
            )
        }
    }
}
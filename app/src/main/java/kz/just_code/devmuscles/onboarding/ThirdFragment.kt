package kz.just_code.devmuscles.onboarding

import android.content.Context
import androidx.navigation.fragment.findNavController
import kz.just_code.devmuscles.R
import kz.just_code.devmuscles.base.BaseFragment
import kz.just_code.devmuscles.databinding.FragmentThirdBinding

class ThirdFragment : BaseFragment<FragmentThirdBinding>(FragmentThirdBinding::inflate) {
    override var showBottomNavigation: Boolean = false


    override fun onBindView() {
        super.onBindView()
        binding.finish.setOnClickListener {
            findNavController().navigate(R.id.action_viewPagerFragment_to_home)
            onBoardingFinished()
        }
    }


    private fun onBoardingFinished() {
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished", true)
        editor.apply()
    }
}
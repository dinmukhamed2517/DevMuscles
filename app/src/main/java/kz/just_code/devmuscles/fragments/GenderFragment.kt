package kz.just_code.devmuscles.fragments

import android.annotation.SuppressLint
import androidx.navigation.fragment.findNavController
import kz.just_code.devmuscles.R
import kz.just_code.devmuscles.base.BaseFragment
import kz.just_code.devmuscles.databinding.FragmentGenderBinding

class GenderFragment:BaseFragment<FragmentGenderBinding>(FragmentGenderBinding::inflate) {

    @SuppressLint("ResourceAsColor")
    override fun onBindView() {
        super.onBindView()




        binding.femaleLayout.setOnClickListener {
            binding.femaleLayout.setBackgroundResource(R.drawable.circle_bg_green)
            binding.femaleImg.setColorFilter(R.color.black)
            binding.titleFemale.setTextColor(R.color.black)
        }
        binding.maleLayout.setOnClickListener {
            binding.maleLayout.setBackgroundResource(R.drawable.circle_bg_green)
            binding.maleImg.setColorFilter(R.color.black)
            binding.titleMale.setTextColor(R.color.black)
        }

       /* binding.nextBtn.setOnClickListener {
            findNavController().navigate(
                R.id.action_genderFragment_to_ageFragment
            )
        }*/
    }
}
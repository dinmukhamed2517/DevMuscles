package kz.just_code.devmuscles.fragments

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kz.just_code.devmuscles.base.BaseFragment
import kz.just_code.devmuscles.base.SharedViewModel
import kz.just_code.devmuscles.databinding.FragmentAgeBinding


@AndroidEntryPoint
class AgeFragment:BaseFragment<FragmentAgeBinding>(FragmentAgeBinding::inflate) {

    private val viewModel:SharedViewModel by activityViewModels()
    override var showBottomNavigation: Boolean = false

    override fun onBindView() {
        with(binding){
            numberPicker.maxValue = 100
            numberPicker.minValue = 18
            numberPicker.wrapSelectorWheel = false
            numberPicker.setOnValueChangedListener { picker, oldVal, newVal ->
                viewModel.age = newVal
            }
            nextBtn.setOnClickListener {
                findNavController().navigate(
                    AgeFragmentDirections.actionAgeFragmentToHeightFragment()
                )
            }
        }

    }
}
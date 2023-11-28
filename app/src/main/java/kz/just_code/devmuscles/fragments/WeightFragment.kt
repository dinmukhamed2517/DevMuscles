package kz.just_code.devmuscles.fragments

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kz.just_code.devmuscles.base.BaseFragment
import kz.just_code.devmuscles.base.SharedViewModel
import kz.just_code.devmuscles.databinding.FragmentWeightBinding


@AndroidEntryPoint
class WeightFragment:BaseFragment<FragmentWeightBinding>(FragmentWeightBinding::inflate) {
    private val viewModel: SharedViewModel by activityViewModels()
    override var showBottomNavigation: Boolean = false


    override fun onBindView() {
        super.onBindView()
        with(binding) {
            numberPicker.maxValue = 200
            numberPicker.minValue = 0
            numberPicker.wrapSelectorWheel = false
            numberPicker.setOnValueChangedListener { picker, oldVal, newVal ->
                viewModel.weight = newVal
            }
            nextBtn.setOnClickListener {
                findNavController().navigate(
                    WeightFragmentDirections.actionWeightFragmentToGoalWeightFragment()
                )
            }
        }
    }
}
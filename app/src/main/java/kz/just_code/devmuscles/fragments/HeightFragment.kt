package kz.just_code.devmuscles.fragments

import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kz.just_code.devmuscles.base.BaseFragment
import kz.just_code.devmuscles.base.SharedViewModel
import kz.just_code.devmuscles.databinding.FragmentHeightBinding


@AndroidEntryPoint

class HeightFragment : BaseFragment<FragmentHeightBinding>(FragmentHeightBinding::inflate) {
    private val viewModel: SharedViewModel by activityViewModels()
    override var showBottomNavigation: Boolean = false

    override fun onBindView() {
        super.onBindView()
        with(binding) {
            numberPicker.maxValue = 200
            numberPicker.minValue = 150
            numberPicker.wrapSelectorWheel = false
            numberPicker.setOnValueChangedListener { picker, oldVal, newVal ->
                viewModel.height = newVal
            }
            nextBtn.setOnClickListener {
                if (viewModel.height == null) {
                    Toast.makeText(requireContext(), "Choose the value", Toast.LENGTH_SHORT).show()
                } else {
                    findNavController().navigate(
                        HeightFragmentDirections.actionHeightFragmentToWeightFragment()
                    )
                }

            }
        }
    }
}
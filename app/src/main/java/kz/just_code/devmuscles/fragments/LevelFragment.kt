package kz.just_code.devmuscles.fragments

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kz.just_code.devmuscles.base.BaseFragment
import kz.just_code.devmuscles.base.SharedViewModel
import kz.just_code.devmuscles.databinding.FragmentLevelBinding
import kz.just_code.devmuscles.firebase.Level


@AndroidEntryPoint
class LevelFragment : BaseFragment<FragmentLevelBinding>(FragmentLevelBinding::inflate) {

    private val viewModel: SharedViewModel by activityViewModels()
    override var showBottomNavigation: Boolean = false
    override fun onBindView() {
        super.onBindView()
        with(binding) {
            val goals = arrayOf(
                "Beginner", "Intermediate", "Advance"
            )
            numberPicker.minValue = 0
            numberPicker.maxValue = goals.size - 1
            numberPicker.wrapSelectorWheel = false
            numberPicker.displayedValues = goals
            numberPicker.setOnValueChangedListener { _, _, newVal ->
                viewModel.level = Level.values()[newVal]
            }
            nextBtn.setOnClickListener {
                findNavController().navigate(
                    LevelFragmentDirections.actionLevelFragmentToUserInformationFragment()
                )
            }
        }
    }
}
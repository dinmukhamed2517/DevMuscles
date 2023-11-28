package kz.just_code.devmuscles.fragments

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kz.just_code.devmuscles.base.BaseFragment
import kz.just_code.devmuscles.base.SharedViewModel
import kz.just_code.devmuscles.databinding.FragmentGoalBinding
import kz.just_code.devmuscles.firebase.Goal


@AndroidEntryPoint
class GoalFragment:BaseFragment<FragmentGoalBinding>(FragmentGoalBinding::inflate) {
    private val viewModel: SharedViewModel by activityViewModels()
    override var showBottomNavigation: Boolean = false

    override fun onBindView() {
        super.onBindView()
        with(binding) {
            val goals = arrayOf(
                "Gain Muscles", "Get fitter", "Lose weight"
            )
            numberPicker.minValue = 0
            numberPicker.maxValue = goals.size -1
            numberPicker.wrapSelectorWheel = false
            numberPicker.displayedValues = goals
            numberPicker.setOnValueChangedListener { _, _, newVal ->
                viewModel.goal = Goal.values()[newVal]
            }
            nextBtn.setOnClickListener {
                findNavController().navigate(
                    GoalFragmentDirections.actionGoalFragmentToLevelFragment()
                )
            }
        }
    }

}
package kz.just_code.devmuscles.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kz.just_code.devmuscles.databinding.FragmentBottomSheetWdBinding

class WorkoutDetailsBottomSheetFragment:BottomSheetDialogFragment() {

    private val args: WorkoutDetailsBottomSheetFragmentArgs by navArgs()

    private lateinit var binding: FragmentBottomSheetWdBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottomSheetWdBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val item = args.workoutItem
        with(binding){
            if (item.name != null && item.name.length <15){
                title.text = item.name.titlecaseFirstChar()
            }
            else{
                title.text = item.name?.substring(0,15)?.titlecaseFirstChar()
            }
            Glide.with(requireContext())
                .load(item.gifUrl)
                .into(gif)
            target.text = "${item.target?.titlecaseFirstChar()} Workout"
            description.text = item.instructions?.joinToString(separator = " ")
            equip.text = item.equipment
            gif.transitionName = "gif_${item.id}"
            gif.setOnClickListener {
                findNavController().navigate(
                    WorkoutDetailsBottomSheetFragmentDirections.actionWorkoutDetailsBottomSheetFragmentToFullscreenFragment(item)
                )
            }
        }

    }
}
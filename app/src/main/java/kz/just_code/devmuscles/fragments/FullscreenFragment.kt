package kz.just_code.devmuscles.fragments

import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import kz.just_code.devmuscles.base.BaseFragment
import kz.just_code.devmuscles.databinding.FragmentFullscreenBinding

class FullscreenFragment :
    BaseFragment<FragmentFullscreenBinding>(FragmentFullscreenBinding::inflate) {
    private val args: FullscreenFragmentArgs by navArgs()
    override var showBottomNavigation: Boolean = false
    override fun onBindView() {
        super.onBindView()
        Glide.with(requireContext())
            .load(args.workoutItem.gifUrl)
            .into(binding.image)


        binding.image.transitionName = "gif_${args.workoutItem.id}"

        sharedElementEnterTransition =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
    }

}
package kz.just_code.devmuscles.onboarding

import kz.just_code.devmuscles.base.BaseFragment
import kz.just_code.devmuscles.databinding.FragmentViewPagerBinding

class ViewPagerFragment:BaseFragment<FragmentViewPagerBinding>(FragmentViewPagerBinding::inflate) {

    override var showBottomNavigation: Boolean = false
    override fun onBindView() {
        super.onBindView()
        val fragmentList = arrayListOf(
            FirstFragment(),
            SecondFragment(),
            ThirdFragment()
        )
        val adapter = ViewPagerAdapter(fragmentList, requireActivity().supportFragmentManager, lifecycle)
        binding.viewPager.adapter = adapter

    }

}
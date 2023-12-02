package kz.just_code.devmuscles.onboarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewbinding.ViewBinding
import androidx.viewpager2.adapter.FragmentStateAdapter
import kz.just_code.devmuscles.base.BaseFragment

class ViewPagerAdapter(
    list: ArrayList<BaseFragment<out ViewBinding>>,
    fm:FragmentManager, lifecycle:Lifecycle
): FragmentStateAdapter(fm, lifecycle) {

    private val fragmentList:ArrayList<BaseFragment<out ViewBinding>> = list
    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}
package com.mike.instagramclone.adapters

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter


class ViewPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fm, lifecycle) {

    val fragmentList: MutableList<Fragment> = mutableListOf<Fragment>()
    val titleList = mutableListOf<String>()

    /*class ViewPagerViewHolder(private val fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        fun bind(user: User, onClick: (User) -> Unit) {

            view.setOnClickListener {
            }


        }
    }*/
    /*fun getItem(position: Int): Fragment {
        return fragmentList.get(position)
    }*/

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    fun getPageTitle(position: Int): CharSequence? {
        return titleList[position]
    }

    fun addFragment(fragment: Fragment, title: String) {
        fragmentList.add(fragment)
        titleList.add(title)
    }

    override fun createFragment(position: Int): Fragment {
        Log.d("AQUI", fragmentList[position].toString())
        return fragmentList[position]
    }

}


/*
public static class MyAdapter extends FragmentPagerAdapter {
    public MyAdapter(FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public Fragment getItem(int position) {
        return ArrayListFragment.newInstance(position);
    }
}*/
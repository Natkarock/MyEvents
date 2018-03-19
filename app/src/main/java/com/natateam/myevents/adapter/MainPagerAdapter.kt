package com.natateam.myevents.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.natateam.myevents.R
import com.natateam.myevents.mainlist.fragments.BirthdaysFragment
import com.natateam.myevents.mainlist.fragments.RepeatFragment
import com.natateam.myevents.mainlist.fragments.TodoFragment

/**
 * Created by macbook on 05/03/ 15.
 */
class MainPagerAdapter(fm: FragmentManager?, context: Context) : FragmentPagerAdapter(fm) {
    enum class Types(val type: Int) {
        TODO(0),
        REEAT(1),
        BIRTHDAYS(2)
    }

    val titles: Array<out String>? = context.resources.getStringArray(R.array.main_titles)
    override fun getCount(): Int {
        if (titles != null) {
            return titles!!.size;
        }
        return 0;
    }

    override fun getItem(position: Int): Fragment {
        when (position) {
            Types.TODO.type -> return TodoFragment()
            Types.REEAT.type -> return RepeatFragment()
            Types.BIRTHDAYS.type -> return BirthdaysFragment()
            else -> return Fragment()
        }

    }

    override fun getPageTitle(position: Int): CharSequence? {
        val title:String? = titles?.get(position)
        return title
    }

}
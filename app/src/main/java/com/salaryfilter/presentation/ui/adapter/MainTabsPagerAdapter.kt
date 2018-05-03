package com.salaryfilter.presentation.ui.adapter

import android.app.Fragment
import android.app.FragmentManager
import android.support.v13.app.FragmentPagerAdapter
import com.salaryfilter.R
import com.salaryfilter.presentation.ui.fragment.SalaryGraphFragment
import com.salaryfilter.presentation.ui.fragment.SalaryListFragment
import com.salaryfilter.util.getKeyByValue
import java.util.*

/**
 * Created by Max Makeychik on 21.06.2017.
 */

class MainTabsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private var salariesListFragment: SalaryListFragment? = null
    private var salariesGraphFragment: SalaryGraphFragment? = null

    override fun getItem(position: Int): Fragment? {
        when (position) {
            POSITION_SALARIES_LIST -> {
                if (salariesListFragment == null) {
                    salariesListFragment = SalaryListFragment()
                }
                return salariesListFragment
            }
            POSITION_SALARIES_GRAPH -> {
                if (salariesGraphFragment == null) {
                    salariesGraphFragment = SalaryGraphFragment()
                }
                return salariesGraphFragment
            }
        }
        return null
    }

    override fun getCount(): Int {
        return BOTTOM_NAVIGATION_ITEMS_COUNT
    }

    companion object {

        const val BOTTOM_NAVIGATION_ITEMS_COUNT = 2
        const val POSITION_SALARIES_LIST = 0
        const val POSITION_SALARIES_GRAPH = 1

        private val bottomBarPositionsMenuIdsMap: MutableMap<Int, Int>

        init {
            bottomBarPositionsMenuIdsMap = HashMap()
            bottomBarPositionsMenuIdsMap[POSITION_SALARIES_LIST] = R.id.action_tab_salaries_list
            bottomBarPositionsMenuIdsMap[POSITION_SALARIES_GRAPH] = R.id.action_tab_salaries_graph
        }

        /**
         * Returns index of {@param menuItemId} in fragments array
         *
         * @param menuItemId - id of menu item
         */
        fun getFragmentPositionByMenuId(menuItemId: Int): Int {
            return bottomBarPositionsMenuIdsMap.getKeyByValue(menuItemId)
                    ?: throw IllegalStateException("no fragment found for position $menuItemId")
        }

        /**
         * Returns index of {@param position} in fragments array
         *
         * @param position
         */
        fun getMenuIdByFragmentPosition(position: Int): Int {
            return bottomBarPositionsMenuIdsMap[position]
                    ?: throw IllegalStateException("menu id for position not found")
        }
    }

}

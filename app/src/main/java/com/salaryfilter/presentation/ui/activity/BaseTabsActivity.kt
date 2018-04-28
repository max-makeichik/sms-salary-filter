package com.salaryfilter.presentation.ui.activity

import android.app.Fragment
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.support.v4.view.ViewPager
import android.text.TextUtils
import android.view.MenuItem
import com.salaryfilter.presentation.ui.adapter.MainTabsPagerAdapter
import com.salaryfilter.util.removeShiftMode
import kotlinx.android.synthetic.main.content_bottom_navigation.*
import timber.log.Timber

/**
 * Created by Maksim Makeychik on 08.03.2018.
 */
abstract class BaseTabsActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    //------ BottomNavigationView --------------------//

    protected fun setBottomBar() {
        getViewPager().apply {
            adapter = MainTabsPagerAdapter(this@BaseTabsActivity, fragmentManager)
            offscreenPageLimit = MainTabsPagerAdapter.BOTTOM_NAVIGATION_ITEMS_COUNT
        }
        bottom_navigation.removeShiftMode()
        val menuView = bottom_navigation.getChildAt(0) as BottomNavigationMenuView
        for (i in 0 until menuView.childCount) {
            val itemView = menuView.getChildAt(i) as BottomNavigationItemView
            itemView.setShiftingMode(false)
            itemView.setChecked(false)
        }
        bottom_navigation.setOnNavigationItemSelectedListener(this)
        setBottomBarItemSelectedOnFirstTime(MainTabsPagerAdapter.POSITION_SALARIES_LIST)
    }

    /**
     * Returns index of {@param menuItemId} in fragments array
     *
     * @param menuItemId - id of menu item
     */
    private fun getFragmentPositionByMenuId(menuItemId: Int): Int {
        return MainTabsPagerAdapter.getFragmentPositionByMenuId(menuItemId)
    }

    /**
     * Returns index of {@param position} in fragments array
     *
     * @param position
     */
    private fun getMenuIdByFragmentPosition(position: Int): Int {
        return MainTabsPagerAdapter.getMenuIdByFragmentPosition(position)
    }

    /**
     * Sets bottom bar tab with {@param position} selected
     *
     * @param position
     */
    protected fun setBottomBarItemSelected(position: Int) {
        getViewPager().setCurrentItem(position, false)
        bottom_navigation.selectedItemId = getMenuIdByFragmentPosition(position)
    }

    protected fun setBottomBarItemSelectedOnFirstTime(position: Int) {
        setBottomBarItemSelected(position)
    }

    /**
     * @return current bottom bar position selected
     */
    protected fun getBottomBarPositionSelected(): Int {
        return getFragmentPositionByMenuId(getBottomBarSelectedItemId())
    }

    /**
     * @return current selected item id in bottom bar
     */
    private fun getBottomBarSelectedItemId(): Int {
        return bottom_navigation.selectedItemId
    }

    /**
     * @return may return null if the fragment has not been instantiated yet for that position - this depends on if the fragment has been viewed
     * yet OR is a sibling covered by [android.support.v4.view.ViewPager.setOffscreenPageLimit]. Can use this to call methods on
     * the current positions fragment.
     */
    protected fun getFragmentForPosition(position: Int): Fragment? {
        val tag = makeFragmentName(getViewPager().id, position.toLong())
        return fragmentManager.findFragmentByTag(tag)
    }

    abstract fun getViewPager(): ViewPager

    /**
     * @param containerViewId the ViewPager this adapter is being supplied to
     * @param id              pass in getItemId(position) as this is whats used internally in this class
     * @return the tag used for this pages fragment
     */
    private fun makeFragmentName(containerViewId: Int, id: Long): String {
        return "android:switcher:$containerViewId:$id"
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        Timber.d("onNavigationItemSelected: ")
        val position = getFragmentPositionByMenuId(item.itemId)
        val fragmentTitle = ""
        getViewPager().setCurrentItem(position, false)
        if (TextUtils.isEmpty(fragmentTitle)) {
            setToolbar(item.title.toString())
        } else {
            setToolbar(fragmentTitle)
        }
        return true
    }
}

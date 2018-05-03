package com.salaryfilter.presentation.ui.fragment.base

import android.app.Fragment
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceFragment
import com.arellomobile.mvp.MvpDelegate

/**
 * Created by Max Makeichik on 03-May-18.
 */
open class MvpPrefsFragment: PreferenceFragment() {

    private var stateSaved: Boolean = false
    private val mvpDelegate: MvpDelegate<out PreferenceFragment> by lazy { MvpDelegate(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mvpDelegate.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()

        stateSaved = false

        mvpDelegate.onAttach()
    }

    override fun onResume() {
        super.onResume()

        stateSaved = false

        mvpDelegate.onAttach()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        stateSaved = true

        mvpDelegate.onSaveInstanceState(outState)
        mvpDelegate.onDetach()
    }

    override fun onStop() {
        super.onStop()

        mvpDelegate.onDetach()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        mvpDelegate.onDetach()
        mvpDelegate.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()

        //We leave the screen and respectively all fragments will be destroyed
        if (activity.isFinishing) {
            mvpDelegate.onDestroy()
            return
        }

        // When we rotate device isRemoving() return true for fragment placed in backstack
        // http://stackoverflow.com/questions/34649126/fragment-back-stack-and-isremoving
        if (stateSaved) {
            stateSaved = false
            return
        }

        // See https://github.com/Arello-Mobile/Moxy/issues/24
        var anyParentIsRemoving = false

        if (Build.VERSION.SDK_INT >= 17) {
            var parent: Fragment? = parentFragment
            while (!anyParentIsRemoving && parent != null) {
                anyParentIsRemoving = parent.isRemoving
                parent = parent.parentFragment
            }
        }

        if (isRemoving || anyParentIsRemoving) {
            mvpDelegate.onDestroy()
        }
    }
}
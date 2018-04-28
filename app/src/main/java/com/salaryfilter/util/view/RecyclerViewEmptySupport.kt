package com.salaryfilter.util.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View

class RecyclerViewEmptySupport : RecyclerView {

    private var emptyView: View? = null

    fun changeVisibility() {
        if (adapter != null && emptyView != null) {
            if (adapter.itemCount == 0) {
                emptyView!!.visibility = View.VISIBLE
                this@RecyclerViewEmptySupport.visibility = View.GONE
            } else {
                emptyView!!.visibility = View.GONE
                this@RecyclerViewEmptySupport.visibility = View.VISIBLE
            }
        }
    }

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {}

    fun setEmptyView(emptyView: View) {
        this.emptyView = emptyView
    }
}
package com.salaryfilter.util

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Created by Max Makeychik on 01-Sep-17.
 */

object RxUtil {

    /**
     * Enum to pass in Observable instead of Void,
     * when no data passing is needed
     */
    enum class Irrelevant {
        INSTANCE
    }

    fun runWithDelay(action: Runnable, delayInMs: Long) {
        Observable.timer(delayInMs, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe { action.run() }
    }

}

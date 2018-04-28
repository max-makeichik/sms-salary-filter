package com.salaryfilter.di.annotation

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

import javax.inject.Scope

/**
 * Created by Andrey V. Murzin on 01.08.17.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
annotation class GlobalScope

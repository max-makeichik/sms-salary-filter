package com.salaryfilter.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat

object PermissionUtil {

    fun hasLocationPermissions(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    fun requestLocationPermissions(activity: Activity, code: Int) {
        val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
        ActivityCompat.requestPermissions(activity, permissions, code)
    }

    fun hasStorageReadPermissions(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

    }

    fun requestStorageReadPermissions(activity: Activity, code: Int) {
        val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        ActivityCompat.requestPermissions(activity, permissions, code)
    }

    fun hasStorageWritePermissions(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
    }

    fun requestStorageWritePermissions(activity: Activity, code: Int) {
        val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        ActivityCompat.requestPermissions(activity, permissions, code)
    }

    fun hasReadSalaryPermissions(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED
    }

    fun requestReadSalaryPermissions(activity: Activity, code: Int) {
        val permissions = arrayOf(Manifest.permission.READ_SMS)
        ActivityCompat.requestPermissions(activity, permissions, code)
    }


}

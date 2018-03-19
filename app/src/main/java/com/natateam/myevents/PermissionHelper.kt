package com.natateam.myevents

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat

import java.util.ArrayList

/**
 * Created by macbook on 28/09/ 15.
 */
object PermissionHelper {
    const val MY_PERMISSIONS_SMS_CODE = 401
    const val MY_PERMISSIONS_CONTACTS_CODE = 402
    const val MY_PERMISSIONS_PHONE_CODE = 403
    fun showMemoryPermission(activity: Activity) {
        if (ContextCompat.checkSelfPermission(activity,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    MY_PERMISSIONS_SMS_CODE)
        }
    }

    fun showLocationAndPhonePermission(activity: Activity) {
        val permissions = ArrayList<String>()
        if (ContextCompat.checkSelfPermission(activity,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(android.Manifest.permission.ACCESS_COARSE_LOCATION)

        }
        if (ContextCompat.checkSelfPermission(activity,
                        android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(android.Manifest.permission.READ_PHONE_STATE)
        }
        if (permissions.size > 0) {
            ActivityCompat.requestPermissions(activity,
                    permissions.toTypedArray(),
                    MY_PERMISSIONS_SMS_CODE)
        }
    }

    fun showContactsPermission(activity: Activity) {
        if (ContextCompat.checkSelfPermission(activity,
                        android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    arrayOf(android.Manifest.permission.READ_CONTACTS),
                    MY_PERMISSIONS_CONTACTS_CODE)
        }
    }

    fun isPermissionGranted(context: Context, permission: String): Boolean {
        return if (ContextCompat.checkSelfPermission(context,
                        permission) != PackageManager.PERMISSION_GRANTED) {
            false
        } else {
            true
        }
    }

    fun showSmsPermission(context: Activity) {
        if (ContextCompat.checkSelfPermission(context,
                        android.Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context,
                    arrayOf(Manifest.permission.RECEIVE_SMS),
                    MY_PERMISSIONS_SMS_CODE)
        }
    }

}

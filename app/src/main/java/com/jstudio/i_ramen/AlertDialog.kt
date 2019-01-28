package com.jstudio.i_ramen

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import androidx.navigation.Navigation


class AlertDialog : Fragment() {

    fun alertDialog(title: String, message: String, activity: FragmentActivity?) :Boolean{

        val AlertDialogBuiluder = AlertDialog.Builder(activity)
        var isPressedOK = false

        AlertDialogBuiluder
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("はい", (DialogInterface.OnClickListener { dialog, which ->
                // OK button pressed
                isPressedOK = true
            }))
            .setNegativeButton("いいえ", null)
            .show()
        return isPressedOK
    }
}
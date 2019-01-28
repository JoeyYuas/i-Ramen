package com.jstudio.i_ramen

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.support.v4.app.FragmentActivity


class AlertDialog {

    fun alertDialog(title: String, message: String, activity: FragmentActivity?) {

        val AlertDialogBuiluder = AlertDialog.Builder(activity)

        AlertDialogBuiluder
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK", (DialogInterface.OnClickListener { dialog, which ->
                // OK button pressed
                println("AlertDaialogOK")
            }))
            .setNegativeButton("Cancel", null)
            .show()
    }
}
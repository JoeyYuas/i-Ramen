package com.jstudio.i_ramen

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class Main_Fragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        
//        日付処理
        val dateManager = DateManager()
        val dateList = dateManager.getIntDate()
//        val day: Int = datelist[0]

        for (date in dateList) {
            println("ListDate ${date}")
        }

//        データ保存処理
        val SharedPreferences =
            this.activity?.getSharedPreferences("SAVE_DATE", Context.MODE_PRIVATE)

        val alertDialog = AlertDialog()
        alertDialog.alertDialog("aaaaa","aaaaa", this.activity)





        return inflater.inflate(R.layout.fragment_main, container, false)
    }
}
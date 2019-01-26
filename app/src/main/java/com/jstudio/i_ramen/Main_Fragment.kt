package com.jstudio.i_ramen

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class Main_Fragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        
//        日付処理
        val dateManager = Date_Manager()
        val datelist = dateManager.getIntDate()
//        val day: Int = datelist[0]

        for (date in datelist) {
            println("ListDate ${date}")
        }

//        データ保存処理



        return inflater.inflate(R.layout.fragment_main, container, false)
    }
}
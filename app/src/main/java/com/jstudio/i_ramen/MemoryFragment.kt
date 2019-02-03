package com.jstudio.i_ramen

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class MemoryFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val thisView = inflater.inflate(R.layout.fragment_memory, container, false)

        //        アクションバーのタイトルを変更
        activity!!.title = "ラーメンの記録"


        return thisView
    }

}
package com.jstudio.i_ramen

import android.arch.persistence.room.Room
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlin.concurrent.thread

class MemoryFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val thisView = inflater.inflate(R.layout.fragment_memory, container, false)

        //        アクションバーのタイトルを変更
        activity!!.title = "ラーメンの記録"

        val eatMemoryDB = Room.databaseBuilder(
            activity!!.applicationContext, AppDatabase::class.java, "database-name"
        ).build()

        thread {

            val eatMemoryList = eatMemoryDB.eatMemoryDAO().getAll()

            val viewManager = LinearLayoutManager(context)
            val viewAdapter = RecyclerViewAdapter(eatMemoryList)

            //RecyclerViewの設定
            val recyclerView = activity!!.findViewById<RecyclerView>(R.id.recyclerView).apply {
                // use this setting to improve performance if you know that changes
                // in content do not change the layout size of the RecyclerView
                setHasFixedSize(true)

                // use a linear layout manager
                layoutManager = viewManager

                // specify an viewAdapter (see also next example)
                adapter = viewAdapter

            }

            for(a in eatMemoryList){
                println(a.ramenName)
                println(a.eatDate)
            }

        }

        return thisView
    }

}
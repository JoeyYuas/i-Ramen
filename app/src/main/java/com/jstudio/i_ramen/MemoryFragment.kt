package com.jstudio.i_ramen

import android.arch.persistence.room.Room
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
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
        return thisView
    }

//    onBackPressedで更新する
    override fun onResume() {
        super.onResume()
        val eatMemoryDB = Room.databaseBuilder(
            activity!!.applicationContext, AppDatabase::class.java, "database-name"
        ).build()

        thread {

            val eatMemoryList = eatMemoryDB.eatMemoryDAO().getAll().asReversed()

            val viewManager = LinearLayoutManager(context)
            val viewAdapter = RecyclerViewAdapter(eatMemoryList)

            activity!!.runOnUiThread {
                //RecyclerViewの設定
                val recyclerView =
                    activity!!.findViewById<RecyclerView>(R.id.recyclerView).apply {

                        setHasFixedSize(true)
                        // use a linear layout manager
                        layoutManager = viewManager
                        // specify an viewAdapter (see also next example)
                        adapter = viewAdapter

                        viewAdapter.setOnItemClickListener(object : RecyclerViewAdapter.OnItemClickListener {
                            override fun onClick(view: View, uid: Int) {
                                val memorySettingIntent = Intent(context, MemorySetting::class.java)
                                memorySettingIntent.putExtra("UID_KEY", uid)
                                startActivity(memorySettingIntent)
                            }
                        })
                    }

//            RecyclerViewに枠線を追加
                recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            }
        }
    }

}

package com.jstudio.i_ramen

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.SharedElementCallback
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class MainFragment : Fragment() {

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
            activity!!.getSharedPreferences("SAVE_DATE", Context.MODE_PRIVATE)

//        クエスト処理
        if (dateManager.isNextDay(SharedPreferences)) {
            this.alertDialog(
                "デイリークエスト", "新規クエストを確認しますか？",
                this.activity
            )
        }

        return inflater.inflate(R.layout.fragment_main, container, false)
    }


    //    アラートダイアログの実装
    private fun alertDialog(title: String, message: String, activity: FragmentActivity?) {

        val AlertDialogBuiluder = android.app.AlertDialog.Builder(activity)

        AlertDialogBuiluder
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("はい", (DialogInterface.OnClickListener { dialog, which ->
                // OK button pressed
                fragmentManager!!.beginTransaction().replace(R.id.fragment_container, QuestFragment()).commit()
            }))
            .setNegativeButton("いいえ", null)
            .show()
    }
}
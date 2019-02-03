package com.jstudio.i_ramen

import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.Checkable
import android.widget.TextView
import androidx.navigation.findNavController
import java.text.SimpleDateFormat
import java.util.*

class QuestFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val thisView = inflater.inflate(R.layout.fragment_quest, container, false)

        //        アクションバーのタイトルを変更
        activity!!.title = "クエスト"

        //        日付が経過していたら新規のクエストを表示する
        lateinit var todaysQuest: String
        val dateManager = DateManager()
        val SharedPreferences =
            activity!!.getSharedPreferences("SAVE_DATE_QUEST", Context.MODE_PRIVATE)

        val readText = ReadText()
        val editor = SharedPreferences.edit()

        if (dateManager.isNextDay(SharedPreferences)) {
            todaysQuest = readText.getTextOneLine(activity!!).replace("\\n", "\n")
            editor.putBoolean("quest_clear", false)
            editor.putString("quest_message", todaysQuest)
            editor.apply()
        } else {
            todaysQuest = SharedPreferences.getString("quest_message", "")
        }

        val isQuestClear = SharedPreferences.getBoolean("quest_clear", false)

        val questMessage = thisView.findViewById<TextView>(R.id.Quest_Message)
        questMessage.text = todaysQuest

        val questButton = thisView.findViewById<Button>(R.id.Quest_Clear)

//        クエストをクリアしていたらクエストボタンを非表示／再表示ボタンを表示＝＞未実装
        if(isQuestClear){
            questButton.visibility = INVISIBLE
        }

        questButton.setOnClickListener {
            this.alertDialog("確認", "クエストを完了しますか？", SharedPreferences, questMessage, questButton)
        }

        return thisView
    }

    private fun alertDialog(
        title: String,
        message: String,
        SharedPreferences: SharedPreferences,
        questMessage: TextView,
        questButton: Button
    ) {

        val AlertDialogBuiluder = android.app.AlertDialog.Builder(activity)

        AlertDialogBuiluder
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("はい", (DialogInterface.OnClickListener { dialog, which ->
                // OK button pressed
                val editor = SharedPreferences.edit()
                editor.putBoolean("quest_clear", true)
                editor.putString("quest_message", "今日のクエストは完了しています")
                editor.apply()

//                ポイントを追加
                val SharedPreferences_PointManager =
                    activity!!.getSharedPreferences("SAVE_DATE_ALERT", Context.MODE_PRIVATE)

                val havePoint = (PointManager().getPoint(SharedPreferences_PointManager) + 10)
                PointManager().setPoint(SharedPreferences_PointManager, havePoint)

                PopUpManager.PopUpManagerTrophy(context).showPopupWindow()
                questMessage.text = "今日のクエストは完了しています"

                questButton.visibility = INVISIBLE

//               //activityにintentを引き渡している。（Fragment-Activity-Fragmentの形がきれい）
//                activity!!.intent.putExtra("QUEST_CLEAR", true)
//                fragmentManager!!.beginTransaction().replace(R.id.fragment_container, MainFragment()).commit()
            }))
            .setNegativeButton("いいえ", null)
            .show()
    }
}
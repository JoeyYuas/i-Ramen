package com.jstudio.i_ramen

import android.animation.ObjectAnimator
import android.arch.persistence.room.Room
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.*
import com.robinhood.ticker.TickerView
import kotlinx.android.synthetic.main.calendar_popup.view.*
import razerdp.basepopup.BasePopupWindow
import razerdp.basepopup.BasePopupWindow.OnDismissListener
import razerdp.basepopup.QuickPopupBuilder
import kotlin.concurrent.thread
import razerdp.basepopup.QuickPopupConfig
import android.widget.Toast






class MainFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

//        viewを保持（とりあえずフラグメントではこれをやっておくと便利かも）
        val thisView = inflater.inflate(R.layout.fragment_main, container, false)

//        アクションバーのタイトルを変更
        activity!!.title = "トップページ"

        //        DB
        val eatMemory = EatMemory()

        eatMemory.ramenName = "Sample"
        eatMemory.eatDate = "2018/01/05"

        val eatMemoryDB = Room.databaseBuilder(
            activity!!.applicationContext, AppDatabase::class.java, "database-name"
        ).build()

        val eatMemoryList: MutableList<EatMemory> = mutableListOf()
        eatMemoryList.add(eatMemory)

        thread {

            eatMemoryDB.eatMemoryDAO().insertAll(eatMemoryList)

        }


        //activityに保持されたintentをgetしている（あまりフラグメント間で遷移させないほうがいいかも）
//        val isQuestClear = activity!!.intent.getBooleanExtra("QUEST_CLEAR", false)
//
//        if (isQuestClear) {
//            Toast.makeText(context, "クエストクリアおめでとう", Toast.LENGTH_LONG).show()
//            activity!!.intent.putExtra("QUEST_CLEAR", false)
//        }


//        日付処理
        val dateManager = DateManager()

//        データ保存処理
        val SharedPreferences =
            activity!!.getSharedPreferences("SAVE_DATE_ALERT", Context.MODE_PRIVATE)

//        ポイント処理（呼び出し、表示、保存）
        val pointManager = PointManager()
        //pointManager.setPoint(SharedPreferences, 250)
        val havePoint = pointManager.getPoint(SharedPreferences)

        val ticker = thisView.findViewById<TickerView>(R.id.havePoint)
        ticker.text = "$havePoint pt"

//        val havePointText = thisView.findViewById<TextView>(R.id.havePoint)
//        havePointText.text = "${havePoint} pt"
        val eatCount = thisView.findViewById<TextView>(R.id.eat_count)
        eatCount.text = "${havePoint / 100} 杯まで食べられます"

        val progressBar = thisView.findViewById<ProgressBar>(R.id.progressBar)
        progressBar.max = 100
        this.onProgressChanged(progressBar, havePoint % 100)


//        クエスト処理
        if (dateManager.isNextDay(SharedPreferences)) {
            this.alertDialog(
                "デイリークエスト", "新規クエストを確認しますか？",
                this.activity
            )
        }

//        EATボタンを押したときの挙動（カレンダーを表示）
        val view = PopUpManager.PopUpManagerCalendar(context)
        val eatButton = thisView.findViewById<Button>(R.id.button_Eat)
        eatButton.setOnClickListener {
            view.showPopupWindow()
            val calendarView = view.findViewById<CalendarView>(R.id.calendarView)

//            カレンダーを選択したときの挙動（日付を読み取る）
            calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
                println(year)
                println(month)
                println(dayOfMonth)
            }
        }

        view.onDismissListener = object : BasePopupWindow.OnDismissListener() {
            override fun onDismiss() {
                Toast.makeText(context, "dismiss", Toast.LENGTH_SHORT).show()
            }
        }


        return thisView
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

    private fun onProgressChanged(progressBar: ProgressBar, percentage: Int) {
        val animation = ObjectAnimator.ofInt(progressBar, "progress", percentage)
        animation.duration = 500 // 0.5秒間でアニメーションする
        animation.interpolator = DecelerateInterpolator()
        animation.start()
    }

}

package com.jstudio.i_ramen

import android.animation.ObjectAnimator
import android.arch.persistence.room.Room
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.*
import com.robinhood.ticker.TickerView
import razerdp.basepopup.BasePopupWindow
import kotlin.concurrent.thread
import android.widget.Toast

class MainFragment : Fragment() {

    //        日付処理
    private val dateManager = DateManager()
    private val dateList = dateManager.getIntDate()

    var selectedYear = 0
    var selectedMonth = 0
    var selectedDayOfMonth = 0

    private val eatMemory = EatMemory()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

//        viewを保持
        val thisView = inflater.inflate(R.layout.fragment_main, container, false)

//        アクションバーのタイトルを変更
        activity!!.title = "トップページ"

//        データ保存処理
        val SharedPreferences =
            activity!!.getSharedPreferences("SAVE_DATE_ALERT", Context.MODE_PRIVATE)

//        ポイント処理（呼び出し、表示、保存）
        val pointManager = PointManager()
        var havePoint = pointManager.getPoint(SharedPreferences)


        val ticker = thisView.findViewById<TickerView>(R.id.havePoint)
        ticker.text = "$havePoint pt"

        val eatCount = thisView.findViewById<TextView>(R.id.eat_count)
        eatCount.text = "${havePoint / 100} 杯まで食べられます"

        val progressBar = thisView.findViewById<ProgressBar>(R.id.progressBar)
        progressBar.max = 100
        onProgressChanged(progressBar, havePoint % 100)


//        クエスト処理
        if (dateManager.isNextDay(SharedPreferences)) {
            dailyAlertDialog(
                "デイリークエスト", "新規クエストを確認しますか？",
                this.activity
            )
        }

//        デバッグ用
        thisView.findViewById<Button>(R.id.debugButton).setOnClickListener {
            havePoint += 110

            pointManager.setPoint(SharedPreferences, havePoint + 110)

            onProgressChanged(progressBar, havePoint % 100)
            ticker.text = "$havePoint pt"
            eatCount.text = "${havePoint / 100} 杯まで食べられます"
        }

//        EATボタンを押したときの挙動（カレンダーを表示）
        val PopUpCalendarView = PopUpManager.PopUpManagerCalendar(context)
        val eatButton = thisView.findViewById<Button>(R.id.button_Eat)
        eatButton.setOnClickListener {

            if (havePoint < 100) {
                simpleAlertDialog("ポイントが足りません", "100pt貯めるまで我慢！")
            } else {

                Toast.makeText(context, "日付を選択してここをタップ", Toast.LENGTH_LONG).show()

                PopUpCalendarView.showPopupWindow()
                val calendarView = PopUpCalendarView.findViewById<CalendarView>(R.id.calendarView)

//            その日のまま閉じた場合

                selectedYear = dateList[0]
                selectedMonth = dateList[1]
                selectedDayOfMonth = dateList[2]


//            カレンダーを選択したときの挙動（日付を読み取る）
                calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
                    selectedYear = year
                    selectedMonth = month + 1
                    selectedDayOfMonth = dayOfMonth
                }
            }
        }

        PopUpCalendarView.onDismissListener = object : BasePopupWindow.OnDismissListener() {
            override fun onDismiss() {
                eatedAlertDialog(
                    "${selectedYear}年${selectedMonth}月${selectedDayOfMonth}日",
                    "\n100pt消費してラーメンを食べます",
                    SharedPreferences,
                    ticker,
                    eatCount
                )
            }
        }

        return thisView
    }


    //    アラートダイアログの実装
    private fun dailyAlertDialog(title: String, message: String, activity: FragmentActivity?) {

        val AlertDialogBuiluder = android.app.AlertDialog.Builder(activity)

        AlertDialogBuiluder
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("はい", (DialogInterface.OnClickListener { dialog, which ->
                fragmentManager!!.beginTransaction().replace(R.id.fragment_container, QuestFragment()).commit()
            }))
            .setNegativeButton("いいえ", null)
            .show()
    }

    private fun eatedAlertDialog(
        title: String,
        message: String,
        sharedPreferences: SharedPreferences,
        ticker: TickerView,
        eatCount: TextView
    ) {

        val AlertDialogBuiluder = android.app.AlertDialog.Builder(activity)

        AlertDialogBuiluder
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("はい", (DialogInterface.OnClickListener { dialog, which ->
                // OK button pressed
                //                ポイントを消費
                val SharedPreferences_PointManager =
                    activity!!.getSharedPreferences("SAVE_DATE_ALERT", Context.MODE_PRIVATE)

                val havePoint = (PointManager().getPoint(sharedPreferences) - 100)
                ticker.text = "$havePoint pt"
                eatCount.text = "${havePoint / 100} 杯まで食べられます"


                PointManager().setPoint(SharedPreferences_PointManager, havePoint)


                //        DB
                val SharedPreferences_UIDController =
                    activity!!.getSharedPreferences("UID", Context.MODE_PRIVATE)
                val uid = SharedPreferences_UIDController.getInt("UID", 0) + 1
                val editor = SharedPreferences_UIDController.edit()
                editor.putInt("UID", uid).apply()


                eatMemory.uid = uid
                eatMemory.ramenName = ""
                eatMemory.eatDate = "${selectedYear}年${selectedMonth}月${selectedDayOfMonth}日"

                val eatMemoryDB = Room.databaseBuilder(
                    activity!!.applicationContext, AppDatabase::class.java, "database-name"
                ).build()

                val eatMemoryList: MutableList<EatMemory> = mutableListOf()
                eatMemoryList.add(eatMemory)

                thread {

                    eatMemoryDB.eatMemoryDAO().insertAll(eatMemoryList)

                }

            }))
            .setNegativeButton("いいえ", (DialogInterface.OnClickListener { dialog, which ->
                dateList[0] = selectedYear
                dateList[1] = selectedMonth
                dateList[2] = selectedDayOfMonth
            }))
            .show()
    }

    private fun simpleAlertDialog(title: String, message: String) {

        val AlertDialogBuiluder = android.app.AlertDialog.Builder(activity)

        AlertDialogBuiluder
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }

    private fun onProgressChanged(progressBar: ProgressBar, percentage: Int) {
        val animation = ObjectAnimator.ofInt(progressBar, "progress", percentage)
        animation.duration = 500 // 0.5秒間でアニメーションする
        animation.interpolator = DecelerateInterpolator()
        animation.start()
    }
}

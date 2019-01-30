package com.jstudio.i_ramen

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.view.animation.DecelerateInterpolator
import android.animation.ObjectAnimator
import android.animation.Animator
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

//        viewを保持
        val thisView = inflater.inflate(R.layout.fragment_main, container, false)


//        日付処理
        val dateManager = DateManager()

//        データ保存処理
        val SharedPreferences =
            activity!!.getSharedPreferences("SAVE_DATE", Context.MODE_PRIVATE)

//        ポイント処理（呼び出し、表示、保存）
        val pointManager = PointManager()
        val havePoint = pointManager.getPoint(SharedPreferences)

        val havePointText = thisView.findViewById<TextView>(R.id.havePoint)
        havePointText.text = "${havePoint} pt"
        val eatCount = thisView.findViewById<TextView>(R.id.eat_count)
        eatCount.text = "${havePoint/100} 杯まで食べられます"

        val progressBar = thisView.findViewById<ProgressBar>(R.id.progressBar)
        progressBar.max = 100
        progressBar.progress = havePoint%100



//        クエスト処理
        if (dateManager.isNextDay(SharedPreferences)) {
            this.alertDialog(
                "デイリークエスト", "新規クエストを確認しますか？",
                this.activity
            )
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

//    private fun onProgressChanged(percentage: Int) {
//        val animation = ObjectAnimator.ofInt(progressBar, "progress", percentage)
//        animation.duration = 500 // 0.5秒間でアニメーションする
//        animation.interpolator = DecelerateInterpolator()
//        animation.start()
//    }

}

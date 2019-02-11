package com.jstudio.i_ramen

import android.arch.persistence.room.Room
import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import kotlin.concurrent.thread

class MemorySetting : AppCompatActivity() {

    lateinit var ramenNameLinearLayout: LinearLayout
    lateinit var settingDeleteLinearLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memory_setting)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "変更"

        ramenNameLinearLayout = findViewById(R.id.ramenNameLinearLayout)
        settingDeleteLinearLayout = findViewById(R.id.settingDeleteLinearLayout)

        val uid = intent.getIntExtra("UID_KEY", 0)
        val settingRamenName: TextView = findViewById(R.id.setting_ramenName)

        val eatMemoryDB = Room.databaseBuilder(
            applicationContext, AppDatabase::class.java, "database-name"
        ).build()


        thread {

            val eatMemoryList = eatMemoryDB.eatMemoryDAO().selectUid(uid)
            settingRamenName.text = eatMemoryList[0].ramenName

        }

//        食べたラーメンの名前
        ramenNameLinearLayout.setOnClickListener {
            insertRamenNameAlertDialog(uid)
        }

//        削除する機能
        settingDeleteLinearLayout.setOnClickListener {
            deleteAlertDialog(uid)
        }

    }

    private fun insertRamenNameAlertDialog(uid: Int) {

        val AlertDialogBuiluder = android.app.AlertDialog.Builder(this)
        val editText = EditText(this)

        AlertDialogBuiluder
            .setTitle("ラーメンの名前を入力してください")
            .setView(editText)
            .setPositiveButton("OK", (DialogInterface.OnClickListener { dialog, which ->
                val eatMemoryDB = Room.databaseBuilder(
                    applicationContext, AppDatabase::class.java, "database-name"
                ).build()

                thread {

                    eatMemoryDB.eatMemoryDAO().update(editText.text.toString(), uid)

                }
                val textView = findViewById<TextView>(R.id.setting_ramenName)
                textView.text = editText.text.toString()

            }))
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun deleteAlertDialog(uid: Int) {

        val AlertDialogBuiluder = android.app.AlertDialog.Builder(this)

        AlertDialogBuiluder
            .setTitle("このデータを消去しますか？")
            .setMessage("ポイントは戻ってきません")
            .setPositiveButton("OK", (DialogInterface.OnClickListener { dialog, which ->
                val eatMemoryDB = Room.databaseBuilder(
                    applicationContext, AppDatabase::class.java, "database-name"
                ).build()

                thread {

                    eatMemoryDB.eatMemoryDAO().deleteData(uid)

                }
                super.onBackPressed()
            }))
            .setNegativeButton("Cancel", null)
            .show()
    }
}


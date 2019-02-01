package com.jstudio.i_ramen
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class QuestFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val thisView = inflater.inflate(R.layout.fragment_quest, container, false)

        val questMassage = thisView.findViewById<TextView>(R.id.Quest_Massage)
        val readText = ReadText()

        questMassage.text = readText.getTextOneLine(activity!!).replace("\\n", "\n")

        return thisView
    }
}
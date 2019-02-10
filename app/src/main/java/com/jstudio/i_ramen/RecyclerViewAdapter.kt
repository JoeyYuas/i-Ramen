package com.jstudio.i_ramen

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.recycle_text_view.view.*

class RecyclerViewAdapter(private val myDataset: MutableList<EatMemory>) :
    RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {


    class MyViewHolder(val textView: View) : RecyclerView.ViewHolder(textView)


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewAdapter.MyViewHolder {
        val recycleTextView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycle_text_view, parent, false)

        return MyViewHolder(recycleTextView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val ramenName = holder.textView.findViewById<TextView>(R.id.ramenName)
        val eatedDay = holder.textView.findViewById<TextView>(R.id.eatedDate)

        ramenName.text = myDataset[position].ramenName
        eatedDay.text = myDataset[position].eatDate

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size
}


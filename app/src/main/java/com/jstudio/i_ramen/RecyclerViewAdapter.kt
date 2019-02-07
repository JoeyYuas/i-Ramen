package com.jstudio.i_ramen

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.recycle_text_view.view.*

class RecyclerViewAdapter(private val myDataset: MutableList<EatMemory>) :
    RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {



    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class MyViewHolder(val textView: View) : RecyclerView.ViewHolder(textView)


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): RecyclerViewAdapter.MyViewHolder {
        // create a new view
        val recycleTextView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycle_text_view, parent, false)
        // set the view's size, margins, paddings and layout parameters

        return MyViewHolder(recycleTextView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        val ramenName = holder.textView.findViewById<TextView>(R.id.ramenName)
        ramenName.text = myDataset[position].ramenName
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size
}
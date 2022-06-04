package com.example.mypartspal

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mypartspal.pbapi.Project

class ProjectRecyclerAdapter(private val ctx: Context, private val dataSet: ArrayList<Project>, private val clickListener: ItemClickListener): RecyclerView.Adapter<ProjectRecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener {
        val textView: TextView

        init {
            textView = view.findViewById(R.id.textView)
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            clickListener.onItemClick(v, adapterPosition)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.text_row_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.textView.text = dataSet[position].project_name
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    // parent activity will implement this method to respond to click events
    interface ItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }
}
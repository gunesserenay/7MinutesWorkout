package com.moonsunapp.a7minutesworkout

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.moonsunapp.a7minutesworkout.databinding.ItemHistoryRowBinding

class HistoryAdapter(private val items:ArrayList<String>):RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    class ViewHolder(binding: ItemHistoryRowBinding):RecyclerView.ViewHolder(binding.root){
        val llHistoryItemMain=binding.llHistoryItemMain
        val tvItemPosition=binding.tvPosition
        val tvDate=binding.tvDateItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemHistoryRowBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val date:String=items.get(position)
        holder.tvItemPosition.text="${position+1})"
        holder.tvDate.text=date

        if (position%2==0){
            holder.llHistoryItemMain.setBackgroundColor(Color.parseColor("#598690"))
        }else
        {
            holder.llHistoryItemMain.setBackgroundColor(Color.LTGRAY)
        }
    }

    override fun getItemCount(): Int {
       return items.size
    }


}
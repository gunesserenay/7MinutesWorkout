package com.moonsunapp.a7minutesworkout

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.moonsunapp.a7minutesworkout.databinding.ItemExerciseStatusBinding

class ExerciseStatusAdapter(val items:ArrayList<ExerciseModel>):RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>() {

    class ViewHolder(binding: ItemExerciseStatusBinding):RecyclerView.ViewHolder(binding.root){

        val tvItem=binding.tvItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(ItemExerciseStatusBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model:ExerciseModel=items[position]
        holder.tvItem.text=model.getId().toString()
        when{
            model.getIsSelected()->{
                holder.tvItem.background=ContextCompat.getDrawable(holder.itemView.context,R.drawable.item_circular_color_green_background)
                holder.tvItem.setTextColor(Color.parseColor("#565737"))
            }
            model.getIsCompleted()->{
                holder.tvItem.background=ContextCompat.getDrawable(holder.itemView.context,R.drawable.item_color_accent_border_ripple_background)
                holder.tvItem.setTextColor(Color.parseColor("#a485b8"))
            }
            else->{
                holder.tvItem.background=ContextCompat.getDrawable(holder.itemView.context,R.drawable.item_circular_color_grey_background)
                holder.tvItem.setTextColor(Color.parseColor("#565737"))
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
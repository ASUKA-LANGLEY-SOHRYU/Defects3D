package com.michael.a3dprintingdefects.presenter.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.michael.a3dprintingdefects.R
import com.michael.a3dprintingdefects.databinding.ListItemBinding
import com.michael.a3dprintingdefects.presenter.model.DefectListItem

class DefectsListAdapter: RecyclerView.Adapter<DefectsListAdapter.DefectsListViewHolder>() {

    private var defects: MutableList<DefectListItem> = mutableListOf()

    fun addDefect(value: DefectListItem){
        defects.add(value)
        notifyItemChanged(defects.size - 1)
        notifyDataSetChanged()
    }

    fun setDefects(value: MutableList<DefectListItem>){
        this.defects = value
        notifyItemChanged(0)
        notifyDataSetChanged()
    }

    private lateinit var onButtonClickListener: OnButtonClickListener

    interface OnButtonClickListener{
        fun onButtonClick(position: Int)
    }

    fun setOnButtonClickListener(listener: OnButtonClickListener){
        onButtonClickListener = listener
    }

    inner class DefectsListViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemBinding.bind(view)


        fun bind(defect: DefectListItem){
            binding.apply {
                name.text = defect.name
                description.text = defect.description

                Glide.with(itemView.context)
                    .load(defect.picture_url).transform(RoundedCorners(10))
                    .error(R.drawable.ic_launcher_foreground)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(image)

                open.setOnClickListener {
                    onButtonClickListener.onButtonClick(position)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefectsListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return DefectsListViewHolder(view)
    }

    override fun onBindViewHolder(holder: DefectsListViewHolder, position: Int) {
        holder.bind(defects[position])
    }

    override fun getItemCount(): Int = defects.size
}
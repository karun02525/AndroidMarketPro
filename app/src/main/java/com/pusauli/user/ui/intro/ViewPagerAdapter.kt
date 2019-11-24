package com.pusauli.user.ui.intro

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pusauli.user.R
import kotlinx.android.synthetic.main.into_item_page.view.*

class ViewPagerAdapter : RecyclerView.Adapter<PagerVH>() {

    //array of colors to change the background color of screen
    private val colors = intArrayOf(
        android.R.color.black,
        android.R.color.holo_red_light,
        android.R.color.holo_blue_dark,
        android.R.color.holo_purple
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerVH =
        PagerVH(LayoutInflater.from(parent.context).inflate(R.layout.into_item_page, parent, false))

    //get the size of color array
    override fun getItemCount(): Int = Int.MAX_VALUE

    //binding the screen with view
    override fun onBindViewHolder(holder: PagerVH, position: Int) = holder.itemView.run {
        if(position == 0){
            tvTitle.text = "ViewPager2"
            tvAbout.text = "In this application we will learn about ViewPager2"
            ivImage.setImageResource(R.drawable.ic_apple)
            container.setBackgroundResource(colors[position])
        }
        if(position == 1) {
            tvTitle.text = "ViewPager2"
            tvAbout.text = "In this application we will learn about ViewPager2"
            ivImage.setImageResource(R.drawable.veg)
            container.setBackgroundResource(colors[position])
        }
        if(position == 2) {
            tvTitle.text = "ViewPager2"
            tvAbout.text = "In this application we will learn about ViewPager2"
            ivImage.setImageResource(R.drawable.ic_shoes)
            container.setBackgroundResource(colors[position])
        }
        if(position == 3) {
            tvTitle.text = "ViewPager2"
            tvAbout.text = "In this application we will learn about ViewPager2"
            ivImage.setImageResource(R.drawable.ic_school)
            container.setBackgroundResource(colors[position])
        }
    }
}

class PagerVH(itemView: View) : RecyclerView.ViewHolder(itemView)
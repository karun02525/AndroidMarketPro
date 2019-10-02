package com.pusauli.user.ui.vender.category

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.pusauli.user.R
import kotlinx.android.synthetic.main.adapter_category_all_layout.view.*

class SpAdapter(private val context:Context, private val list:ArrayList<CategoryData>) : BaseAdapter() {

    @SuppressLint("ViewHolder")
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {

      val view:View=View.inflate(context, R.layout.adapter_category_all_layout,null)
         view.cate_name.text = ("Select Category")
         view.cate_name.text = (list[p0].name)
        return view
    }

    override fun getItem(p0: Int): Any {
       return list[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
       return list.size
    }


}
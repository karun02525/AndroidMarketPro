package com.pusauli.user.ui.category

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.pusauli.user.R
import com.pusauli.user.model.DataCategory
import com.pusauli.user.network.Const
import com.pusauli.user.utils.loadImage

class CategorySpinnerAdapter(context:Context, private val list:ArrayList<DataCategory>) : BaseAdapter() {


    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val vh: ItemRowHolder
        if (convertView == null) {
            view = mInflater.inflate(R.layout.adapter_category_all_layout, parent, false)
            vh = ItemRowHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemRowHolder
        }

        vh.cate_name.text = list[position].categoryName
        vh.imgCategory.loadImage(Const.CATEGORY_AVATAR_BASE_URL +list[position].categoryAvatar)

        return view
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return list.size
    }

    private class ItemRowHolder(row: View?) {
        val cate_name: TextView = row?.findViewById(R.id.cate_name) as TextView
        val imgCategory: ImageView = row?.findViewById(R.id.imgCategory) as ImageView

    }
}





/*



class CategorySpinnerAdapter() : BaseAdapter() {

    @SuppressLint("ViewHolder")
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {

      val view:View=View.inflate(context, R.layout.adapter_category_all_layout,null)
         view.cate_name.text = ("Select Category")
         view.cate_name.text = (list[p0].categoryName)
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


}*/

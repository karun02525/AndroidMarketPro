package com.pusauli.user.ui.vender.category

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pusauli.user.R
import com.pusauli.user.utils.toast
import kotlinx.android.synthetic.main.activity_shop_list.*
import kotlinx.android.synthetic.main.adapter_radio_category.view.*
import java.util.*


class LanguageActivity : AppCompatActivity() {


    private fun getLanData(): ArrayList<LanguageModel> {
        val listData = ArrayList<LanguageModel>()
        listData.add(LanguageModel("Medical", "med"))
        listData.add(LanguageModel("Hospital", "hos"))


        return listData
    }



    private lateinit var mLangAdapter: LangAdapter
    private var list = getLanData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_list)
        setupViews()
    }


    private fun setupViews() {
        //cat_lst.layoutManager = LinearLayoutManager(this)
        mLangAdapter = LangAdapter(list, object : LangAdapter.ItemClickListener {
            override fun onItemClicked(repos: LanguageModel) {
                toast(repos.name)
            }

        })
        //cat_lst.adapter = mLangAdapter

    }


    class LangAdapter(var list: ArrayList<LanguageModel>, var listener: ItemClickListener) :
        RecyclerView.Adapter<LangAdapter.ViewHolder>() {

        private var lastSelectedPosition = -1

        interface ItemClickListener {
            fun onItemClicked(repos: LanguageModel)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_radio_category, parent, false)
            return ViewHolder(v)
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bindItems(list[position],position)
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            @SuppressLint("SetTextI18n")
            val context = itemView.context

            fun bindItems(model: LanguageModel, position: Int) {
                itemView.cate_name.text = model.name
                itemView.cate_radio.isChecked = lastSelectedPosition == position;
                itemView.setOnClickListener {
                    listener.onItemClicked(model)
                }
                itemView.cate_radio.setOnClickListener {
                    lastSelectedPosition = adapterPosition
                    notifyDataSetChanged()
                }
            }
        }

    }
    data class LanguageModel(var name: String, var code: String)

}





package com.pusauli.user.ui.dashboard.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pusauli.user.R
import com.pusauli.user.model.CategoriModel
import com.pusauli.user.model.getCatMenuData
import com.pusauli.user.ui.dashboard.MainActivity
import com.pusauli.user.ui.store_list.StoreListActivity
import kotlinx.android.synthetic.main.adapter_menu_categories.view.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import technolifestyle.com.imageslider.FlipperView


class HomeFragment : Fragment() {


    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }


    private var list: ArrayList<CategoriModel> = getCatMenuData()
    private lateinit var mActivity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity=(activity!! as MainActivity)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v= inflater.inflate(R.layout.fragment_home, container, false)
        initView(v)
        return v
    }

    private fun initView(v: View?) {
        setLayout(v)
        val rec=v!!.recyclerView
        rec.layoutManager = GridLayoutManager(mActivity, 3)
        val mLangAdapter = MenuAdapter(list, object : MenuAdapter.ItemClickListener {
            override fun onItemClicked(repos: CategoriModel) {
                onSendStoreList(repos.code,repos.name)
            }
        })
        rec.adapter = mLangAdapter

    }

    private fun setLayout(v:View?) {
        val url = arrayOf("https://d2ile4x3f22snf.cloudfront.net/wp-content/uploads/sites/122/2017/02/20080146/manavabeachresortmoorea_restaurant_14.jpg",
            "https://studentcareercoach.files.wordpress.com/2012/06/bigstock_portrait_of_happy_teens_lookin_13855142.jpg",
            "http://tastegrowersmarket.com.au/wp-content/uploads/2016/03/produce.jpg",
            "http://cdn.odishatv.in/wp-content/uploads/2017/11/school-student.jpg",
            "https://randomwire.com/wp-content/uploads/hua-cheng-bei-electronics.jpeg",
            "http://images.newindianexpress.com/uploads/user/imagelibrary/2017/4/18/original/Where4.jpg")

        for (i in 0..5) {
            val view = FlipperView(mActivity)
            view.imageUrl = url[i]
            // .setDescription("Cool" + (i + 1));
            v!!.flipperLayout.addFlipperView(view)
            view.setOnFlipperClickListener {
              //  toast("Here " + (flipperLayout.currentPagePosition + 1))
            }
        }
    }



    private fun onSendStoreList(code: String, name: String) {
        mActivity.startActivity(
            Intent(mActivity, StoreListActivity::class.java)
                .putExtra("shop_category", code)
                .putExtra("shop_categoryName", name)
        )
    }




    class MenuAdapter(var list: ArrayList<CategoriModel>, var listener: ItemClickListener) :
        RecyclerView.Adapter<MenuAdapter.ViewHolder>() {
        interface ItemClickListener {
            fun onItemClicked(repos: CategoriModel)
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_menu_categories, parent, false)
            return ViewHolder(v)
        }
        override fun getItemCount(): Int {
            return list.size
        }
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bindItems(list[position])
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            @SuppressLint("SetTextI18n")
            val context = itemView.context
            fun bindItems(model: CategoriModel) {
                itemView.img.setImageResource(model.img)
                itemView.txt.text=model.name
                itemView.setOnClickListener {
                    listener.onItemClicked(model)
                }

            }
        }

    }

}

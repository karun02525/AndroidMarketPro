package com.pusauli.user.ui.dashboard.fragment

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pusauli.user.R
import com.pusauli.user.model.DataCategory
import com.pusauli.user.mvvm.CategoryViewModel
import com.pusauli.user.network.Const.CATEGORY_AVATAR_BASE_URL
import com.pusauli.user.ui.dashboard.MainActivity
import com.pusauli.user.ui.store_list.StoreListActivity
import com.pusauli.user.utils.loadImage
import com.pusauli.user.utils.showSnackBar
import kotlinx.android.synthetic.main.adapter_menu_categories.view.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import technolifestyle.com.imageslider.FlipperView


class HomeFragment : Fragment() {


    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    private val instanceViewModel by lazy { CategoryViewModel() }
    private var list: ArrayList<DataCategory> = arrayListOf()
    private lateinit var mActivity: MainActivity
    private lateinit var rec:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity=(activity!! as MainActivity)
        initObservers()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v= inflater.inflate(R.layout.fragment_home, container, false)
        initView(v)
        apiCall()
        return v
    }

    private fun apiCall() {
        mActivity.showProgress()
        instanceViewModel.getCategoryApi()
    }

    private fun initObservers() {
        instanceViewModel.requestCategoryData.observe(this, Observer {
            mActivity.hideProgress()
            successData(it)
        })
        instanceViewModel.errorMess.observe(this, Observer {
            mActivity.hideProgress()
            mActivity.showSnackBar(it!!)
        })
    }

    private fun successData(it: List<DataCategory>?) {
        list= it!! as ArrayList<DataCategory>
        val mLangAdapter = MenuAdapter(list, object : MenuAdapter.ItemClickListener {
            override fun onItemClicked(repos: DataCategory) {
                onSendStoreList(repos.categoryId!!,repos.categoryName!!)
            }
        })
        rec.adapter = mLangAdapter
    }

    private fun initView(v: View?) {
        setLayout(v)
        rec=v!!.recyclerView
        rec.layoutManager = GridLayoutManager(mActivity, 3)


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



    private fun onSendStoreList(category_id: String, categoryName: String) {
        mActivity.startActivity(
            Intent(mActivity, StoreListActivity::class.java)
                .putExtra("category_id", category_id)
                .putExtra("categoryName", categoryName)
        )
    }


    class MenuAdapter(var list: ArrayList<DataCategory>, var listener: ItemClickListener) :
        RecyclerView.Adapter<MenuAdapter.ViewHolder>() {
        interface ItemClickListener {
            fun onItemClicked(repos: DataCategory)
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
            fun bindItems(model: DataCategory) {
                itemView.img.loadImage(CATEGORY_AVATAR_BASE_URL+model.categoryAvatar)
                itemView.txt.text=model.categoryName
                itemView.setOnClickListener {
                    listener.onItemClicked(model)
                }

            }
        }

    }

}

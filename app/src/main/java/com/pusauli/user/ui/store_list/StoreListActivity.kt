package com.pusauli.user.ui.store_list

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pusauli.user.R
import com.pusauli.user.model.StoreListResult
import com.pusauli.user.mvvm.DashboardViewModel
import com.pusauli.user.network.Const.PROFILE_AVATAR_BASE_URL
import com.pusauli.user.network.Const.STORE_AVATAR_BASE_URL
import com.pusauli.user.ui.common.ZoomImageActivity
import com.pusauli.user.ui.dashboard.BaseActivity
import com.pusauli.user.ui.store_list.details.DetailsActivity
import com.pusauli.user.utils.loadImage
import com.pusauli.user.utils.loadImageProfile
import com.pusauli.user.utils.log
import com.pusauli.user.utils.showSnackBar
import kotlinx.android.synthetic.main.activity_shop_list.*
import kotlinx.android.synthetic.main.adapter_shop_list.view.*
import kotlinx.android.synthetic.main.toolbar.*


class StoreListActivity : BaseActivity() {

    private var listStore: ArrayList<StoreListResult> = arrayListOf()
    private val instanceViewModel by lazy { DashboardViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_list)

        initObservers()

        val categoryId = intent.getStringExtra("category_id") ?: ""
        val categoryName = intent.getStringExtra("categoryName") ?: ""

        recyclerViewList.layoutManager = LinearLayoutManager(this)
        tv_title.text = categoryName
        apiCall(categoryId)
    }


    private fun apiCall(category_id: String) {
        showProgress()
        instanceViewModel.getStoreListApi(category_id)
    }

    private fun initObservers() {
        instanceViewModel.requestStoreListData.observe(this, Observer {
            hideProgress()
            successData(it)
        })
        instanceViewModel.errorMess.observe(this, Observer {
            hideProgress()
            showSnackBar(it!!)
        })
    }

    private fun successData(it: List<StoreListResult>?) {
        listStore = it as ArrayList<StoreListResult>

        val mAdapter = StoreListAdapter(listStore, object : StoreListAdapter.ItemClickListener {
            override fun onItemClicked(repos: StoreListResult) {
                startActivity(
                    Intent(this@StoreListActivity, DetailsActivity::class.java)
                        .putExtra("vender_id", repos.venderId)
                    // .putExtra("shop_categoryName", name)
                )
            }

        })
        recyclerViewList.adapter = mAdapter
        mAdapter.notifyDataSetChanged()
    }


    fun btnBack(v: View) {
        onBackPressed()
    }


    class StoreListAdapter(var list: ArrayList<StoreListResult>, var listener: ItemClickListener) :
        RecyclerView.Adapter<StoreListAdapter.ViewHolder>() {

        interface ItemClickListener {
            fun onItemClicked(repos: StoreListResult)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_shop_list, parent, false)
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

            fun bindItems(model: StoreListResult) {
                itemView.setOnClickListener {
                    listener.onItemClicked(model)
                }


                val userPhoto = PROFILE_AVATAR_BASE_URL + model.user_avatar
                itemView.clg_logo.loadImageProfile(userPhoto)

                itemView.clg_logo.setOnClickListener {
                    context.startActivity(
                        Intent(context, ZoomImageActivity::class.java)
                            .putExtra("galleryAvatar", userPhoto)
                    )

                }

                val shopPhoto = STORE_AVATAR_BASE_URL + model.shopAvatar
                itemView.iv_shopBack.loadImage(shopPhoto)


                itemView.tv_shop_name.text = model.shopName ?: ""
                itemView.tv_location_city.text = model.shopAddress ?: ""

            }
        }
    }

    private fun logs(msg: String) {
        log("StoreList", msg)
    }

}





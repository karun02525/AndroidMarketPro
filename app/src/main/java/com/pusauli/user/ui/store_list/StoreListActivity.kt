package com.pusauli.user.ui.store_list

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.pusauli.user.R
import com.pusauli.user.model.ResponseStoreList
import com.pusauli.user.model.StoreListResult
import com.pusauli.user.network.Const.PROFILE_AVATAR_BASE_URL
import com.pusauli.user.network.Const.STORE_AVATAR_BASE_URL
import com.pusauli.user.network.NetworkUtil
import com.pusauli.user.network.RestClient
import com.pusauli.user.ui.dashboard.BaseActivity
import com.pusauli.user.ui.store_list.details.DetailsActivity
import com.pusauli.user.ui.vender.fragment.ZoomImageActivity
import com.pusauli.user.utils.SharedPref
import com.pusauli.user.utils.loadImage
import com.pusauli.user.utils.loadImageProfile
import com.pusauli.user.utils.log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_shop_list.*
import kotlinx.android.synthetic.main.adapter_shop_list.view.*
import kotlinx.android.synthetic.main.toolbar.*


class StoreListActivity : BaseActivity() {

    var shop_category: String = ""
    var shop_categoryName: String = ""
    private var listStore: ArrayList<StoreListResult> = arrayListOf()
    private val sp by lazy { SharedPref.instance }
    private val uid = sp.userId

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_list)

        try {
            shop_category = intent.getStringExtra("shop_category")
        } catch (e: Exception) {
            this.shop_category =""
        }

       try {
         shop_categoryName = intent.getStringExtra("shop_categoryName")
            } catch (e: Exception) {
                this.shop_category =""
            }


        tv_title.text=shop_categoryName


        setupViews(shop_category)
    }

    fun btnBack(v:View){
        onBackPressed()
    }



    @SuppressLint("CheckResult")
    private fun setupViews(shop_category: String) {
        recyclerViewList.layoutManager = LinearLayoutManager(this)
        showProgress()
        RestClient.webServices().getStoreList(shop_category)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.status!!) {
                    listStore=it.result as ArrayList<StoreListResult>
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

                hideProgress()
            },
                { error ->
                    hideProgress()
                    logs("Error: " + NetworkUtil.isHttpStatusCode(error))
                }
            )




    }


    class StoreListAdapter(var list: ArrayList<StoreListResult>, var listener: ItemClickListener) :
        RecyclerView.Adapter<StoreListAdapter.ViewHolder>() {

        interface ItemClickListener {
            fun onItemClicked(repos: StoreListResult)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_shop_list, parent, false)
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

                if(model.user_avatar !=null && model.user_avatar !="" ) {
                    val userPhoto = PROFILE_AVATAR_BASE_URL + model.user_avatar
                    itemView.clg_logo.loadImageProfile(userPhoto)

                    itemView.clg_logo.setOnClickListener {
                        context.startActivity(
                            Intent(context, ZoomImageActivity::class.java)
                                .putExtra("galleryAvatar", userPhoto)
                        )
                    }
                }
                if(model.shopAvatar !=null){
                    val shopPhoto = STORE_AVATAR_BASE_URL + model.shopAvatar
                    itemView.iv_shopBack.loadImage(shopPhoto)
                }

                itemView.tv_shop_name.text=model.shopName?:""
                itemView.tv_location_city.text=model.shopAddress?:""

            }
        }
    }

    private fun logs(msg: String) {
        log("StoreList", msg)
    }

}





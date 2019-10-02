package com.pusauli.user.ui.vender.fragment.fragement_details

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
import com.pusauli.user.model.GalleryDetails
import com.pusauli.user.network.Const
import com.pusauli.user.ui.vender.VenderActivity
import com.pusauli.user.ui.vender.fragment.ZoomImageActivity
import com.pusauli.user.utils.loadImage
import com.pusauli.user.utils.log
import kotlinx.android.synthetic.main.adapter_gallery.view.*
import kotlinx.android.synthetic.main.fragment_gallery_vender.view.*
import java.util.*


class GalleryViewFragmentVender : Fragment() {



    private lateinit var rev_gallery: RecyclerView
    private lateinit var mActivity: VenderActivity
    var galleryList: ArrayList<GalleryDetails> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = (activity!! as VenderActivity)
        val bundle = this.arguments

        if (bundle != null) {
            galleryList = bundle.getParcelableArrayList("gallery")!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(com.pusauli.user.R.layout.fragment_gallery_vender, container, false)
        uploadData(v, galleryList)
        return v
    }


    @SuppressLint("SetTextI18n")
    private fun uploadData(v: View?, galleryList: ArrayList<GalleryDetails>) {
        rev_gallery=v!!.rev_gallerys
        rev_gallery = v.rev_gallerys
        rev_gallery.layoutManager = GridLayoutManager(context, 2)

        val mAdapter = GalleryListAdapter(galleryList, object : GalleryListAdapter.ItemClickListener {
            override fun onItemClicked(repos: GalleryDetails) {

            }
        })
        rev_gallery.adapter = mAdapter
        mAdapter.notifyDataSetChanged()
    }


    class GalleryListAdapter(var list: ArrayList<GalleryDetails>, var listener: ItemClickListener) :
        RecyclerView.Adapter<GalleryListAdapter.ViewHolder>() {

        interface ItemClickListener {
            fun onItemClicked(repos: GalleryDetails)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_gallery, parent, false)
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

            fun bindItems(model: GalleryDetails) {

                itemView.setOnClickListener {
                    context.startActivity(
                        Intent(context, ZoomImageActivity::class.java)
                            .putExtra("galleryAvatar", Const.STORE_AVATAR_BASE_URL+ model.galleryAvatar)
                    )
                }
                itemView.setOnLongClickListener {
                    listener.onItemClicked(model)
                    true
                }

                itemView.iv_store.loadImage(Const.STORE_AVATAR_BASE_URL+model.galleryAvatar!!)
            }
        }
    }









    private fun logs(msg: String) {
        mActivity.log("VenderDetailsGallery", msg)
    }

    private fun setProgress(flag: Boolean) {
        if (flag)
            mActivity.showProgress()
        else mActivity.hideProgress()
    }

}

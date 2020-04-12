package com.pusauli.user.ui.notification.fragment

import android.annotation.SuppressLint
import androidx.lifecycle.Observer
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pusauli.user.R
import com.pusauli.user.model.NotificationData
import com.pusauli.user.mvvm.NotificationListViewModel
import com.pusauli.user.ui.dashboard.MainActivity
import com.pusauli.user.utils.SharedPref
import com.pusauli.user.utils.equalsIgnoreCase
import com.pusauli.user.utils.showSnackBar
import kotlinx.android.synthetic.main.adapter_notification_list.view.*
import kotlinx.android.synthetic.main.fragment_home.view.*


class PersonalNotificationFragment : Fragment() {

    companion object {
        fun newInstance(): PersonalNotificationFragment {
            return PersonalNotificationFragment()
        }
    }

    private val sp by lazy { SharedPref.instance }
    private lateinit var mActivity: MainActivity
    private val instanceViewModel by lazy { NotificationListViewModel() }
    var notificationList: ArrayList<NotificationData> = arrayListOf()
    private var rec: RecyclerView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = (activity!! as MainActivity)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_personal_notification, container, false)
        initObservers()
        initView(v)
        return v
    }

    private fun initView(v:View?) {
        setProgress(true)
        rec = v!!.recyclerView
        rec!!.layoutManager =
            LinearLayoutManager(mActivity)
        instanceViewModel.getNotificationApiCall()
    }

    private fun initObservers() {
        instanceViewModel.requestStoreDetailsData.observe(this, Observer {
            setProgress(false)
            gotData(it)
        })
        instanceViewModel.errorMess.observe(this, Observer {
            mActivity.showSnackBar(it!!)
            setProgress(false)
        })
    }

    private fun gotData(it: List<NotificationData>?) {
        notificationList = it as ArrayList<NotificationData>

        val mLangAdapter = MenuAdapter(notificationList, object : MenuAdapter.ItemClickListener {
            override fun onItemClicked(repos: NotificationData) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
        rec!!.adapter = mLangAdapter
        mLangAdapter.notifyDataSetChanged()
    }

    private fun setProgress(flag: Boolean) {
        if (flag)
            mActivity.showProgress()
        else mActivity.hideProgress()
    }


    //------------------------Adapter bind----------------------------
    class MenuAdapter(var list: ArrayList<NotificationData>, var listener: ItemClickListener) :
        RecyclerView.Adapter<MenuAdapter.ViewHolder>() {
        interface ItemClickListener {
            fun onItemClicked(repos: NotificationData)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_notification_list, parent, false)
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

            @SuppressLint("SetTextI18n")
            fun bindItems(model: NotificationData) {



                if(model.title!!.equalsIgnoreCase("Pending")){
                    itemView.tv_headline.text = model.title
                    itemView.tv_headline.setTextColor(Color.BLUE)
                }
                if(model.title!!.equalsIgnoreCase("Approved")){
                    itemView.tv_headline.text = model.title
                    itemView.tv_headline.setTextColor(Color.GREEN)
                }
                if(model.title!!.equalsIgnoreCase("Rejected")){
                    itemView.tv_headline.text = model.title
                    itemView.tv_headline.setTextColor(Color.RED)
                }


                itemView.run {
                    tv_venderID.text = "Vender ID : "+model.venderId
                    tv_Category.text = "Category  : "+model.category
                    tv_description.text ="Status      : "+ model.message
                    tv_time.text = model.createAt
                }



            }
        }
    }
}

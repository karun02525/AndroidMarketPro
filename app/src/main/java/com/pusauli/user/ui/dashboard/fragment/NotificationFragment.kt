package com.pusauli.user.ui.dashboard.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.pusauli.user.R
import com.pusauli.user.ui.notification.MyPagerAdapter
import kotlinx.android.synthetic.main.fragment_notification.view.*


class NotificationFragment : BaseFragment() {

    companion object {
        fun newInstance(): NotificationFragment {
            return NotificationFragment()
        }
    }

    private lateinit var myPagerAdapter: MyPagerAdapter
    private lateinit var mContext: Context
    private lateinit var tv_general: TextView
    private lateinit var tv_personal: TextView
    private lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_notification, container, false)
        initView(v)
        return v
    }

    private fun initView(v: View?) {
        tv_general = v!!.tv_general
        tv_personal = v.tv_personal
        viewPager = v.viewPager

        setViewPager()

        onClickTab()

    }

    private fun onClickTab() {
        tv_general.setOnClickListener {
            viewPager.currentItem = 0
        }
        tv_personal.setOnClickListener {
            viewPager.currentItem = 1
        }
    }


    private fun setViewPager() {
        viewPager.offscreenPageLimit = 0
        myPagerAdapter = MyPagerAdapter(childFragmentManager, 2)
        viewPager.adapter = myPagerAdapter
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    tv_general.background = ContextCompat.getDrawable(mContext, R.drawable.black_round)
                    tv_personal.background = ContextCompat.getDrawable(mContext, R.drawable.grey_round_stroke)
                } else {
                    tv_general.background = ContextCompat.getDrawable(mContext, R.drawable.grey_round_stroke)
                    tv_personal.background = ContextCompat.getDrawable(mContext, R.drawable.black_round)

                }

            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }

}

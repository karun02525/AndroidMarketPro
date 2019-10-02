package com.pusauli.user.ui.notification.fragment


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.pusauli.user.R

class GeneralNotificationFragment : Fragment() {

    companion object {
        fun newInstance(): GeneralNotificationFragment {
            return GeneralNotificationFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_general_notification, container, false) }

}

package com.sulv9.talktodo.pages.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sulv9.talktodo.R
import com.sulv9.talktodo.base.BaseFragment
import com.sulv9.talktodo.databinding.FragmentHomeBinding
import com.sulv9.talktodo.util.getColor

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override fun initBinding(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHomeBinding.inflate(layoutInflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding.homeCpbBroadTodayProgress) {
            ringColorArray = intArrayOf(
                getColor(R.color.green_600),
                getColor(R.color.green_400)
            )
            startAnim(80)
        }
    }

}
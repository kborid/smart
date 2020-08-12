package com.kborid.setting.ui.main.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModelProvider
import com.kborid.demo.SimpleLifecycleObserver
import com.kborid.setting.R
import com.kborid.setting.databinding.FragmentHomeBinding
import com.kborid.setting.tool.TestDataHelper
import com.kborid.setting.ui.main.adapter.MainAdapter
import com.thunisoft.common.base.BaseSimpleFragment

class HomeFragment : BaseSimpleFragment() {

    private lateinit var homeViewModel: HomeViewModel

    var adapter: MainAdapter? = null
    var mBinding: FragmentHomeBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentHomeBinding.inflate(inflater)
        return mBinding?.root
    }

    override fun initEventAndData(savedInstanceState: Bundle?) {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        adapter = MainAdapter(context!!, TestDataHelper.getTestStringData())
        mBinding?.listview!!.adapter = adapter
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_home
    }

    val lifecycleObserver: LifecycleObserver = SimpleLifecycleObserver(this.javaClass.simpleName)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        lifecycle.addObserver(lifecycleObserver)
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(lifecycleObserver)
    }
}
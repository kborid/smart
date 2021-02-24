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
import com.kborid.setting.databinding.FragHomeBinding
import com.kborid.setting.tool.TestDataHelper
import com.kborid.setting.ui.main.adapter.MainAdapter
import com.thunisoft.common.base.BaseSimpleFragment

class HomeFragment : BaseSimpleFragment() {

    private var mBinding: FragHomeBinding? = null
    private var lifecycleObserver: LifecycleObserver? = null

    private lateinit var homeViewModel: HomeViewModel
    private var adapter: MainAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragHomeBinding.inflate(inflater)
        return mBinding?.root
    }

    override fun initEventAndData(savedInstanceState: Bundle?) {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        adapter = MainAdapter(requireContext(), TestDataHelper.getTestStringData())
        mBinding?.listview!!.adapter = adapter
        adapter?.notifyDataSetChanged()
    }

    override fun getLayoutResId(): Int {
        return R.layout.frag_home
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        lifecycleObserver = SimpleLifecycleObserver(this.javaClass.simpleName)
        lifecycle.addObserver(lifecycleObserver!!)
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(lifecycleObserver!!)
    }
}

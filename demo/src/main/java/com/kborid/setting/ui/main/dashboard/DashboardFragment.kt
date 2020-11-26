package com.kborid.setting.ui.main.dashboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.recyclerview.widget.LinearLayoutManager
import com.kborid.demo.SimpleLifecycleObserver
import com.kborid.demo.t_rxjava.RxJavaTest
import com.kborid.library.adapter.CommRVAdapter
import com.kborid.library.adapter.RViewHolder
import com.kborid.library.listener.OnItemClickListener
import com.kborid.setting.R
import com.kborid.setting.databinding.FragDashboardBinding
import com.kborid.setting.tool.TestDataHelper
import com.kborid.setting.ui.LifeCycleActivity

class DashboardFragment : Fragment() {

    private lateinit var mBinding: FragDashboardBinding
    private var lifecycleObserver: LifecycleObserver? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragDashboardBinding.inflate(inflater)
        mBinding.recycleView.layoutManager = LinearLayoutManager(context)
//        mBinding.recycleView.setItemViewCacheSize(50)
        val adapter = DashAdapter(context, R.layout.item_dash, TestDataHelper.getTestStringData())
        adapter.setOnItemClickListener(object : OnItemClickListener<String> {
            override fun onItemClick(parent: ViewGroup?, view: View?, entity: String?, position: Int) {
                if (position == 0) {
                    startActivity(Intent(context, LifeCycleActivity::class.java))
                } else if (position == 1) {
                    RxJavaTest.test()
//                    adapter.notifyDataSetChanged()
//                    adapter.dataIO.get(position).plus("jdf")
//                    adapter.notifyItemChanged(1)
                } else if (position == 2) {
                    RxJavaTest.test()
                }
            }

            override fun onItemLongClick(parent: ViewGroup?, view: View?, entity: String?, position: Int): Boolean {
                return false
            }
        })
        adapter.setHasStableIds(true)
        mBinding.recycleView.adapter = adapter
        return mBinding.root
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

    /**
     * 内部adapter实现类
     */
    class DashAdapter constructor(context: Context?, resId: Int, data: List<String>) : CommRVAdapter<String>(context, resId, data) {

        override fun convert(r: RViewHolder?, t: String?) {
            r!!.setText(R.id.fruit_name, t)
        }
    }
}
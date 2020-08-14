package com.kborid.setting.ui.main.dashboard

import android.app.KeyguardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.recyclerview.widget.GridLayoutManager
import com.kborid.demo.SimpleLifecycleObserver
import com.kborid.library.adapter.CommRVAdapter
import com.kborid.library.adapter.RViewHolder
import com.kborid.setting.R
import com.kborid.setting.databinding.FragmentDashboardBinding
import com.kborid.setting.tool.TestDataHelper
import org.slf4j.LoggerFactory

class DashboardFragment : Fragment() {

    private val logger = LoggerFactory.getLogger(DashboardFragment::class.java)

    private lateinit var mBinding: FragmentDashboardBinding
    private var lifecycleObserver: LifecycleObserver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
//                or WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
//                or WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
//                or WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON)
        val keyguardManager = activity?.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager?
        val isLocked = null != keyguardManager && keyguardManager.isKeyguardLocked
        logger.info("锁屏状态：{}", isLocked)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentDashboardBinding.inflate(inflater)
        mBinding.recycleView.layoutManager = GridLayoutManager(context, 5)
        mBinding.recycleView.adapter = DashAdapter(context, R.layout.item_dash, TestDataHelper.getTestStringData())
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
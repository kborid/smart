package com.kborid.setting.ui.main.notifications

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kborid.setting.databinding.FragNotificationsBinding
import com.kborid.setting.ui.ThirdActivity
import com.kborid.setting.ui.TransActivity
import com.kborid.setting.vm.MainViewModel
import com.thunisoft.common.util.ToastUtils
import org.slf4j.LoggerFactory

class NotificationsFragment : Fragment() {

    private val logger = LoggerFactory.getLogger(NotificationsFragment::class.java)

    private lateinit var mBinding: FragNotificationsBinding

    private lateinit var notificationsViewModel: NotificationsViewModel
    private lateinit var mainViewModel: MainViewModel
    private var dynamicValue = MutableLiveData("我是动态变量")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragNotificationsBinding.inflate(inflater)
//        mBinding = DataBindingUtil.inflate(inflater, R.layout.frag_notifications, container, false)
        initEventAndData(savedInstanceState)
        return mBinding.root
    }

    private fun initEventAndData(savedInstanceState: Bundle?) {
        logger.info("initEventAndData({})", savedInstanceState)
        notificationsViewModel = ViewModelProvider(this).get(NotificationsViewModel::class.java)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mBinding.fragment = this
        mBinding.mainViewModel = mainViewModel
        mBinding.notificationViewModel = notificationsViewModel
        mBinding.lifecycleOwner = this
        dynamicValue.observe(viewLifecycleOwner, Observer {
            ToastUtils.showToast("value changed：旧：${dynamicValue.value}, 新：$it")
        })
    }

    fun doClick() {
        dynamicValue.value = "改变了的动态变量"
    }

    fun clickImage() {
        ToastUtils.showToast("图片被点击了")
    }

    fun onBtnClick() {
//        startActivity(Intent(context, ThirdActivity::class.java))
        startActivity(Intent(context, TransActivity::class.java))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding.unbind()
    }
}
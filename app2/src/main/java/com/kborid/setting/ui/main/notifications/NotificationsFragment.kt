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
import com.kborid.kotlin.constant.Constant
import com.kborid.kotlin.pojo.Address
import com.kborid.setting.databinding.FragmentNotificationsBinding
import com.kborid.setting.ui.TransActivity
import com.kborid.setting.vm.MainViewModel
import com.thunisoft.common.util.ToastUtils
import org.slf4j.LoggerFactory

class NotificationsFragment : Fragment() {

    private val logger = LoggerFactory.getLogger(NotificationsFragment::class.java)

    private lateinit var notificationsViewModel: NotificationsViewModel
    private lateinit var mainViewModel: MainViewModel
    private lateinit var constant: Constant
    var dynamicValue = MutableLiveData("我是动态变量")
    lateinit var mBinding: FragmentNotificationsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentNotificationsBinding.inflate(inflater)
//        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_notifications, container, false)
        initEventAndData(savedInstanceState)
        return mBinding.root
    }

    private fun initEventAndData(savedInstanceState: Bundle?) {
        logger.info("initEventAndData({})", savedInstanceState)
        notificationsViewModel = ViewModelProvider(this).get(NotificationsViewModel::class.java)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        constant = ViewModelProvider(this).get(Constant::class.java)
        mBinding.address = Address("四川", "成都")
        mBinding.fragment = this
        mBinding.constant = constant
        mBinding.mainViewModel = mainViewModel
        mBinding.notificationViewModel = notificationsViewModel
        mBinding.lifecycleOwner = this
        dynamicValue.observe(viewLifecycleOwner, Observer {
            mBinding.tvName.text = it
            ToastUtils.showToast("value changed：旧：$dynamicValue.value, 新：$it")
        })
    }

    fun doClick(msg: String) {
        dynamicValue.value = "改变了的动态变量"
    }

    fun clickImage() {
        ToastUtils.showToast("图片被点击了")
    }

    fun onBtnClick() {
        startActivity(Intent(context, TransActivity::class.java))
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.unbind()
    }
}
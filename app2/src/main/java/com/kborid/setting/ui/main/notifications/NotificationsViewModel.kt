package com.kborid.setting.ui.main.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NotificationsViewModel : ViewModel() {

    val text: LiveData<String> = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val p: LiveData<String> = MutableLiveData<String>().apply {
        value = "我是PPPP"
    }
    val imgUrl: LiveData<String> = MutableLiveData<String>().apply {
        value = "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=333294395,1671364992&fm=26&gp=0.jpg"
    }
}
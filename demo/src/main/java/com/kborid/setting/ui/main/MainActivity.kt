package com.kborid.setting.ui.main

import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.NonNull
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kborid.setting.R
import com.kborid.setting.databinding.ActNavigationBinding
import com.thunisoft.common.base.BaseSimpleActivity

class MainActivity : BaseSimpleActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var mBinding: ActNavigationBinding
    private lateinit var navController: NavController
    private lateinit var naviView: BottomNavigationView

    override fun getLayoutResId(): Int {
        return R.layout.act_navigation
    }

    override fun initDataAndEvent(savedInstanceState: Bundle?) {
//        mBinding = DataBindingUtil.setContentView(this, R.layout.act_navigation)

        naviView = findViewById(R.id.nav_view)
        navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
        setupActionBarWithNavController(navController, appBarConfiguration)
        naviView.setupWithNavController(navController)
//        mBinding.navView.setupWithNavController(navController)
//        mBinding.navView.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(@NonNull item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_home -> {
                if (item.isChecked) {
                    return true
                }
                navController.navigate(R.id.navigation_home)
            }
            R.id.navigation_dashboard -> {
                if (item.isChecked) {
                    return true
                }
                navController.navigate(R.id.navigation_dashboard)
            }
            R.id.navigation_notifications -> {
                if (item.isChecked) {
                    return true
                }
                navController.navigate(R.id.navigation_notifications)
            }
        }
        return false
    }
}

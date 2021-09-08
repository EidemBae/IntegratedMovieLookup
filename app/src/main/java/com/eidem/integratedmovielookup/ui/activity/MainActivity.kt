package com.eidem.integratedmovielookup.ui.activity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.eidem.integratedmovielookup.databinding.ActivityMainBinding
import com.eidem.integratedmovielookup.utils.ViewUtils
import android.content.pm.PackageManager

import android.content.pm.PackageInfo
import android.util.Base64
import android.util.Log
import com.eidem.integratedmovielookup.R
import com.eidem.integratedmovielookup.ui.viewmodel.MainViewModel
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initView()
        getHashKey()
    }

    override fun initView() {
        viewModel = MainViewModel(this, binding)
        initDayOfWeekList(binding.dayOfWeekList)
        initBoxOfficeMovieList()
    }

    private fun initDayOfWeekList(recyclerView: RecyclerView) {
        ViewUtils.setHorizontalRecyclerView(this, recyclerView)
        viewModel.initDayOfWeekLiveData()
    }

    private fun initBoxOfficeMovieList() {
        viewModel.requestBoxOfficeList()
    }

    private fun getHashKey() {
        var packageInfo: PackageInfo? = null
        try {
            packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        if (packageInfo == null) Log.e("KeyHash", "KeyHash:null")
        for (signature in packageInfo!!.signatures) {
            try {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            } catch (e: NoSuchAlgorithmException) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=$signature", e)
            }
        }
    }
}
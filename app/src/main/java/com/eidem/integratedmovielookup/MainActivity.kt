package com.eidem.integratedmovielookup

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.eidem.integratedmovielookup.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initView()
    }

    override fun initView() {
        viewModel = MainViewModel(this, binding)
        viewModel.requestBoxOfficeList()
    }
}
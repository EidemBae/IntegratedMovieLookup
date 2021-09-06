package com.eidem.integratedmovielookup

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.eidem.integratedmovielookup.databinding.ActivityMainBinding
import com.eidem.integratedmovielookup.utils.ViewUtils

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
        initRecyclerView(binding.recyclerView)
    }

    private fun initRecyclerView(recyclerView: RecyclerView) {
        ViewUtils.setHorizontalRecyclerView(this, recyclerView)
        viewModel.initDayOfWeekLiveData()
    }
}
package com.eidem.integratedmovielookup

import android.os.Bundle
import androidx.databinding.DataBindingUtil
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
}
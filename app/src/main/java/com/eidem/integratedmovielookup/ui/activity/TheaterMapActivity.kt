package com.eidem.integratedmovielookup.ui.activity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.eidem.integratedmovielookup.R
import com.eidem.integratedmovielookup.databinding.ActivityTheaterMapBinding
import com.eidem.integratedmovielookup.ui.viewmodel.TheaterMapViewModel

class TheaterMapActivity: BaseActivity() {
    private lateinit var binding: ActivityTheaterMapBinding
    private lateinit var viewModel: TheaterMapViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_theater_map)
        initView()
    }

    override fun initView() {
        viewModel = TheaterMapViewModel(this, binding)
    }
}
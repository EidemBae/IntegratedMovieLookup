package com.eidem.integratedmovielookup

import androidx.appcompat.app.AppCompatActivity

open abstract class BaseActivity: AppCompatActivity() {
    abstract fun initView()
}
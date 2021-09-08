package com.eidem.integratedmovielookup.ui.activity

import androidx.appcompat.app.AppCompatActivity

open abstract class BaseActivity: AppCompatActivity() {
    abstract fun initView()
}
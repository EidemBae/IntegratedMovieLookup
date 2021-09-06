package com.eidem.integratedmovielookup

import android.app.Activity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.eidem.integratedmovielookup.databinding.ActivityMainBinding
import com.eidem.integratedmovielookup.adapter.DayOfWeekAdapter

class MainViewModel(activity: Activity, binding: ActivityMainBinding) {
    private var dayOfWeekDataListLiveData = MutableLiveData<ArrayList<DayOfWeekAdapter.ViewHolderData>>()
    private var dayOfWeekDataList = arrayListOf<DayOfWeekAdapter.ViewHolderData>()
    private var dayOfWeekAdapter: DayOfWeekAdapter? = null

    init {
        initDayOfWeekView(activity, binding)
    }

    private fun initDayOfWeekView(activity: Activity, binding: ActivityMainBinding) {
        dayOfWeekDataListLiveData.observe(activity as LifecycleOwner, { liveData ->
            if (dayOfWeekAdapter == null) {
                dayOfWeekAdapter = createDayOfWeekAdapter()
                binding.recyclerView.adapter = dayOfWeekAdapter
                dayOfWeekDataListLiveData.value?.let {
                    for (i in it.indices) {
                        dayOfWeekDataList.add(it[i].clone())
                    }
                }
            }
            else {
                dayOfWeekAdapter!!.updateList(dayOfWeekDataList)
            }
        })
    }

    private fun createDayOfWeekAdapter(): DayOfWeekAdapter {
        return DayOfWeekAdapter(dayOfWeekDataListLiveData).apply {
            this.setOnItemSelectedListener(object: DayOfWeekAdapter.OnItemSelectedListener {
                override fun onSelected(position: Int) {

                }
            })
        }
    }

    fun initDayOfWeekLiveData() {
        val dataList = arrayListOf(
            DayOfWeekAdapter.ViewHolderData(5, "금일", true),
            DayOfWeekAdapter.ViewHolderData(6, "내일"),
            DayOfWeekAdapter.ViewHolderData(7, "화"),
            DayOfWeekAdapter.ViewHolderData(8, "수"),
            DayOfWeekAdapter.ViewHolderData(9, "목"),
            DayOfWeekAdapter.ViewHolderData(10, "금"),
            DayOfWeekAdapter.ViewHolderData(11, "토")
        )

        dayOfWeekDataListLiveData.postValue(dataList)
    }
}
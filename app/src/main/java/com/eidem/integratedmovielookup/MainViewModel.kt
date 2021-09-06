package com.eidem.integratedmovielookup

import android.app.Activity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.eidem.integratedmovielookup.adapter.BoxOfficeAdapter
import com.eidem.integratedmovielookup.adapter.DayOfWeekAdapter
import com.eidem.integratedmovielookup.api.ApiUtil
import com.eidem.integratedmovielookup.databinding.ActivityMainBinding
import com.eidem.integratedmovielookup.model.BoxOfficeModel
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.eidem.integratedmovielookup.view.CarouselLayoutManager


class MainViewModel(activity: Activity, val binding: ActivityMainBinding) {
    private var boxOfficeModelList: List<BoxOfficeModel>? = null
    private var dayOfWeekDataListLiveData =
        MutableLiveData<ArrayList<DayOfWeekAdapter.ViewHolderData>>()
    private var dayOfWeekDataList = arrayListOf<DayOfWeekAdapter.ViewHolderData>()
    private var dayOfWeekAdapter: DayOfWeekAdapter? = null

    private var boxOfficeAdapter: BoxOfficeAdapter? = null

    init {
        initAd()
        initDayOfWeekView(activity, binding)

    }

    private fun initAd() {
        binding.adView.loadAd(AdRequest.Builder().build())
        binding.adView.adListener = object: AdListener() {
            override fun onAdLoaded() {
                println("====> Ad Loaded")
            }

            override fun onAdFailedToLoad(p0: Int) {
                println("====> Ad load failed")
            }
        }
    }

    private fun initDayOfWeekView(activity: Activity, binding: ActivityMainBinding) {
        dayOfWeekDataListLiveData.observe(activity as LifecycleOwner, { liveData ->
            if (dayOfWeekAdapter == null) {
                dayOfWeekAdapter = createDayOfWeekAdapter()
                binding.dayOfWeekList.adapter = dayOfWeekAdapter
                dayOfWeekDataListLiveData.value?.let {
                    for (i in it.indices) {
                        dayOfWeekDataList.add(it[i].clone())
                    }
                }
            } else {
                dayOfWeekAdapter!!.updateList(dayOfWeekDataList)
            }
        })
    }

    private fun createDayOfWeekAdapter(): DayOfWeekAdapter {
        return DayOfWeekAdapter(dayOfWeekDataListLiveData).apply {
            this.setOnItemSelectedListener(object : DayOfWeekAdapter.OnItemSelectedListener {
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

    private fun getBoxOfficePosterUrl(url: String): String {
        return url.replace("/thumb/","/")
            .replace("/thn_","/")
    }

    fun requestBoxOfficeList() {
        ApiUtil.getKobisApiService()
            .requestBoxOfficeList()
            .enqueue(object: Callback<List<BoxOfficeModel>> {
                override fun onResponse(
                    call: Call<List<BoxOfficeModel>>,
                    response: Response<List<BoxOfficeModel>>
                ) {
                    boxOfficeModelList = response.body()
                    boxOfficeModelList?.let { setBoxOffice(it) }
                }

                override fun onFailure(call: Call<List<BoxOfficeModel>>, t: Throwable) {
                }
            })
    }

    private fun setBoxOffice(itemList: List<BoxOfficeModel>) {
        boxOfficeAdapter = BoxOfficeAdapter(itemList)
        binding.moviePosterList.adapter = boxOfficeAdapter
        binding.moviePosterList.set3DItem(true)
        binding.moviePosterList.setAlpha(true)

        binding.moviePosterList.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val position: Int = getCurrentItem()
                    setMovieInfo(position)
                }
            }
        })

        setMovieInfo(0)
    }

    private fun setMovieInfo(position: Int) {
        val item = boxOfficeAdapter?.getItem(position)
        item?.let {
            binding.tvName.text = it.movieNm
            binding.tvNameEn.text = it.movieNmEn

            binding.tvOpenDate.text = it.openDt
            binding.movieProductState.text = it.moviePrdtStat
            binding.watchGradeNumber.text = it.watchGradeNm

            binding.showTm.text = it.showTm + "분" + it.showTs + "초"
            binding.repNationCode.text = it.repNationCd

            binding.director.text = it.director
            binding.genre.text = it.genre
            binding.dtNm.text = it.dtNm
        }
    }

    private fun getCurrentItem(): Int {
        return (binding.moviePosterList.layoutManager as CarouselLayoutManager)
            .centerPosition()
    }
}
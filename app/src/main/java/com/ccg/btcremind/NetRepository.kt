package com.ccg.btcremind

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 20-3-27 下午3:53
 */
class NetRepository {
    private val netService = Retrofit.Builder()
        .baseUrl("https://sochain.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build().create(GitHubService::class.java)

    /**
     * https://sochain.com/api/v2/get_price/ETH/USD
     * 获取房间信息
     * @return Observable<RoomBean>
     */
    fun getListData(): Observable<RoomBean> {
        return netService.getListData()
            .subscribeOn(Schedulers.io())
    }

}
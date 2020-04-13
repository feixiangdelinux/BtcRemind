package com.ccg.btcremind

import android.util.Log
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
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
        .client(getOkHttpClient())//使用自己创建的OkHttp
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
    private fun getOkHttpClient(): OkHttpClient? {
        //日志显示级别
        val level = HttpLoggingInterceptor.Level.BODY
        //新建log拦截器
        val loggingInterceptor =
            HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
                Log.d(
                    "zcb",
                    "OkHttp====Message:$message"
                )
            })
        loggingInterceptor.level = level

        //定制OkHttp
        val httpClientBuilder = OkHttpClient.Builder()
        //OkHttp进行添加拦截器loggingInterceptor
        httpClientBuilder.addInterceptor(loggingInterceptor)
        return httpClientBuilder.build()
    }
}
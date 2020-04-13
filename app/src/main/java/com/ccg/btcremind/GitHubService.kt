package com.ccg.btcremind

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET


/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 20-3-21 上午12:20
 */
interface GitHubService {
    @GET("/api/v2/get_price//ETH/USD")
    fun getListData(): Observable<RoomBean>

}
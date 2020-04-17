package com.ccg.btcremind

import java.util.*
import kotlin.collections.ArrayList

/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 20-4-17 下午3:26
 */
object PriceUtil {
    /**
     * 历史价格
     * 最多存100个历史价格
     */
    private var allPrice: MutableList<Double> = ArrayList()

    /**
     * 涨到多少提醒
     */
    var one: Double = 0.0

    /**
     * 跌到多少提醒
     */
    var two: Double = 0.0

    /**
     * 比15分钟前高多少提醒
     */
    var three: Double = 0.0

    /**
     * 比15分钟前低多少提醒
     */
    var four: Double = 0.0

    /**
     *和以前的100次价格中最低的价格进行比较如果比最低的价格还低就提醒
     */
    var five: Boolean = false

    /**
     *和以前的100次价格中最高的价格进行比较如果比最高的价格还高就提醒
     */
    var six: Boolean = false
    fun saveCurrentPrice(
        currentPrice: String,
        service: TestOneService
    ) {
        val doublePrice = currentPrice.toDouble()
        if (allPrice.isEmpty()) {
            allPrice.add(doublePrice)
        } else {
            oneRemind(service, doublePrice, one)
            twoRemind(service, doublePrice, two)
            threeRemind(service, allPrice[allPrice.size - 1], doublePrice, three)
            fourRemind(service, allPrice[allPrice.size - 1], doublePrice, four)
            fiveRemind(service, allPrice, doublePrice, five)
            sixRemind(service, allPrice, doublePrice, six)
        }
    }

    /**
     * 涨到多少提醒
     * @param currentPrice Double
     * @param highPrice Double
     */
    private fun oneRemind(service: TestOneService, currentPrice: Double, highPrice: Double) {
        if (highPrice == 0.0) {
            return
        }
        if (currentPrice >= highPrice) {
            //提醒用户，当前价格已经涨到***
            service.notification("涨到${highPrice}提醒", "当前价格${currentPrice}  历史最高${allPrice.max()}  历史最低${allPrice.min()}  平均价格${allPrice.average()}")
        }
    }

    /**
     * 跌到多少提醒
     * @param currentPrice Double
     * @param lowPrice Double
     */
    private fun twoRemind(service: TestOneService, currentPrice: Double, lowPrice: Double) {
        if (lowPrice == 0.0) {
            return
        }
        if (currentPrice <= lowPrice) {
            //提醒用户，当前价格已经跌到***
            service.notification("跌到${lowPrice}提醒", "当前价格${currentPrice}  历史最高${allPrice.max()}  历史最低${allPrice.min()}  平均价格${allPrice.average()}")
        }
    }


    /**
     * 比15分钟前高多少提醒
     * @param service TestOneService
     * @param lastPrice Double
     * @param currentPrice Double
     * @param dValue Double
     */
    private fun threeRemind(
        service: TestOneService,
        lastPrice: Double,
        currentPrice: Double,
        dValue: Double
    ) {
        if (dValue == 0.0) {
            return
        }
        if ((currentPrice - lastPrice) > dValue) {
            //提醒用户，当前价格比15分钟前高dValue提醒
            service.notification("比15分钟前高${dValue}提醒", "当前价格${currentPrice}  历史最高${allPrice.max()}  历史最低${allPrice.min()}  平均价格${allPrice.average()}")
        }
    }

    /**
     * 比15分钟前低多少提醒
     * @param service TestOneService
     * @param lastPrice Double
     * @param currentPrice Double
     * @param dValue Double
     */
    private fun fourRemind(
        service: TestOneService,
        lastPrice: Double,
        currentPrice: Double,
        dValue: Double
    ) {
        if (dValue == 0.0) {
            return
        }
        if ((lastPrice - currentPrice) > dValue) {
            //提醒用户，当前价格比15分钟前低dValue提醒
            service.notification("比15分钟前低${dValue}提醒", "当前价格${currentPrice}  历史最高${allPrice.max()}  历史最低${allPrice.min()}  平均价格${allPrice.average()}")
        }
    }

    /**
     * 和以前的100次价格中最低的价格进行比较如果比最低的价格还低就提醒
     * @param service TestOneService
     * @param allPrice MutableList<Double>
     * @param currentPrice Double
     * @param isCheck Boolean
     */
    private fun fiveRemind(
        service: TestOneService,
        allPrice: MutableList<Double>,
        currentPrice: Double,
        isCheck: Boolean
    ) {
        if (isCheck) {
            val minPrice = Collections.min(allPrice)
            if (currentPrice <= minPrice) {
                //提醒用户，当前价格和以前的100次价格中最低的价格进行比较如果比最低的价格还低就提醒
                service.notification("当前价格和前100次比已经达到最低的价格", "当前价格${currentPrice}  历史最高${allPrice.max()}  历史最低${allPrice.min()}  平均价格${allPrice.average()}")
            }
        }
    }
    /**
     * 和以前的100次价格中最高的价格进行比较如果比最高的价格还高就提醒
     * @param service TestOneService
     * @param allPrice MutableList<Double>
     * @param currentPrice Double
     * @param isCheck Boolean
     */
    private fun sixRemind(
        service: TestOneService,
        allPrice: MutableList<Double>,
        currentPrice: Double,
        isCheck: Boolean
    ) {
        if (isCheck) {
            val maxPrice = Collections.max(allPrice)
            if (currentPrice >= maxPrice) {
                //提醒用户，当前价格和以前的100次价格中最高的价格进行比较如果比最高的价格还高就提醒
                service.notification("当前价格和前100次比已经达到最高的价格", "当前价格${currentPrice}  历史最高${allPrice.max()}  历史最低${allPrice.min()}  平均价格${allPrice.average()}")
            }
        }
    }

}
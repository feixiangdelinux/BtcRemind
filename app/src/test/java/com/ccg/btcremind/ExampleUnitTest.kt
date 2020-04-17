package com.ccg.btcremind

import org.junit.Test

import org.junit.Assert.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val allPrice: MutableList<Double> = ArrayList()
        print("250:  最大值 "+     allPrice.max())
        print("250:  最小之 "+     allPrice.min())
        print("250:  平均之 "+     allPrice.average())
        assertEquals(4, 2 + 2)
    }
}

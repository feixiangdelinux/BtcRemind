package com.ccg.btcremind

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ixintui.push.Receiver
import com.ixintui.pushsdk.PushSdkApi
import com.jakewharton.rxbinding3.widget.checkedChanges
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


class MainActivity : AppCompatActivity() {
    var context = this
    private val compositeDisposable by lazy { CompositeDisposable() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val ixintuiFilter = IntentFilter()
        ixintuiFilter.addAction("com.ixintui.action.BROADCAST")
        ixintuiFilter.addAction("android.intent.action.BOOT_COMPLETED")
        ixintuiFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        val ixintuiFilterRemove = IntentFilter()
        ixintuiFilterRemove.addAction("android.intent.action.PACKAGE_REMOVED")
        ixintuiFilterRemove.addDataScheme("package")
        val ixintuiReceiver = Receiver()
        registerReceiver(ixintuiReceiver, ixintuiFilter)
        registerReceiver(ixintuiReceiver, ixintuiFilterRemove)
        PushSdkApi.register(this, 1881902464, "爱心推" , "1.0")
//        val extra = intent.getStringExtra(SdkConstants.ADDITION)
        //启动后台服务,实时联网获取价格
        startService(Intent(context, TestOneService::class.java))
        initData()

//        CustomerDialog.show(context,"你有一条新订单")
    }

    private fun initData() {
        //涨到多少提醒
        addDisposable(findViewById<EditText>(R.id.main_et_one).textChanges().filter {
            return@filter !android.text.TextUtils.isEmpty(it.toString())
        }.flatMap {
            return@flatMap Observable.just(it.toString())
        }.subscribe({
            val price = it.toDouble()
            PriceUtil.one = price
        }, {
            Toast.makeText(context, "输入的价格格式不正确，比如 124.58", Toast.LENGTH_LONG).show()
        }))
        //跌到多少提醒
        addDisposable(findViewById<EditText>(R.id.main_et_two).textChanges().filter {
            return@filter !android.text.TextUtils.isEmpty(it.toString())
        }.flatMap {
            return@flatMap Observable.just(it.toString())
        }.subscribe({
            val price = it.toDouble()
            PriceUtil.two = price
        }, {
            Toast.makeText(context, "输入的价格格式不正确，比如 124.58", Toast.LENGTH_LONG).show()
        }))
        //比15分钟前高多少提醒
        addDisposable(findViewById<EditText>(R.id.main_et_three).textChanges().filter {
            return@filter !android.text.TextUtils.isEmpty(it.toString())
        }.flatMap {
            return@flatMap Observable.just(it.toString())
        }.subscribe({
            val price = it.toDouble()
            PriceUtil.three = price
        }, {
            Toast.makeText(context, "输入的价格格式不正确，比如 124.58", Toast.LENGTH_LONG).show()
        }))
        //比15分钟前低多少提醒
        addDisposable(findViewById<EditText>(R.id.main_et_four).textChanges().filter {
            return@filter !android.text.TextUtils.isEmpty(it.toString())
        }.flatMap {
            return@flatMap Observable.just(it.toString())
        }.subscribe({
            val price = it.toDouble()
            PriceUtil.four = price
        }, {
            Toast.makeText(context, "输入的价格格式不正确，比如 124.58", Toast.LENGTH_LONG).show()
        }))
        //和以前的100次价格中最低的价格进行比较如果比最低的价格还低就提醒
        addDisposable(findViewById<CheckBox>(R.id.main_cb_five).checkedChanges().subscribe({
            PriceUtil.five = it
        }, {
            Toast.makeText(context, "输入的价格格式不正确，比如 124.58", Toast.LENGTH_LONG).show()
        }))
        //和以前的100次价格中最高的价格进行比较如果比最高的价格还高就提醒
        addDisposable(findViewById<CheckBox>(R.id.main_cb_six).checkedChanges().subscribe({
            PriceUtil.six = it
        }, {
            Toast.makeText(context, "输入的价格格式不正确，比如 124.58", Toast.LENGTH_LONG).show()
        }))
    }

    /**
     * 添加订阅
     * @param disposable Disposable
     */
    private fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}

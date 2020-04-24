package com.ccg.btcremind

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*


/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 20-4-11 下午2:57
 */
class TestOneService : Service() {
    private val repository by lazy { NetRepository() }
    private val compositeDisposable by lazy { CompositeDisposable() }
    private val groupId = "groupId"
    private val groupName: CharSequence = "Group1"

    private val groupId2 = "groupId2"
    private val groupName2: CharSequence = "Group2"
    private val chatChannelId2 = "chatChannelId2"
    private val adChannelId2 = "adChannelId2"

    private val chatChannelId = "chatChannelId"
    private val chatChannelName = "聊天通知"
    private val chatChannelDesc = "这是一个聊天通知，建议您置于开启状态，这样才不会漏掉女朋友的消息哦"
    private val chatChannelImportance = NotificationManager.IMPORTANCE_MAX

    private val adChannelName = "广告通知"
    private val adChannelDesc = "这是一个广告通知，可以关闭的，但是如果您希望我们做出更好的软件服务于你，请打开广告支持一下吧"
    private val adChannelImportance = NotificationManager.IMPORTANCE_LOW

    private lateinit var mNotificationManager: NotificationManager
    private lateinit var sss: Notification

    /**
     * 首次创建服务时，系统将调用此方法。如果服务已在运行，则不会调用此方法，该方法只调用一次。
     */
    override fun onCreate() {
        super.onCreate()
        mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createGroup(mNotificationManager)
    }

    /**
     * 当另一个组件通过调用startService()请求启动服务时，系统将调用此方法。
     * @param intent Intent
     * @param flags Int
     * @param startId Int
     * @return Int
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                addDisposable(
                    repository.getListData()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            Log.e("250:", "aaaaaaaaaaaaaaaa" + it.data.prices[0].price)
                            it.data.prices[0].price
                            PriceUtil.saveCurrentPrice(it.data.prices[0].price, this@TestOneService)
                            startForeground(110, sss)
                        }, {
                            Log.e("250:", "" + it.message)
                        })
                )
            }
        }, 0, 900000)
        return super.onStartCommand(intent, flags, startId)
    }

    /**
     * 当服务不再使用且将被销毁时，系统将调用此方法。
     */
    override fun onDestroy() {
        super.onDestroy()
        Log.e("250:  ", "onDestroy")
        compositeDisposable.clear()
    }

    /**
     * 当另一个组件通过调用bindService()与服务绑定时，系统将调用此方法。
     * @param intent Intent
     * @return IBinder?
     */
    override fun onBind(intent: Intent?): IBinder? {
        Log.e("250:  ", "onBind")
        return null
    }

    /**
     * 当另一个组件通过调用unbindService()与服务解绑时，系统将调用此方法。
     * @param intent Intent
     * @return Boolean
     */
    override fun onUnbind(intent: Intent?): Boolean {
        Log.e("250:  ", "onUnbind")
        return super.onUnbind(intent)
    }

    /**
     * 当旧的组件与服务解绑后，另一个新的组件与服务绑定，onUnbind()返回true时，系统将调用此方法。
     * @param intent Intent
     */
    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
        Log.e("250:  ", "onRebind")
    }

    /**
     * 添加订阅
     * @param disposable Disposable
     */
    private fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    fun notification(
        title: String,
        pic: String
    ) {
        createNotificationChannel(
            chatChannelId,
            chatChannelName,
            chatChannelImportance,
            chatChannelDesc,
            groupId2, mNotificationManager
        )
        var builder: Notification.Builder? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = Notification.Builder(this, chatChannelId)
            builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(pic)
                .setBadgeIconType(Notification.BADGE_ICON_SMALL)
                .setNumber(1)
                .setAutoCancel(true)
            val resultIntent = Intent(this, MainActivity::class.java)
            val stackBuilder =
                TaskStackBuilder.create(this)
            stackBuilder.addParentStack(MainActivity::class.java)
            stackBuilder.addNextIntent(resultIntent)
            val resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_ONE_SHOT)
            builder.setContentIntent(resultPendingIntent)
            val sss = builder.build()
            mNotificationManager.notify(
                System.currentTimeMillis().toInt(),
                sss
            )
        }
    }

    private fun createNotificationChannel(
        id: String?,
        name: String?,
        importance: Int,
        desc: String?,
        groupId: String?,
        mNotificationManager: NotificationManager
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (mNotificationManager.getNotificationChannel(id) != null) {
                return
            }
            val notificationChannel = NotificationChannel(id, name, importance)
            notificationChannel.enableLights(true)
            notificationChannel.enableVibration(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            notificationChannel.setShowBadge(true)
            notificationChannel.setBypassDnd(true)
            notificationChannel.vibrationPattern = longArrayOf(100, 200, 300, 400)
            notificationChannel.description = desc
            notificationChannel.group = groupId
            mNotificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun createGroup(mNotificationManager: NotificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mNotificationManager.createNotificationChannelGroup(
                NotificationChannelGroup(
                    groupId,
                    groupName
                )
            )
            mNotificationManager.createNotificationChannelGroup(
                NotificationChannelGroup(
                    groupId2,
                    groupName2
                )
            )
            createNotificationChannel(
                chatChannelId2,
                chatChannelName,
                chatChannelImportance,
                chatChannelDesc,
                groupId, mNotificationManager
            )
            createNotificationChannel(
                adChannelId2,
                adChannelName,
                adChannelImportance,
                adChannelDesc,
                groupId, mNotificationManager
            )
        }
    }
}
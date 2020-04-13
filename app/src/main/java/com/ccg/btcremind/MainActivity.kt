package com.ccg.btcremind

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {


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
    var context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startService(Intent(context, TestOneService::class.java))
//        val mNotificationManager: NotificationManager =
//            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        createGroup(mNotificationManager)
//        notification(mNotificationManager)

    }

    private fun notification(mNotificationManager: NotificationManager) {
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
                .setContentTitle("我是标题")
                .setContentText("内容：Today released Android 8.0 version of its name is Oreo")
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
            mNotificationManager.notify(
                System.currentTimeMillis().toInt(),
                builder.build()
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

    override fun onStart() {
        super.onStart()
        Log.e("250:1", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.e("250:1", "onResume")
    }

    override fun onRestart() {
        super.onRestart()
        Log.e("250:1", "onRestart")
    }

    override fun onPause() {
        super.onPause()
        Log.e("250:1", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.e("250:1", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("250:1", "onDestroy")
    }
}

package com.poran.workmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
private const val CHANNEL_ID="worker_channel"
private const val CHANNEL_NAME="WorkManager"
private  const val NOTIFICATION_ID=1

class MyWorker(
   private val context: Context,workerParams:WorkerParameters
) :Worker(context,workerParams) {


    override fun doWork(): Result {
        val title=inputData.getString(title_key)
        val message=inputData.getString(message_key)

        sendNotification(title,message)
        return Result.success()
    }

    private fun  sendNotification(title: String?,message:String?) {

        val manager:NotificationManager=context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            val importance=NotificationManager.IMPORTANCE_DEFAULT
            val notifyChannel=NotificationChannel(CHANNEL_ID, CHANNEL_NAME,importance)
            manager.createNotificationChannel(notifyChannel)
        }
       val notificationBuilder=NotificationCompat.Builder(context, CHANNEL_ID)
           .setSmallIcon(R.drawable.notification_ic)
           .setContentTitle(title)
           .setContentText(message)
           .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        manager.notify(NOTIFICATION_ID,notificationBuilder.build())



    }
}
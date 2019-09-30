package com.poran.workmanager

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.*

const val  title_key:String="title"
const val message_key="message"
const val UNIQUE_WORK="charging_status"

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val constrains:Constraints=Constraints.Builder().
                setRequiresCharging(true)
            .build()
        val data= workDataOf(title_key to "status",message_key to "Battery Charging")


        val requestWorkManager= OneTimeWorkRequestBuilder<MyWorker>()
            .setInputData(data)
            .setConstraints(constrains)
            .build()

        WorkManager.getInstance(this)
            .enqueueUniqueWork(
                UNIQUE_WORK,
                ExistingWorkPolicy.KEEP,
                requestWorkManager
            )

        val info=findViewById<TextView>(R.id.state)

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(requestWorkManager.id).observe(this,
            Observer {
                if (it != null) {
                    info.append(it.state.name+"\n")
                }
            })




    }
}

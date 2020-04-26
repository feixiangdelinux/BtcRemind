package com.ccg.btcremind

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.blankj.utilcode.util.TimeUtils
import timber.log.Timber

/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 20-4-26 上午11:00
 */
class UploadWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {
    override fun doWork(): Result {

        uploadImages()

        // Indicate whether the task finished successfully with the Result
        return Result.success()
    }

    private fun uploadImages() {

        Log.e("250:","aaaaaaaaaaaaa"+TimeUtils.getNowString())
    }

}

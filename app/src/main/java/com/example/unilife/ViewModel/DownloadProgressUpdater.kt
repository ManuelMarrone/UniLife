package com.example.unilife.ViewModel


import android.annotation.SuppressLint
import android.app.DownloadManager
import kotlinx.coroutines.delay



const val STATUS_SUCCESS = 100L
const val STATUS_FAILED = -100L
class DownloadProgressUpdater(
    private val downloadManager: DownloadManager,
    private val downloadId: Long,
    private val listener: DownloadProgessListener
) {

    interface  DownloadProgessListener{

        fun updateProgress(progress: Long)
    }

    private val query = DownloadManager.Query()
    private var totalBytes = 0

    init {
        query.setFilterById(downloadId)
    }

    @SuppressLint("Range")
    suspend fun run(){
        while(downloadId > 0){
            delay(250)
            downloadManager.query(query).use {cursor ->
                if(cursor.moveToFirst())
                    if(totalBytes <= 0){
                        totalBytes = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))
                    }

                when(cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))){
                    DownloadManager.STATUS_SUCCESSFUL -> {
                        listener.updateProgress(STATUS_SUCCESS)
                        return

                    }
                    DownloadManager.STATUS_FAILED -> {
                        listener.updateProgress(STATUS_FAILED)
                        return
                    }
                    else ->{
                        val bytesDownloadSoFar = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
                        val progress = bytesDownloadSoFar * 100L / totalBytes
                        listener.updateProgress(progress = progress)

                    }
                }
            }
        }
    }

}





/**
import android.app.DownloadManager
import android.content.ContentResolver
import android.database.ContentObserver
import android.net.Uri
import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class DownloadProgressUpdater(
private val contentResolver: ContentResolver,
private val downloadId: Long
) {

companion object {
const val STATUS_SUCCESS = 1
const val STATUS_FAILED = 0
}

private val _progressLiveData = MutableLiveData<Long>()
val progressLiveData: LiveData<Long> = _progressLiveData

private val contentObserver = object : ContentObserver(Handler()) {
override fun onChange(selfChange: Boolean) {
queryDownloadStatus()
}
}

fun startProgressUpdates() {
val downloadUri = Uri.parse("content://downloads/my_downloads")
contentResolver.registerContentObserver(downloadUri, true, contentObserver)
}



private fun queryDownloadStatus() {
val queryUri = Uri.parse("content://downloads/my_downloads")
val cursor = contentResolver.query(queryUri, null, null, null, null)
cursor?.use {
val statusIndex = it.getColumnIndex(DownloadManager.COLUMN_STATUS)
val totalSizeIndex = it.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES)
val downloadedSizeIndex = it.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)

if (statusIndex != -1 && totalSizeIndex != -1 && downloadedSizeIndex != -1) {
if (it.moveToFirst()) {
val totalBytes = it.getLong(totalSizeIndex)
val downloadedBytes = it.getLong(downloadedSizeIndex)
val progress = if (totalBytes > 0) {
(downloadedBytes * 100L / totalBytes)
} else {
-1L
}
_progressLiveData.postValue(progress)
}
} else {
// Gestisci il caso in cui le colonne richieste non esistono nel risultato della query
}
}
}


}*/
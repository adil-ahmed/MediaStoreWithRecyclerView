package com.aac.recyclerviewwithmediastore

import android.content.Context
import android.provider.MediaStore

interface IMediaStore{
    fun getAllMedia(): List<VideoItem>
}

class MediaStoreHelper(private val context:Context): IMediaStore
{
    override fun getAllMedia(): List<VideoItem> {
        val videoList = mutableListOf<VideoItem>()
        val projection = arrayOf(
            MediaStore.Video.Media.TITLE,
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media.SIZE,
            MediaStore.Video.Media.DURATION
        )

        val selection = null
        val selectionArgs = null
        val sortOrder = null

        context.contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )?.use { cursor ->
            val titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE)
            val uriColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
            val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)
            val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)

            while (cursor.moveToNext()) {
                val title = cursor.getString(titleColumn)
                val uri = cursor.getString(uriColumn)
                val size = cursor.getLong(sizeColumn)
                val duration = cursor.getLong(durationColumn)

                val videoItem = VideoItem(title, uri, size, duration)
                videoList.add(videoItem)

            }

        }
        return videoList
    }
}

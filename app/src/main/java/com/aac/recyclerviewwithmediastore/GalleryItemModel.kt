package com.aac.recyclerviewwithmediastore

import androidx.annotation.DrawableRes

class GalleryItemModel (
    @DrawableRes var image: Int,
    var picName: String,
    var picSize: Long,
    var duration: Long,
    var id: Int? = null
)
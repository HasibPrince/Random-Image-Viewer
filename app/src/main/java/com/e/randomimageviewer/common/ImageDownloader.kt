package com.e.randomimageviewer.common

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.bumptech.glide.signature.ObjectKey

object ImageDownloader {
     fun downloadImageAsBitmap(context: Context, callback: (Bitmap?) -> Unit) {
        Glide.with(context)
            .asBitmap()
            .load(Config.RANDOM_IMAGE_URL)
            .signature(ObjectKey(System.currentTimeMillis().toString()))
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    Log.d(
                        this.javaClass.simpleName,
                        "===> bitmap info: ${resource.width}x${resource.height}"
                    )
                    callback(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    Log.e("HomeFragment", "Image downloading error")
                    callback(null)
                }
            })
    }
}
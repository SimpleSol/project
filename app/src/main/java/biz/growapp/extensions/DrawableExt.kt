/*
 * BusinessApps
 *
 * Created by Dmitry Ikryanov on 10.06.16
 * Copyright © 2016 Dmitry Ikryanov. All rights reserved.
 */
@file:JvmName("DrawableUtils")

package biz.growapp.extensions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.graphics.drawable.VectorDrawableCompat
import android.support.v4.content.ContextCompat

fun Int.loadVector(context: Context): Drawable = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
    ContextCompat.getDrawable(context, this)
else
    VectorDrawableCompat.create(context.resources, this, context.theme) as Drawable

fun Int.bitmapFromVectorRes(context: Context): Bitmap {
    val drawable = this.loadVector(context)
    val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return bitmap
}

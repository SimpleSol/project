/*
 * BusinessApps
 *
 * Created by Dmitry Ikryanov on 10.06.16
 * Copyright © 2016 Dmitry Ikryanov. All rights reserved.
 */
@file:JvmName("ImageViewUtils")

package biz.growapp.extensions

import android.net.Uri
import android.text.TextUtils
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.request.ImageRequestBuilder

fun SimpleDraweeView.setResizedImage(url: String?, width: Int, height: Int) {
    if (TextUtils.isEmpty(url)) {
        this.setImageURI(null as String?)
    } else {
        controller = Fresco.newDraweeControllerBuilder().apply {
            oldController = controller
            imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url)).apply {
                resizeOptions = ResizeOptions(width, height)
            }.build()
        }.build()
    }
}
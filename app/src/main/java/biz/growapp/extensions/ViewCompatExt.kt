/*
 * BusinessApps
 *
 * Created by Dmitry Ikryanov on 10.06.16
 * Copyright © 2016 Dmitry Ikryanov. All rights reserved.
 */
@file:JvmName("ViewCompatUtils")

package biz.growapp.extensions

import android.support.v4.view.ViewCompat
import android.view.View
import android.view.ViewTreeObserver

var View.elevationCompat: Float
    get() = ViewCompat.getElevation(this)
    set(value) = ViewCompat.setElevation(this, value)

inline fun View.onGlobalLayout(crossinline action: () -> Unit): ViewTreeObserver.OnGlobalLayoutListener {
    val listener = object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            viewTreeObserver.removeOnGlobalLayoutListener(this)
            action()
        }
    }
    viewTreeObserver.addOnGlobalLayoutListener(listener)
    return listener
}
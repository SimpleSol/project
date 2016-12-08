@file:JvmName("ViewCompatUtils")

package biz.growapp.extensions

import android.support.annotation.LayoutRes
import android.support.v4.view.ViewCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.isVisible() = visibility == View.VISIBLE

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false)
        = LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)

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
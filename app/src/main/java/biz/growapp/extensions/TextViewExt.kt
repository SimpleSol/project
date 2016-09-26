/*
 * BusinessApps
 *
 * Created by Dmitry Ikryanov on 10.06.16
 * Copyright © 2016 Dmitry Ikryanov. All rights reserved.
 */
@file:JvmName("TextViewUtils")

package biz.growapp.extensions

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.TextView

private const val LEFT = 0
private const val TOP = 1
private const val RIGHT = 2
private const val BOTTOM = 3

var TextView.drawableLeft: Drawable?
    get() = compoundDrawables[LEFT]
    set(value) = compoundDrawables.let {
        setCompoundDrawablesWithIntrinsicBounds(value, it[TOP], it[RIGHT], it[BOTTOM])
    }

var TextView.drawableRight: Drawable?
    get() = compoundDrawables[RIGHT]
    set(value) = compoundDrawables.let {
        setCompoundDrawablesWithIntrinsicBounds(it[LEFT], it[TOP], value, it[BOTTOM])
    }

var TextView.drawableTop: Drawable?
    get() = compoundDrawables[TOP]
    set(value) = compoundDrawables.let {
        setCompoundDrawablesWithIntrinsicBounds(it[LEFT], value, it[RIGHT], it[BOTTOM])
    }

fun TextView.hideIfTextNull(text: CharSequence?) {
    this.text = text
    visibility = if (text == null) View.GONE else View.VISIBLE
}
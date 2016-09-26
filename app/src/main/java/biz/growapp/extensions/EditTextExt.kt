/*
 * BusinessApps
 *
 * Created by Dmitry Ikryanov on 17.06.16
 * Copyright © 2016 Dmitry Ikryanov. All rights reserved.
 */
@file:JvmName("EditViewUtils")

package biz.growapp.extensions

import android.widget.EditText

var EditText.textWithSelection: CharSequence?
    get() = text
    set(value) {
        setText(value)
        value?.let { setSelection(it.length) }
    }
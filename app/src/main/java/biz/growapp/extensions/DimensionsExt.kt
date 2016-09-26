/*
 * BusinessApps
 *
 * Created by Dmitry Ikryanov on 10.06.16
 * Copyright © 2016 Dmitry Ikryanov. All rights reserved.
 */
@file:JvmName("DimensionUtils")

package biz.growapp.extensions

import android.content.res.Resources
import android.util.TypedValue

val Float.dp: Int
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, Resources.getSystem().displayMetrics).toInt()

val Float.sp: Int
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this, Resources.getSystem().displayMetrics).toInt()
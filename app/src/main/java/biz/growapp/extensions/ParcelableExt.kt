@file:JvmName("ParcelableUtils")
@file:Suppress("NOTHING_TO_INLINE")

package biz.growapp.extensions

import android.os.Parcel

inline fun Parcel.readBoolean() = readInt() != 0

inline fun Parcel.writeBoolean(value: Boolean) = writeInt(if (value) 1 else 0)

inline fun Parcel.writeInt(value: Int?) = if (value == null) {
    writeBoolean(false)
} else {
    writeBoolean(true)
    writeInt(value)
}

inline fun Parcel.readInt() = if (readBoolean()) readInt() else null
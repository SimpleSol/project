package biz.growapp.extensions

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat

val Context.launcherIntent: Intent
    get() = packageManager.getLaunchIntentForPackage(packageName)

fun Context.getCompatColor(@ColorRes resId: Int) = ContextCompat.getColor(this, resId)

fun Context.getCompatDrawable(@DrawableRes drawableId:Int) = ContextCompat.getDrawable(this, drawableId)

inline fun Fragment.makeDial(number: String) = activity.makeDial(number)

fun Context.makeDial(number: String): Boolean {
    try {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number"))
        startActivity(intent)
        return true
    } catch (e: Exception) {
        e.printStackTrace()
        return false
    }
}

fun Context.makeRoute(address: String) : Boolean {
    val gmmIntentUri = Uri.parse("google.navigation:q=$address")
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
    mapIntent.`package` = "com.google.android.apps.maps"
    if (mapIntent.resolveActivity(packageManager) != null) {
        startActivity(mapIntent)
        return true
    } else {
        return false
    }
}

package com.realworld.io.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.location.Geocoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.text.TextUtils
import android.util.DisplayMetrics
import android.util.Patterns
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.core.graphics.ColorUtils
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt


fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

/**
 * Display the simple Toast message with the [Toast.LENGTH_SHORT] duration.
 *
 * @param message the message text.
 */
fun Context.toast(message: CharSequence): Toast = Toast
    .makeText(this, message, Toast.LENGTH_SHORT)
    .apply {
        show()
    }

fun View.snackbar(message: CharSequence) = Snackbar
    .make(this, message, Snackbar.LENGTH_SHORT)
    .apply { show() }

/**
 * For validate email-id
 *
 * @return email-id is valid or not
 */
fun String.isValidEmail(): Boolean {
    return !TextUtils.isEmpty(this) && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

/**
 * For validate phoneNumber
 *
 * @return phoneNumber is valid or not
 */
fun String.isValidMobile(): Boolean {
    return Patterns.PHONE.matcher(this).matches()
}


fun String.setTextHTML(): Spanned {
    val result: Spanned =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(this)
        }
    return result
}

/**
 * isNetworkAvailable - Check if there is a NetworkConnection
 * @return boolean
 */
/**
 * compare string
 * @return boolean
 */
infix fun String.shouldBeSame(other: String) = this == other

/**
 * For launch other activity
 */
inline fun <reified T : Any> Activity.launchActivity(
    requestCode: Int = -1,
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}
) {

    val intent = newIntent<T>(this)
    intent.init()
    startActivityForResult(intent, requestCode)

}

inline fun <reified T : Any> Context.launchActivity(
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}
) {

    val intent = newIntent<T>(this)
    intent.init()
    startActivity(intent)
}

inline fun <reified T : Any> newIntent(context: Context): Intent =
    Intent(context, T::class.java)


/**
 * load image
 */
/**
 * for check string
 */
fun isEmptyString(text: String?): String {
    return if (text == null || text.trim { it <= ' ' } == "null" || text.trim { it <= ' ' }
            .isEmpty()) {
        "-"
    } else {
        text
    }
}

/**
 * value set in decimal format
 * like 1.1111 to 1.11
 */
fun setValueInDecimalFormat(value: Double): String {
    return "%.2f".format(Locale.ENGLISH, value)
}

/**
 * convert object to string
 */
fun convertObjectToString(value: Any): String {
    return Gson().toJson(value)
}

/**
 * get object from string
 */
fun <T> convertStringToObject(value: String?, defValue: Class<T>): T {
    return Gson().fromJson(SharedPrefHelper[value!!, ""], defValue)
}

/**
 * convert to Requestbody
 */
fun String.convertToRequestBody(): RequestBody {
    return this.toRequestBody("text/plain".toMediaTypeOrNull())
}

/**
 * prepare a file part
 */
fun prepareFilePart(partName: String, imagePath: String): MultipartBody.Part {
    val imageFile = File(imagePath)
    val requestFile = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData(partName, imageFile.name, requestFile)
}

/**
 * getFull Address from latlng
 */
fun getAddressFromLocation(context: Context, latitude: Double, longitude: Double): String {
    val geoCoder = Geocoder(context, Locale.getDefault())
    val addresses = geoCoder.getFromLocation(latitude, longitude, 1)
    return addresses[0].getAddressLine(0)
}

/**
 * getFull Address from latlng
 */
fun getSubLocalityFromLocation(context: Context, latitude: Double, longitude: Double): String {
    val geoCoder = Geocoder(context, Locale.getDefault())
    val addresses = geoCoder.getFromLocation(latitude, longitude, 1)
    return addresses[0].subLocality
}

/**
 * getState from latlng
 */
fun getStateFromLocation(context: Context, latitude: Double, longitude: Double): String {
    val geoCoder = Geocoder(context, Locale.getDefault())
    val addresses = geoCoder.getFromLocation(latitude, longitude, 1)
    return addresses[0].adminArea
}

/**
 * getCity from latlng
 */
fun getCityFromLocation(context: Context, latitude: Double, longitude: Double): String {
    val geoCoder = Geocoder(context, Locale.getDefault())
    val addresses = geoCoder.getFromLocation(latitude, longitude, 1)
    return addresses[0].locality
}

/**
 * getCountryCode from latlng
 */
fun getCountryCodeFromLocation(context: Context, latitude: Double, longitude: Double): String {
    val geoCoder = Geocoder(context, Locale.getDefault())
    val addresses = geoCoder.getFromLocation(latitude, longitude, 1)
    return addresses[0].countryCode
}

/**
 * getPostalCode from latlng
 */
fun getPostalCodeFromLocation(context: Context, latitude: Double, longitude: Double): String {
    val geoCoder = Geocoder(context, Locale.getDefault())
    val addresses = geoCoder.getFromLocation(latitude, longitude, 1)
    return addresses[0].postalCode
}

/**
 * fullScreen transparent statusbar
 */
fun Activity.makeStatusBarTransparent() {
    window.apply {
        clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        statusBarColor = Color.TRANSPARENT
    }
}

fun Activity.coloredStatusBarMode(
    @ColorInt color: Int = Color.WHITE,
    lightSystemUI: Boolean? = null
) {
    var flags: Int = window.decorView.systemUiVisibility // get current flags
    val systemLightUIFlag = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    var setSystemUILight = lightSystemUI

    if (setSystemUILight == null) {
        // Automatically check if the desired status bar is dark or light
        setSystemUILight = ColorUtils.calculateLuminance(color) < 0.5
    }

    flags = if (setSystemUILight) {
        // Set System UI Light (Battery Status Icon, Clock, etc)
        removeFlag(flags, systemLightUIFlag)
    } else {
        // Set System UI Dark (Battery Status Icon, Clock, etc)
        addFlag(flags, systemLightUIFlag)
    }

    window.decorView.systemUiVisibility = flags
    window.statusBarColor = color
}

private fun containsFlag(flags: Int, flagToCheck: Int) = (flags and flagToCheck) != 0

private fun addFlag(flags: Int, flagToAdd: Int): Int {
    return if (!containsFlag(flags, flagToAdd)) {
        flags or flagToAdd
    } else {
        flags
    }
}

private fun removeFlag(flags: Int, flagToRemove: Int): Int {
    return if (containsFlag(flags, flagToRemove)) {
        flags and flagToRemove.inv()
    } else {
        flags
    }
}

/**
 * convert dp to px
 */
fun Context.dpToPx(dp: Int): Int {
    val displayMetrics = this.resources.displayMetrics
    return (dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()
}


/**
 * dial phone
 */
fun dialPhone(context: Context, phoneNumber: String) {
    val i = Intent(Intent.ACTION_DIAL)
    val p = "tel:$phoneNumber"
    i.data = Uri.parse(p)
    context.startActivity(i)
}


fun RecyclerView.smoothSnapToPosition(
    position: Int,
    snapMode: Int = LinearSmoothScroller.SNAP_TO_START
) {
    val smoothScroller = object : LinearSmoothScroller(this.context) {
        override fun getVerticalSnapPreference(): Int = snapMode
        override fun getHorizontalSnapPreference(): Int = snapMode
    }
    smoothScroller.targetPosition = position
    layoutManager?.startSmoothScroll(smoothScroller)
}

/**
 * covert from HTML string
 */
fun String.toHtml(): Spanned {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        @Suppress("DEPRECATION")
        return Html.fromHtml(this)
    }
}

/**
 * format date
 */
@SuppressLint("SimpleDateFormat")
fun String.toFormatDate(): String {
    var spf = SimpleDateFormat("yyyy-MM-dd")
    val newDate = spf.parse(this)
    spf = SimpleDateFormat("dd MMM yyyy")
    return spf.format(newDate ?: "")
}

/**
 * open dailer
 */
/**
 * open mail intent
 */

/**
 * open url
 */
fun Context.openURL(url: String) {
    val openURL = Intent(Intent.ACTION_VIEW)
    openURL.data = Uri.parse(url)
    startActivity(openURL)

}


fun capitalizeString(str: String): String {
    var retStr = str
    try { // We can face index out of bound exception if the string is null
        retStr = str.substring(0, 1).toUpperCase() + str.substring(1)
    } catch (e: Exception) {
    }
    return retStr
}


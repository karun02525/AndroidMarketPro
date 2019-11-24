package com.pusauli.user.utils

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.Intent.*
import android.graphics.*
import android.net.ConnectivityManager
import android.net.Uri
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.google.android.material.snackbar.Snackbar
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.format.DateFormat
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.pusauli.user.R
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


object Utils {

    private const val isCheckLog: Boolean = true


    /*New Activity Start*/
    fun startNewActivity(cnt:AppCompatActivity, cls : Class<*>){
        cnt.startActivity(Intent(cnt,cls))
        cnt.finish()
        leftToRight(cnt)
    }

    fun startActivityes(cnt:Context, cls : Class<*>){
        cnt.startActivity(Intent(cnt,cls))
    }


    fun openDashBordScreen(cnt:AppCompatActivity, cls : Class<*>) {
        cnt.startActivity(Intent(cnt,cls)
           .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK))
    }

    fun Context.verifyAvailableNetwork():Boolean{
        val connectivityManager=this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo=connectivityManager.activeNetworkInfo
        return  networkInfo!=null && networkInfo.isConnected
    }


    fun parseDateToddMMyyyy(time: String): String? {
        val outputPattern = "yyyy-MM-dd"
        val inputPattern = "dd-MM-yyyy"
        val inputFormat = SimpleDateFormat(inputPattern)
        val outputFormat = SimpleDateFormat(outputPattern,Locale.ENGLISH)

        val date: Date?
        var str: String? = null

        try {
            date = inputFormat.parse(time)
            str = outputFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return str
    }

    fun parseDateToddMMyyyy2(time: String): String? {
        val outputPattern = "dd-MM-yyyy"
        val inputPattern = "dd-MM-yyyy"
        val inputFormat = SimpleDateFormat(inputPattern)
        val outputFormat = SimpleDateFormat(outputPattern,Locale.ENGLISH)

        var date: Date? = null
        var str: String? = null

        try {
            date = inputFormat.parse(time)
            str = outputFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return str
    }


    //Textformate
     fun getFormattedString(cnt: Context,label: String, values: String): SpannableString {
        var value = values
        value = if (TextUtils.isEmpty(value)) "N.A." else value
        val spannableString = SpannableString(label + "\n" + value)
        val foregroundSpan = ForegroundColorSpan(ContextCompat.getColor(cnt, R.color.colorPrimary))
        val styleSpan = StyleSpan(Typeface.NORMAL)
        val sizeSpan = AbsoluteSizeSpan(10, true)
        spannableString.setSpan(sizeSpan, 0, label.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(foregroundSpan, 0, label.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(styleSpan, 0, label.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return spannableString
    }





    }

    fun hideSoftKeyboard(activity: Activity) {
        try {
            val inputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
        } catch (e: Exception) {
        }
    }



    /*Alert Dailog SpannableString*/
     fun getFormattedStringAlertTitile(cnt: Context,label: String): SpannableString {
        val spannableString = SpannableString(label)
        val foregroundSpan = ForegroundColorSpan(ContextCompat.getColor(cnt, R.color.colorAccent))
        val styleSpan = StyleSpan(Typeface.BOLD)
        val sizeSpan = AbsoluteSizeSpan(22, true)
        spannableString.setSpan(sizeSpan, 0, label.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(foregroundSpan, 0, label.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(styleSpan, 0, label.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return spannableString
    }

     fun getFormattedStringAlertMess(cnt: Context, label: String): SpannableString {

        val spannableString = SpannableString(label)
        val foregroundSpan = ForegroundColorSpan(ContextCompat.getColor(cnt, R.color.colorPrimary))
        val styleSpan = StyleSpan(Typeface.NORMAL)
        val sizeSpan = AbsoluteSizeSpan(15, true)
        spannableString.setSpan(sizeSpan, 0, label.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(foregroundSpan, 27, label.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(styleSpan, 0, label.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return spannableString
    }





    //Notification URL convert Bitmap
    fun getBitmapFromURL(strURL: String): Bitmap? {
        return try {
            val url = URL(strURL)
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input = connection.inputStream
            BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }


/*
        AlertDialog.Builder(context)
                .setTitle(title)
                .setCancelable(false)
                .setMessage(mess)
                .setPositiveButton(android.R.string.yes) { dialog, whichButton ->

                }
                .setNegativeButton(android.R.string.no) { dialog, which ->

                }.show()*/




    /*Animation Right To Left */
    fun leftToRight(cnt: AppCompatActivity) {
        cnt.overridePendingTransition(R.anim.enter, R.anim.exit)
    }

    fun rightToLeft(cnt: AppCompatActivity) {
        cnt.overridePendingTransition(R.anim.left_to_right_silder, R.anim.right_to_left__silder)
    }

    //Custom Snackbar
    fun showSnackBar(context: Context, message: String, color: Int): Snackbar {
        val sb = Snackbar.make((context as Activity).findViewById<View>(android.R.id.content), message, Snackbar.LENGTH_LONG)
        sb.view.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
        val textView = sb.view.findViewById<TextView>(R.id.snackbar_text)
        textView.setTextColor(color)
        sb.show()
        return sb
    }

    //Custom Snackbar
    fun showSnackBarErr(context: Context, message: String, color: Int): Snackbar {
        val sb = Snackbar.make((context as Activity).findViewById<View>(android.R.id.content), message, Snackbar.LENGTH_LONG)
        sb.view.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        val textView = sb.view.findViewById<TextView>(R.id.snackbar_text)
        textView.setTextColor(color)
        sb.show()
        return sb
    }




    //Custome hide keyboard
    fun hideSoftKeyboad(context: Context,v: View) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(v.windowToken, 0)
    }



    //show Log
    fun log(tag: String, mess: String) {
        /*if (BuildConfig.DEBUG) {
            Log.d(tag, mess)
        }*/
    }




  fun timeStamp(timestamp:String):String{
      val cal = Calendar.getInstance(Locale.ENGLISH)
      cal.timeInMillis = timestamp.toLong() * 1000L
     // return  DateFormat.format("dd-MM-yyyy hh:mm:ss", cal).toString()
      return  DateFormat.format("dd/MM/yyyy", cal).toString()
  }



//   val ed = edit.text.toString()

//   val bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.nk)

//   img.setImageBitmap(bitmap.resize(1000, 1200))


//       Log.d("TAGS", "  :  " + bitmap.toBase64())


/*
            if(ed.equalsIgnoreCase("kaju"))
            Toast.makeText(applicationContext,"Right ",Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(applicationContext,"Not Right ",Toast.LENGTH_SHORT).show()
*/


//  img.loadFromUrl("https://s33.postimg.cc/lite9hzlb/IMG_20180606_104125_094.jpg")


//   img.setImageBitmap(edit.getBitmap())


//   edit.setBackgroundColor(this.getColors(R.color.colorAccent))


/**
 * Extension method to get a view as bitmap.
 */
fun View.getBitmap(): Bitmap {
    val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bmp)
    draw(canvas)
    canvas.save()
    return bmp
}


fun Context.getColors(@ColorRes id: Int) = ContextCompat.getColor(this, id)

fun Context.getDrawables(@DrawableRes id: Int) = ContextCompat.getDrawable(this, id)


fun View.showKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    this.requestFocus()
    imm.showSoftInput(this, 0)
}


fun Context.sms(phone: String?, body: String = "") {
    val smsToUri = Uri.parse("smsto:$phone")
    val intent = Intent(Intent.ACTION_SENDTO, smsToUri)
    intent.putExtra("sms_body", body)
    startActivity(intent)
}

fun Context.dial(tel: String?) {
    startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:$tel")))
}


/**
 * Extension method to share for Context.
 */
fun Context.share(text: String, subject: String = ""): Boolean {
    val intent = Intent()
    intent.type = "text/plain"
    intent.putExtra(EXTRA_SUBJECT, subject)
    intent.putExtra(EXTRA_TEXT, text)
    try {
        startActivity(createChooser(intent, null))
        return true
    } catch (e: ActivityNotFoundException) {
        return false
    }
}



//Extension method to check if String is Number.
fun String.isNumeric(): Boolean {
    val p = "^[0-9]+$".toRegex()
    return matches(p)
}


//Extension method to check String equalsIgnoreCase
fun String.equalsIgnoreCase(other: String) = this.toLowerCase().contentEquals(other.toLowerCase())


//  Extension method to get base64 string for Bitmap.
fun Bitmap.toBase64(): String {
    var result = ""
    val baos = ByteArrayOutputStream()
    try {
        compress(Bitmap.CompressFormat.JPEG, 100, baos)
        baos.flush()
        baos.close()
        val bitmapBytes = baos.toByteArray()
        result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT)
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        try {
            baos.flush()
            baos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    return result
}

/**
 * Extension method to resize Bitmap to specified height and width.
 */
fun Bitmap.resize(w: Number, h: Number): Bitmap {
    val width = width
    val height = height
    val scaleWidth = w.toFloat() / width
    val scaleHeight = h.toFloat() / height
    val matrix = Matrix()
    matrix.postScale(scaleWidth, scaleHeight)
    if (width > 0 && height > 0) {
        return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
    }
    return this
}


/**
 * Extension method to save Bitmap to specified file path.
 */
fun Bitmap.saveFile(path: String) {
    val f = File(path)
    if (!f.exists()) {
        f.createNewFile()
    }
    val stream = FileOutputStream(f)
    compress(Bitmap.CompressFormat.PNG, 100, stream)
    stream.flush()
    stream.close()
}



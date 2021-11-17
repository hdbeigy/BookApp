package com.hdb.dreamyapp.utils

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Html
import android.text.Html.ImageGetter
import android.text.Spanned
import android.text.TextUtils
import android.util.DisplayMetrics
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import ir.hdb.bookap.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object Utilities {

    fun setToolbar(activity: AppCompatActivity, title: String?, color: String?): ActionBar? {
        val toolbar = activity.findViewById<Toolbar>(R.id.toolbar)
        if (color != null && !color.isEmpty()) toolbar.setBackgroundColor(Color.parseColor(color))

        val toolbarTitle: TextView = activity.findViewById(R.id.toolbar_title);


//        if (toolbarTitle != null) {
        toolbarTitle.setText(title);
//            toolbar.setTitle("");
//        } else
        toolbar.title = ""
        activity.setSupportActionBar(toolbar)
        return activity.supportActionBar
    }

    fun setToolbar(activity: AppCompatActivity, title: String?): ActionBar? {
        return setToolbar(activity, title, null)
    }

    fun setupCustomActivtyToolbar(actionbar: ActionBar?) {
        actionbar!!.setDisplayShowTitleEnabled(true)
        actionbar.setDisplayShowHomeEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setHomeAsUpIndicator(R.drawable.ic_back)
    }

    @JvmOverloads
    fun setupCustomActivtyCloseToolbar(actionbar: ActionBar?, iconId: Int = R.drawable.ic_close) {
        actionbar?.setDisplayShowTitleEnabled(true)
        actionbar?.setDisplayShowHomeEnabled(true)
        actionbar?.setDisplayHomeAsUpEnabled(true)
        actionbar?.setHomeAsUpIndicator(iconId)
    }

    fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    fun getHtml(imageGetter: ImageGetter?, html: String): Spanned {
        var html = html
        while (html.contains("[gallery")) {
            val sub = html.substring(html.indexOf("[gallery"), html.indexOf("\"]") + 2)
            Log.d("hdb", sub)
            html = html.replace(sub, "\n")
            Log.d("hdb-final", html)
        }
        return if (Build.VERSION.SDK_INT >= 24) Html.fromHtml(
            html,
            Html.FROM_HTML_MODE_COMPACT,
            imageGetter,
            null
        ) else Html.fromHtml(html, imageGetter, null)
    }

    fun dpToPx(resources: Resources, dp: Int): Int {
        val displayMetrics = resources.displayMetrics
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
    }

    @Throws(ParseException::class)
    fun parseMysqlJustDate(date: String?): Date {
        val pattern = "yyyy-MM-dd"
        val formatter = SimpleDateFormat(pattern)
        return formatter.parse(date)
    }

    @Throws(ParseException::class)
    fun parseMysqlDate(date: String?): Date {
        val pattern = "yyyy-MM-dd hh:mm:ss"
        val formatter = SimpleDateFormat(pattern)
        return formatter.parse(date)
    }

    @Throws(ParseException::class)
    fun toMysqlDate(date: Date?): String {
        val pattern = "yyyy-MM-dd hh:mm:ss"
        val formatter = SimpleDateFormat(pattern)
        return formatter.format(date)
    }

    fun changeViewFont(context: Context, v: View?) {
        try {
            if (v is ViewGroup) {
                val vg = v
                for (i in 0 until vg.childCount) {
                    val child = vg.getChildAt(i)
                    changeViewFont(context, child)
                }
            } else if (v is TextView) {
                v.typeface = Typeface.createFromAsset(context.assets, "fonts/spinwerad.ttf")
            }
        } catch (e: Exception) {
        }
    }

    fun hideSystemUI(activity: Activity) {
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        val decorView = activity.window.decorView
        decorView.fitsSystemWindows = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            activity.window.setDecorFitsSystemWindows(false)
        }
        decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                or View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                or View.SYSTEM_UI_FLAG_IMMERSIVE)
    }
}
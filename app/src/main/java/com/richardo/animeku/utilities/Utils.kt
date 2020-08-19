package com.richardo.animeku.utilities

import android.view.View
import android.widget.TextView
import com.richardo.animeku.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

object Utils {

    fun getResourcesString(view : TextView, name : Int,  text : String) : String{
        val resources = view.context.resources
        return resources.getString(name, text)
    }

    fun getRandomColorS(view : View) : Int{
        val resources = view.context.resources.getIntArray(R.array.placeholderColor)
        val random = Random.nextInt(0, resources.size)

        return resources[random]
    }

    fun getFuzzyDateInt() : Int{
        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat("yyyyMMdd")
        return formatter.format(date).toInt()
    }

    fun convertDpIntoPx(dp : Float, view : View) : Int {
        val scale = view.resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }
}
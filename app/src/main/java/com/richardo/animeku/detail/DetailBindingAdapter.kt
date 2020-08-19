package com.richardo.animeku.detail


import android.view.View
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_COMPACT
import androidx.databinding.BindingAdapter
import com.example.animeku.DetailAnimeQuery
import com.richardo.animeku.R
import com.richardo.animeku.utilities.Utils
import okhttp3.internal.Util
import java.text.DateFormatSymbols

@BindingAdapter("HtmlRenderDescription")
fun bindDescription(textView: TextView, text: String?){

    if(text != null){
        textView.text = HtmlCompat.fromHtml(text, FROM_HTML_MODE_COMPACT)
    }else{
        textView.text = ""
    }
}

@BindingAdapter("setEpisode")
fun bindEpisode(textView: TextView, data : DetailAnimeQuery.Media?){

    if(data?.episodes != null){
        textView.text = Utils.getResourcesString(textView,
        R.string.episodes, data.episodes.toString())
    }else{
        textView.text = "Episodes : Unknown"
    }

}

@BindingAdapter("setGenre")
fun bindGenre(textView: TextView, data : DetailAnimeQuery.Media?){
    data?.genres?.let {
        if (it.isNotEmpty()){
            textView.text = Utils.getResourcesString(textView,
            R.string.genres, it.joinToString())
        }else{
            textView.text = "Genre : Unknown"
        }
    }
}

@BindingAdapter("setStartDate")
fun bindStartDate(textView: TextView, data: DetailAnimeQuery.Media?){
    var strDate = "Unkown"
    if (data?.startDate?.month != null){
        val strMonth = DateFormatSymbols().months[data.startDate.month - 1]
        strDate = "$strMonth ${data.startDate.day ?: ""}, ${data.startDate.year}"
    }

    textView.text = Utils.getResourcesString(textView,
        R.string.start_date, strDate)
}

@BindingAdapter("isGone")
fun bindViewIsGone(view : View, visible : Boolean){
    view.visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("setRating")
fun bindRating(view : RatingBar, rating : Int?){
    var value = 0f

    if (rating != null){
        value = (rating.toFloat() / 10f) / 2f
    }

    view.rating = value
}

@BindingAdapter("setStudio")
fun bindStudio(textview : TextView, data : DetailAnimeQuery.Media?){
    data?.studios?.nodes?.let {
        if (it.isNotEmpty()){
            textview.text = it[0]?.name?.let { name ->
                Utils.getResourcesString(textview,
                    R.string.studio,
                    name
                )
            }
        }else{
            textview.text = "Unknown"
        }
    }
}

@BindingAdapter("setStatus")
fun bindStatus(textView: TextView, data : DetailAnimeQuery.Media?){

    if(data?.status != null){
        textView.text = Utils.getResourcesString(textView,
            R.string.status,
            data.status.toString().replace("_", " "))
    }else{
        textView.text = "Status : Unknown"
    }
}

@BindingAdapter("setDuration")
fun bindDuration(textView: TextView, duration : Int?){
    if (duration != null){
        textView.text = Utils.getResourcesString(textView,
        R.string.duration,
        duration.toString())
    }else{
        textView.text = "Duration : Unknown"
    }
}
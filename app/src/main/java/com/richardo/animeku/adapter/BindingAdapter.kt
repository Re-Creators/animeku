package com.richardo.animeku.adapter

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.animeku.OngoingListQuery
import com.example.animeku.SortListQuery
import com.richardo.animeku.utilities.Utils
import kotlinx.android.synthetic.main.ongoing_item.view.*
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view : ImageView, url : String?){
    if(!url.isNullOrEmpty()){
        val color = Utils.getRandomColorS(view)
        Glide.with(view.context)
            .load(url)
            .placeholder(ColorDrawable(color))
            .into(view)
    }
}

@BindingAdapter("bindTextView")
fun bindTextView(view: TextView, text : String?){
   text?.let {
       view.text = text
   }
}

@BindingAdapter("setRatingText")
fun bindRatingText(view: TextView, rating : Int?){
    var textRate = "N/A"

    if(rating != null){
        textRate = (rating.toFloat() / 10f).toString()
    }

    view.text = textRate
}

@BindingAdapter("setEpisodeOngoing")
fun bindEpisodeOngoing(view: TextView, data: OngoingListQuery.Medium?){
    // Mengecek apakah waktu nextepisode null atau tidak dan konversi dari waktu epoch ke date time
    val seconds : Long? = data?.nextAiringEpisode?.airingAt?.toLong()
    seconds?.let {
        val df = SimpleDateFormat("EEEE")
       view.text = df.format(Date(seconds * 1000))
    }
}
package kg.kunduznbkva.newsapp.utils

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

fun View.loadImage(res: String?){
    Glide
        .with(this)
        .load(res)
        .apply(RequestOptions.bitmapTransform( RoundedCorners(14)))
        .into(this as ImageView)
}

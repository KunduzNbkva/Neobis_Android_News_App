package kg.kunduznbkva.newsapp.utils

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.isVisible
import com.bumptech.glide.Glide


fun ImageView.loadImage(url: String) {
    Glide.with(this).load(url).centerCrop().into(this)
}

fun View.gone() {
    this.isVisible = false
}

fun View.visible() {
    this.isVisible = true
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

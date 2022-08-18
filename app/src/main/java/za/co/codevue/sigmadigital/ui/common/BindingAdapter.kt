package za.co.codevue.sigmadigital.ui.common

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.paging.LoadState
import com.bumptech.glide.Glide
import com.google.android.material.textview.MaterialTextView
import za.co.codevue.sigmadigital.R

/** Shows or hides a [View] */
@BindingAdapter(value = ["visibleOrGone"])
fun View.setVisibleOrGone(show: Boolean?) {
    visibility = if (show == true) View.VISIBLE else View.GONE
}

/** Loads an image into an [ImageView] */
@BindingAdapter(value = ["imageUrl"])
fun ImageView.loadImage(imageUrl: String?) {
    imageUrl?.also {
        Glide.with(this.context)
            .load(it)
            .centerCrop()
            .placeholder(R.mipmap.ic_launcher)
            .into(this)
    }
}

/** Sets an error message from [LoadState.Error] to [MaterialTextView] */
@BindingAdapter(value = ["errorMessage"])
fun MaterialTextView.setError(loadState: LoadState?) {
    (loadState as? LoadState.Error)?.also {
        text = it.error.message
    }
}
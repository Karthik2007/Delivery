package com.karthik.delivery.base.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.karthik.delivery.R


/**
 * created by Karthik A on 11/03/19
 */
fun ImageView.loadImage(url: String?) {
    Glide.with(context)
        .load(url)
        .apply(RequestOptions.circleCropTransform())
        .placeholder(context.getDrawable(R.drawable.ic_user))
        .into(this)
}

package com.salaryfilter.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.widget.ImageView

/**
 * Created by Max Makeychik on 02-Feb-18.
 */
data class LoadImageUtil(val context: Context, private val url: String?, private val placeholder: Placeholder,
                         private val error: Error, private val params: Params, private val imageView: ImageView) {

    fun load(): LoadImageUtil {
        val glide = GlideApp.with(context)
        var requestManager: GlideRequest<Drawable>
        if (TextUtils.isEmpty(url)) {
            requestManager = glide.load("http://")
        } else {
            requestManager = glide.load(url)
        }
        if (placeholder.withPlaceholder) {
            if (placeholder.drawable != null || placeholder.resId != 0) {
                if (placeholder.drawable != null) {
                    requestManager = requestManager.placeholder(placeholder.drawable)
                } else if (placeholder.resId != 0) {
                    requestManager = requestManager.placeholder(placeholder.resId)
                }
            } else {
                //requestManager = requestManager.placeholder(getPlaceHolder())
            }
        }
        if (error.errorDrawable != null) {
            requestManager = requestManager.error(error.errorDrawable)
        } else if (error.errorResId != 0) {
            requestManager = requestManager.error(error.errorResId)
        } else {
            //  by default load mock burger image on error
            //requestManager = requestManager.error(getErrorDrawable())
        }

        when (params.imageFitType) {
            LoadImageUtil.ImageFitType.CENTER_INSIDE -> requestManager.centerInside()
            LoadImageUtil.ImageFitType.CENTER_CROP -> requestManager.centerCrop()
        }

        //fade in
        requestManager.into(imageView)
        return this
    }

    class LoadImageUtilBuilder(context: Context?) {

        private val context: Context
        private var url: String? = null
        private var placeholderDrawable: Drawable? = null
        private var placeholderResId: Int = 0
        private var errorDrawable: Drawable? = null
        private var errorResId: Int = 0
        private var imageFitType: ImageFitType? = null
        private var placeholder = true
        private var withAnimationFadeIn: Boolean = false

        init {
            if (context == null) {
                throw IllegalArgumentException("Context must not be null.")
            }
            this.context = context.applicationContext
        }

        fun load(url: String): LoadImageUtilBuilder {
            //Timber.d("load image: $url")
            this.url = url
            return this
        }

        fun placeholder(placeholderResId: Int): LoadImageUtilBuilder {
            if (placeholderResId == 0) {
                throw IllegalArgumentException("Placeholder image resource invalid.")
            }
            if (placeholderDrawable != null) {
                throw IllegalStateException("Placeholder image already set.")
            }
            this.placeholderResId = placeholderResId
            return this
        }

        fun placeholder(placeholderDrawable: Drawable): LoadImageUtilBuilder {
            if (placeholderResId != 0) {
                throw IllegalStateException("Placeholder image already set.")
            }
            this.placeholderDrawable = placeholderDrawable
            return this
        }

        fun noPlaceholder(): LoadImageUtilBuilder {
            this.placeholder = false
            return this
        }

        fun error(errorResId: Int): LoadImageUtilBuilder {
            if (errorResId == 0) {
                throw IllegalArgumentException("Error image resource invalid.")
            }
            if (errorDrawable != null) {
                throw IllegalStateException("Error image already set.")
            }
            this.errorResId = errorResId
            return this
        }

        fun error(errorDrawable: Drawable): LoadImageUtilBuilder {
            if (errorResId != 0) {
                throw IllegalStateException("Error image already set.")
            }
            this.errorDrawable = errorDrawable
            return this
        }

        fun fitType(imageFitType: ImageFitType?): LoadImageUtilBuilder {
            if (imageFitType == null) {
                throw IllegalArgumentException("ImageFitType invalid.")
            }
            if (this.imageFitType != null) {
                throw IllegalStateException("ImageFitType already set.")
            }
            this.imageFitType = imageFitType
            return this
        }

        fun into(imageView: ImageView): LoadImageUtil {
            if (this.imageFitType == null) {
                this.imageFitType = ImageFitType.CENTER_INSIDE
            }
            return LoadImageUtil(context, url, Placeholder(placeholder, placeholderDrawable, placeholderResId), Error(errorDrawable, errorResId),
                    Params(imageFitType!!, withAnimationFadeIn), imageView).load()
        }

        fun withFadeInAnimation(): LoadImageUtilBuilder {
            withAnimationFadeIn = true
            return this
        }
    }

    enum class ImageFitType {
        CENTER_INSIDE,
        CENTER_CROP
    }

    class Placeholder(val withPlaceholder: Boolean, val drawable: Drawable?, val resId: Int)

    class Error(val errorDrawable: Drawable?, val errorResId: Int)

    class Params(val imageFitType: ImageFitType, val withAnimationFadeIn: Boolean)

    companion object {
        fun with(context: Context): LoadImageUtilBuilder {
            return LoadImageUtilBuilder(context)
        }
    }
}
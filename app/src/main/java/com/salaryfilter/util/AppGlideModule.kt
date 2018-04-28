package com.salaryfilter.util

import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

@GlideModule
class AppGlideModule : AppGlideModule() {

    // Disable manifest parsing to avoid adding similar modules twice.
    override fun isManifestParsingEnabled(): Boolean {
        return false
    }
}
package com.stiven.languageapp.model

import com.stiven.languageapp.utils.Clouds

/**
 * Cloud object.
 *
 * @param cloud Cloud index.
 * @param position Floating position of the cloud.
 * @param status Status of the lesson represented by the cloud.
 * */
data class Cloud(
    val cloud: Clouds,
    val position: Pair<Float,Float>,
    val status: Boolean
)
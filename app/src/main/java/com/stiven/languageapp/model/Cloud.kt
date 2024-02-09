package com.stiven.languageapp.model

import com.stiven.languageapp.utils.CloudType

/**
 * Cloud object.
 *
 * @param cloud Cloud index.
 * @param position Floating position of the cloud.
 * @param status Status of the lesson represented by the cloud.
 * @param cloudThreshold Points to reach unlock the cloud.
 * */
data class Cloud(
    val cloud: CloudType,
    val position: Pair<Float,Float>,
    val status: Boolean,
    val cloudThreshold: Int
)
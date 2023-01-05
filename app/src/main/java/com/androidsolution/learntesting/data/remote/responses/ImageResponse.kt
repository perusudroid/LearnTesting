package com.androidsolution.learntesting.data.remote.responses


data class ImageResponse(
    val hits: List<ImageResult>?=null,
    val total: Int,
    val totalHits: Int
)
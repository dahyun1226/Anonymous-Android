package com.anonymous.appilogue.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    @field:Json(name = "id") val id: Int = 0,
    @field:Json(name = "nickname") val nickname: String = "",
    @field:Json(name = "profileUrl") val profileUrl: String? = null
)

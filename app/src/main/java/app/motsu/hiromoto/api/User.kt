package app.motsu.hiromoto.api

import com.google.gson.annotations.SerializedName

data class User(
    var name: String,
    @SerializedName("login") var userId: String,
    @SerializedName("avatar_url") var avatarUrl: String,
    var following: Int,
    var followers: Int
)
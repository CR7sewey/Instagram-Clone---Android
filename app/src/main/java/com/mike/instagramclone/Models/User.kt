package com.mike.instagramclone.Models

data class User(
    var username: String? = null,
    var password: String? = null,
    var email: String? = null,
    var image: String? = null,
    var bio: String? = null,
    var following: ArrayList<User>? = null,
    var followers: ArrayList<User>? = null,
    var totalPosts: Int? = null
)

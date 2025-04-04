package com.mike.instagramclone.Models

 class Post {

     var caption: String? = null
    var image: String? = null
    var uid: String? = null
    var time: String? = null
     var uname: String? = null

     constructor(caption: String? = null, image: String?= null, uid: String?= null, time: String? = null, uname: String? = null) {
         this.caption = caption
         this.image = image
         this.uid = uid
         this.time = time
        this.uname = uname
     }
 }
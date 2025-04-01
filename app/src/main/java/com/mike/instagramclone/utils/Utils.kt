package com.mike.instagramclone.utils

import android.net.Uri
import java.util.UUID
import com.google.firebase.storage.FirebaseStorage

class Utils {

    public fun uploadImage(uri: Uri, folderName: String, callback: (String?) -> Unit) {
        // TO DO -> Cloud Storage - FIREBASE
        var imageUrl: String? = null
        /* FirebaseStorage.getInstance().getReference(folderName).child(UUID.randomUUID().toString()).putFile(uri)
        .addOnSuccessListener { it ->
            it.storage.downloadUrl.addOnSuccessListener {
                imageUrl = it.toString()
            }
            callback(imageUrl)

        }
        .addOnFailureListener {
            it.printStackTrace()
            callback(null)
        }

        return imageUrl!!

         */
        callback(imageUrl)
    }
}
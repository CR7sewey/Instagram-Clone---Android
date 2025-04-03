package com.mike.instagramclone.utils

import android.app.ProgressDialog
import android.content.Context
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

    public fun uploadVideo(uri: Uri, folderName: String, context: Context, progressDialog: ProgressDialog, callback: (String?) -> Unit) {
        // TO DO -> Cloud Storage - FIREBASE
        var videoUrl: String? = null
        progressDialog.setTitle("Uploading")
        /* FirebaseStorage.getInstance().getReference(folderName).child(UUID.randomUUID().toString()).putFile(uri)
        .addOnProgressListener {
            var progress: Double = (100.0 * it.bytesTransferred) / it.totalByteCount
            progressDialog.setMessage("Uploading ${progress.toInt()}%")
            progressDialog.show()
        }
        .addOnSuccessListener { it ->
            it.storage.downloadUrl.addOnSuccessListener {
                videoUrl = it.toString()
            }
            progressDialog.dismiss()
            callback(imageUrl)

        }
        .addOnFailureListener {
            it.printStackTrace()
            progressDialog.dismiss()
            callback(null)
        }

        return videoUrl!!

         */
        callback(videoUrl)
    }
}
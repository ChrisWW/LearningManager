package com.example.learningmanager.fragments.myinspiration

import android.app.ProgressDialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class FirebaseManager @Inject constructor(
//    @ApplicationContext val context: Context,
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseStorage: FirebaseStorage,
    private val firebaseAuth: FirebaseAuth
) {
    private val mStorageRef = firebaseStorage.reference
    private lateinit var mProgressDialog: ProgressDialog

    fun uploadImage(imageFileUri: Uri, activity: FragmentActivity) {
        mProgressDialog = ProgressDialog(activity)
        mProgressDialog.setMessage("Please wait, image being upload")

        mProgressDialog.show()
        val date = Date()
        val uploadTask = mStorageRef.child("images/${date}.png").putFile(imageFileUri)
        uploadTask.addOnSuccessListener {
            Log.e("Firebase", "Image Upload success")
            mProgressDialog.dismiss()
            val uploadedURL =
                mStorageRef.child("images/${date}.png").downloadUrl.addOnCompleteListener {
                    imgUrl = it.result.toString()
                    Log.d("value", "VALUE FIREBASE IMG $imgUrl")
                    _triggerImage.value = imgUrl
                    val ONE_MEGABYTE: Long = 1024 * 1024
//                    (firebaseStorage.getReferenceFromUrl(imgUrl)).getBytes(ONE_MEGABYTE).addOnSuccessListener { bytes ->
//                        imageBytesInString = String(bytes)
//                    }
                }

            Log.e("Firebase", "Uploaded $uploadedURL")
        }.addOnFailureListener {
            Log.e("Frebase", "Image Upload fail")
            mProgressDialog.dismiss()
        }
    }

    fun saveFireStore(
        inspirationTitle: String,
        description: String,
        data: String,
        author: String,
        quote: String,
        localization: String,
        activity: FragmentActivity
    ) {
                val inspiration: MutableMap<String, Any> = HashMap()
                inspiration["inspirationTitle"] = inspirationTitle
                inspiration["description"] = description
                inspiration["author"] = author
                inspiration["quote"] = quote
                inspiration["imagePath"] = imgUrl
                inspiration["data"] = data
                inspiration["localization"] = localization

                firebaseFirestore.collection("inspirations")
                    .add(inspiration)
                    .addOnSuccessListener {
                        Toast.makeText(activity, "record added successfully ", Toast.LENGTH_SHORT)
                            .show()
                        Log.d("finish", "READ DB")
                        readFireStoreData()
                    }
                    .addOnFailureListener {
                        Log.d("finish", "FAIL READ DB")
                        Toast.makeText(activity, "record Failed to add ", Toast.LENGTH_SHORT).show()
                    }
    }

    fun readFireStoreData() {
        firebaseFirestore.collection("inspirations")
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    elementList.clear()
                    for (document in it.result!!) {
                        elementList.add(
                            arrayListOf(
                                document.data.getValue("inspirationTitle").toString(),
                                document.data.getValue("description").toString(),
                                document.data.getValue("author").toString(),
                                document.data.getValue("quote").toString(),
                                document.data.getValue("imagePath").toString(),
                                document.data.getValue("data").toString(),
                                document.data.getValue("localization").toString()
                            )
                        )
                    }
                    Log.d("value", "TRIGGER FB ${elementList}")
//                    _triggerImage.value = document.data.getValue("imagePath").toString()
                    _setGoalsData.value = elementList

                    //TODO Fetch data
                }
            }
    }

    fun signOut() {
        firebaseAuth.signOut()
    }

    companion object {
        var imgUrl = ""
        var elementList = arrayListOf<ArrayList<String>>()
        var _setGoalsData = MutableStateFlow(arrayListOf<ArrayList<String>>())
        val setGoalsData = _setGoalsData.asStateFlow()
        var _triggerImage = MutableStateFlow("")
        val triggerImage = _triggerImage.asStateFlow()
        var imageBytesInString = ""
    }
}
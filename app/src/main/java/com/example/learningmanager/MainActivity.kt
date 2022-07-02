package com.example.learningmanager

import android.Manifest
import android.Manifest.permission.READ_CALENDAR
import android.Manifest.permission.WRITE_CALENDAR
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.example.learningmanager.MainActivity.Companion.addGoogleResultCode
import com.github.dhaval2404.imagepicker.ImagePicker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        requestPermissions(arrayListOf(Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR).toTypedArray(), 42)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && addPictureResultCode) {
            //Image Uri will not be null for RESULT_OK
            Log.d("datanull", "${data?.data!!}")
            val uri: Uri = data?.data!!
            inspirationImage = uri.toString()
            Log.d("value", "$inspirationImage")
            addPictureResultCode = false
        } else if (resultCode == RESULT_OK && addGoogleResultCode && addGoogleSaveAndNavigate) {
                Log.d("googletest", "GOOGLERESULT $addGoogleResultCode")
                addGoogleResultCode = false
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Log.d("datanull", "${resultCode}")
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        var inspirationImage = ""
        var addPictureResultCode = false
        var addGoogleResultCode = false
        var addGoogleSaveAndNavigate = false
    }
}
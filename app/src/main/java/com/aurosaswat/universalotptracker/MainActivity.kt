package com.aurosaswat.universalotptracker

import android.Manifest.permission.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.aurosaswat.universalotptracker.databinding.ActivityMainBinding
import android.app.appsearch.SetSchemaRequest.READ_SMS
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

//    private lateinit var viewBinding
    private lateinit var viewBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        ActivityCompat.requestPermissions(this,
            arrayOf("android.permission.READ_SMS"), PackageManager.PERMISSION_GRANTED)

        if (ContextCompat.checkSelfPermission(this, "android.permission.READ_SMS") == PackageManager.PERMISSION_GRANTED) {
            viewBinding.showSms.text=""
            val uri = Uri.parse("content://sms")
            val cursor = contentResolver.query(uri, null, null, null, null)

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    val body = cursor.getString(cursor.getColumnIndexOrThrow("body"))
                    val address = cursor.getString(cursor.getColumnIndexOrThrow("address"))
                    viewBinding.showSms.append(address)

                } while (cursor.moveToNext())
            }
        } else {
            Toast.makeText(this@MainActivity,"Please Allow Notifications Manually",Toast.LENGTH_SHORT).show();
        }

    }
}
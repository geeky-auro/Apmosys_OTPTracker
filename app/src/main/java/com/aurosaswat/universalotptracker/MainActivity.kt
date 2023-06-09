package com.aurosaswat.universalotptracker


import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.credentials.HintRequest
import com.google.android.gms.auth.api.phone.SmsRetriever
import java.util.regex.Pattern


class MainActivity : AppCompatActivity() {
//    private lateinit var viewBinding
    private val REQ_USER_CONSENT = 200

    private var smsReceiver:SMSReceiver?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        ActivityCompat.requestPermissions(this,
            arrayOf("android.permission.READ_SMS"), PackageManager.PERMISSION_GRANTED)
        val data = ArrayList<ItemsViewmodel>()
        val recyclerView=findViewById<RecyclerView>(R.id.recycler_view)
        if (ContextCompat.checkSelfPermission(this, "android.permission.READ_SMS") == PackageManager.PERMISSION_GRANTED) {

            val uri = Uri.parse("content://sms")
            val cursor = contentResolver.query(uri, null, null, null, null)

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    val body = cursor.getString(cursor.getColumnIndexOrThrow("body"))
                    val address = cursor.getString(cursor.getColumnIndexOrThrow("address"))
                    if (address.contains("OTP",ignoreCase = true) || address.contains("otp") || body.contains("OTP") || body.contains("otp")){
                    data.add(ItemsViewmodel(body,address))
                   }

                } while (cursor.moveToNext())
            }
        } else {
            Toast.makeText(this@MainActivity,"Please Allow Notifications Manually",Toast.LENGTH_SHORT).show();
        }
        recyclerView.layoutManager=LinearLayoutManager(this)
        val adapter=OTPAdapter(data)
        recyclerView.adapter=adapter


    }

//    private fun startSmartUserConsent() {
//        // Mark this Line ;)
//        val client = SmsRetriever.getClient(this)
//        client.startSmsUserConsent(null)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        val message = data?.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)
//        getOtpFromMessage(message)
//
//
//
//    }
//
//    private fun getOtpFromMessage(message: String?) {
//        val otpPatter = Pattern.compile("(|^)\\d{6}")
//        val matcher = message?.let { otpPatter.matcher(it) }
//        if (matcher!!.find()){
//
//            viewBinding.showSms.text = matcher.group(0)
//
//        }
//    }
//
//    private fun registerBroadcastReceiver() {
//
//        smsReceiver = SMSReceiver()
//        smsReceiver!!.smsBroadcastReceiverListener =
//            object : SMSReceiver.SmsBroadcasrReceiverListener {
//                override fun onSuccess(intent: Intent?) {
//                    startActivityForResult(intent!!, REQ_USER_CONSENT)
//                }
//
//                override fun onFailure() {
//                }
//
//            }
//        val intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
//        registerReceiver(smsReceiver,intentFilter)
//    }
//
//
//
//    override fun onStart() {
//        super.onStart()
//        registerBroadcastReceiver()
//    }
//
//    override fun onStop() {
//        super.onStop()
//        unregisterReceiver(smsReceiver!!)
//    }


}
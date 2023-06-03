package com.aurosaswat.universalotptracker


import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.aurosaswat.universalotptracker.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.credentials.HintRequest
import com.google.android.gms.auth.api.phone.SmsRetriever


class MainActivity : AppCompatActivity() {

//    private lateinit var viewBinding
    private lateinit var viewBinding: ActivityMainBinding
    private var intentFilter:IntentFilter?=null
    private var smsReceiver:SMSReceiver?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        initSmsListener()
        initBroadCast()
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

    private fun initSmsListener() {
        val client = SmsRetriever.getClient(this)
        client.startSmsRetriever()
    }


    private fun initBroadCast(){
        intentFilter= IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
        smsReceiver= SMSReceiver()
        smsReceiver?.setOTPListener(object :SMSReceiver.OTPReceiveListener{
            override fun onOTPReceived(otp: String?) {
                Toast.makeText(this@MainActivity,"OTP is $otp",Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(smsReceiver,intentFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(smsReceiver)
    }

    override fun onDestroy() {
        super.onDestroy()
        smsReceiver=null
    }

}
package com.aurosaswat.universalotptracker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import java.util.regex.Pattern
import javax.net.ssl.SSLEngineResult.Status

class SMSReceiver : BroadcastReceiver() {

    // Creation of SMS Broadcase Receiver to receive the message

    private var otpListener:OTPReceiveListener?=null

    interface OTPReceiveListener {
        fun onOTPReceived(otp: String?)
    }

    fun setOTPListener(otpListener: OTPReceiveListener?) {
        this.otpListener = otpListener
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent?.action==SmsRetriever.SMS_RETRIEVED_ACTION){
            val extras=intent.extras
            val status=extras!![SmsRetriever.EXTRA_STATUS] as Status?
            when(status!!.ordinal){
                CommonStatusCodes.SUCCESS->{
                    val sms=extras[SmsRetriever.EXTRA_SMS_MESSAGE] as String?
                    sms?.let {
                        val p=Pattern.compile("\\d+")
                        val m=p.matcher(it)
                        if (m.find()){
                            val otp=m.group()
                            if(otpListener!=null){
                                otpListener!!.onOTPReceived(otp)
                            }
                        }
                    }
                }
            }
        }
    }
}
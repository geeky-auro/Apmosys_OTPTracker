package com.aurosaswat.universalotptracker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import java.util.regex.Pattern
import javax.net.ssl.SSLEngineResult.Status

class SMSReceiver : BroadcastReceiver() {


    interface SmsBroadcasrReceiverListener {
        fun onSuccess(intent:Intent?)
        fun onFailure()
    }


    var smsBroadcastReceiverListener:SmsBroadcasrReceiverListener?=null

    override fun onReceive(context: Context?, intent: Intent?) {
        if  (SmsRetriever.SMS_RETRIEVED_ACTION == intent?.action){

            val extras = intent.extras
            val smsRetrieverStatus = extras?.get(SmsRetriever.EXTRA_STATUS) as com.google.android.gms.common.api.Status

            when(smsRetrieverStatus.statusCode){

                CommonStatusCodes.SUCCESS ->{
                    val messageIntent = extras.getParcelable<Intent>(SmsRetriever.EXTRA_CONSENT_INTENT)
                    smsBroadcastReceiverListener?.onSuccess(messageIntent)
                }
                CommonStatusCodes.TIMEOUT -> {
                    smsBroadcastReceiverListener?.onFailure()

                }
            }

        }
    }


}